#!/usr/bin/env python3
"""
ChromaDB 인덱스 빌드 스크립트.

소스:
  1. knowledge.py의 HERITAGE_DB (17개 전각 × 평균 2~4개 청크)
  2. dataset/extracted/heritage_pdf_data.json (21개 PDF 원문)

각 청크를 임베딩하고 heritage_chunks 컬렉션에 upsert.

실행:
  python3 services/agent-retrieval/build_vector_index.py

결과:
  .chroma/ 디렉토리에 SQLite 기반 영구 저장소 생성.
"""

from __future__ import annotations
import json
import logging
import sys
from pathlib import Path

# 서비스 경로를 import path에 추가
SERVICE_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(SERVICE_DIR))

from knowledge import HERITAGE_DB  # noqa: E402

ROOT = SERVICE_DIR.parent.parent
CHROMA_DB_PATH = str(ROOT / ".chroma")
COLLECTION_NAME = "heritage_chunks"
PDF_DATA_PATH = ROOT / "dataset" / "extracted" / "heritage_pdf_data.json"

logging.basicConfig(level=logging.INFO, format="%(levelname)s %(message)s")
log = logging.getLogger("build-index")


def chunk_long_text(text: str, max_chars: int = 400) -> list[str]:
    """PDF 원문을 문장 단위로 분할."""
    if len(text) <= max_chars:
        return [text]
    # 문장 끝(. ! ? 。)에서 분할
    sentences = []
    current = []
    for ch in text:
        current.append(ch)
        if ch in ".!?。" and len("".join(current)) > max_chars // 2:
            sentences.append("".join(current).strip())
            current = []
    if current:
        sentences.append("".join(current).strip())

    # 합쳐서 max_chars를 넘지 않는 블록 만들기
    blocks = []
    buf = ""
    for s in sentences:
        if len(buf) + len(s) > max_chars:
            if buf:
                blocks.append(buf.strip())
            buf = s
        else:
            buf += " " + s
    if buf:
        blocks.append(buf.strip())
    return blocks or [text[:max_chars]]


def main():
    import chromadb
    from chromadb.config import Settings

    log.info("Initializing ChromaDB at %s", CHROMA_DB_PATH)
    client = chromadb.PersistentClient(
        path=CHROMA_DB_PATH,
        settings=Settings(anonymized_telemetry=False),
    )

    # 기존 컬렉션 제거 후 재생성 (clean rebuild)
    try:
        client.delete_collection(COLLECTION_NAME)
        log.info("Deleted existing collection: %s", COLLECTION_NAME)
    except Exception:
        pass

    collection = client.create_collection(
        name=COLLECTION_NAME,
        metadata={"description": "Gyeongbokgung heritage chunks (ko)"},
    )

    # ── Source 1: knowledge.py HERITAGE_DB ─────────────────────
    ids = []
    documents = []
    metadatas = []

    for heritage_id, heritage in HERITAGE_DB.items():
        for chunk in heritage.chunks:
            uid = f"kb_{heritage_id}_{chunk.chunk_id}"
            ids.append(uid)
            documents.append(chunk.content)
            metadatas.append({
                "heritage_id": heritage_id,
                "chunk_id": chunk.chunk_id,
                "section": chunk.section,
                "title": chunk.title,
                "keywords": ",".join(chunk.keywords),
                "source": "knowledge_db",
            })
    log.info("Staged %d chunks from knowledge.py", len(ids))

    # ── Source 2: PDF 원문 (있으면 추가) ──────────────────────
    pdf_count = 0
    if PDF_DATA_PATH.exists():
        with open(PDF_DATA_PATH, encoding="utf-8") as f:
            pdf_entries = json.load(f)
        for entry in pdf_entries:
            hid = entry["heritage_id"]
            # _로 시작하는 것은 heritage가 아닌 POI — 일단 제외
            if hid.startswith("_"):
                continue
            raw = entry.get("raw_text", "")
            if not raw:
                continue
            blocks = chunk_long_text(raw, max_chars=400)
            for idx, block in enumerate(blocks):
                uid = f"pdf_{hid}_{idx}"
                ids.append(uid)
                documents.append(block)
                metadatas.append({
                    "heritage_id": hid,
                    "chunk_id": f"pdf_{idx}",
                    "section": "PDF 원문",
                    "title": entry.get("source_pdf", "").replace(".pdf", ""),
                    "keywords": "",
                    "source": "pdf",
                })
                pdf_count += 1
        log.info("Staged %d chunks from PDF data", pdf_count)
    else:
        log.warning("PDF data not found at %s (skip)", PDF_DATA_PATH)

    # Upsert
    if ids:
        collection.upsert(ids=ids, documents=documents, metadatas=metadatas)
        log.info("Indexed %d total chunks into '%s'", len(ids), COLLECTION_NAME)
    else:
        log.warning("No data to index")

    # Sanity check
    count = collection.count()
    log.info("Collection now contains %d items", count)

    # 예시 쿼리
    log.info("\n=== Sanity query ===")
    result = collection.query(
        query_texts=["세종대왕과 한글"],
        n_results=3,
    )
    for rank, (doc, meta, dist) in enumerate(zip(
        result["documents"][0], result["metadatas"][0], result["distances"][0]
    )):
        log.info("  #%d [%s / %s] dist=%.3f: %s...",
                 rank + 1, meta["heritage_id"], meta.get("source", "?"),
                 dist, doc[:80].replace("\n", " "))


if __name__ == "__main__":
    main()
