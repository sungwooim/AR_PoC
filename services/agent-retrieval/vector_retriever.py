"""
ChromaDB 기반 벡터 검색 엔진.

## 구성
- 임베딩: ChromaDB 기본 모델 (all-MiniLM-L6-v2, 다국어 OK)
- 저장소: 로컬 디스크 (PersistentClient) — 외부 서비스 불필요
- 인덱스: heritage_chunks 컬렉션 (heritage_id 필터 + 텍스트 유사도)

## 인덱스 구축
    python3 services/agent-retrieval/build_vector_index.py

## 환경변수
- `CHROMA_DB_PATH`: Chroma 저장 경로 (기본 `.chroma`)
- `CHROMA_COLLECTION`: 컬렉션명 (기본 `heritage_chunks`)

벡터 DB가 없으면 빈 결과 반환 — HybridRetriever가 gracefully fallback.
"""

from __future__ import annotations
import logging
import os
from pathlib import Path
from typing import TYPE_CHECKING

from models import ChunkData, RetrievalQuery, RetrievalResult, RetrievalStrategy, ScoredChunk

if TYPE_CHECKING:
    import chromadb
    from chromadb.api import ClientAPI

logger = logging.getLogger("vector_retriever")

# 프로젝트 루트 기준 기본 DB 경로
_DEFAULT_DB_PATH = str(Path(__file__).resolve().parent.parent.parent / ".chroma")
CHROMA_DB_PATH = os.environ.get("CHROMA_DB_PATH", _DEFAULT_DB_PATH)
CHROMA_COLLECTION = os.environ.get("CHROMA_COLLECTION", "heritage_chunks")

_client_singleton: "ClientAPI | None" = None
_collection_cache: "object | None" = None


def _get_client() -> "ClientAPI | None":
    """ChromaDB 클라이언트 싱글톤 획득. 실패하면 None (gracefully fallback)."""
    global _client_singleton
    if _client_singleton is not None:
        return _client_singleton
    try:
        import chromadb
        from chromadb.config import Settings
        _client_singleton = chromadb.PersistentClient(
            path=CHROMA_DB_PATH,
            settings=Settings(anonymized_telemetry=False),
        )
        logger.info("ChromaDB client initialized at %s", CHROMA_DB_PATH)
        return _client_singleton
    except Exception as e:
        logger.warning("ChromaDB init failed: %s — using empty results", e)
        return None


def _get_collection():
    """컬렉션 캐싱된 접근자. 없으면 None."""
    global _collection_cache
    if _collection_cache is not None:
        return _collection_cache
    client = _get_client()
    if client is None:
        return None
    try:
        _collection_cache = client.get_collection(CHROMA_COLLECTION)
        return _collection_cache
    except Exception as e:
        logger.info("Vector collection '%s' not found: %s. Run build_vector_index.py.",
                    CHROMA_COLLECTION, e)
        return None


async def vector_retrieve(query: RetrievalQuery) -> RetrievalResult:
    """쿼리에 대해 ChromaDB에서 코사인 유사도 top-k 검색."""
    if not query.query:
        return RetrievalResult(chunks=[], strategy=RetrievalStrategy.VECTOR, total_candidates=0)

    collection = _get_collection()
    if collection is None:
        return RetrievalResult(chunks=[], strategy=RetrievalStrategy.VECTOR, total_candidates=0)

    try:
        result = collection.query(
            query_texts=[query.query],
            n_results=query.max_results,
            where={"heritage_id": query.heritage_id},
        )
    except Exception as e:
        logger.warning("Vector query failed: %s", e)
        return RetrievalResult(chunks=[], strategy=RetrievalStrategy.VECTOR, total_candidates=0)

    chunks: list[ScoredChunk] = []
    if not result or not result.get("ids") or not result["ids"][0]:
        return RetrievalResult(chunks=[], strategy=RetrievalStrategy.VECTOR, total_candidates=0)

    documents = result["documents"][0]
    metadatas = result["metadatas"][0]
    distances = result.get("distances", [[]])[0]

    for doc_text, meta, dist in zip(documents, metadatas, distances):
        # ChromaDB 코사인 거리 → 유사도 점수 (0~1, 1이 최고)
        similarity = max(0.0, 1.0 - float(dist))
        chunks.append(ScoredChunk(
            chunk=ChunkData(
                chunk_id=meta.get("chunk_id", ""),
                section=meta.get("section", ""),
                title=meta.get("title", ""),
                content=doc_text,
                keywords=meta.get("keywords", "").split(",") if meta.get("keywords") else [],
            ),
            score=similarity,
            match_reason=f"vector_similarity:{similarity:.3f}",
            source=RetrievalStrategy.VECTOR,
        ))

    return RetrievalResult(
        chunks=chunks,
        strategy=RetrievalStrategy.VECTOR,
        total_candidates=len(chunks),
    )
