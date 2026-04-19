#!/usr/bin/env python3
"""
dataset/ 폴더의 경복궁 PDF에서 텍스트 + 이미지 추출.

사용법:
    python3 scripts/extract_pdfs.py

산출물:
    - dataset/extracted/{heritage_id}.json  : 텍스트 + 메타데이터
    - dataset/extracted/images/{heritage_id}_{n}.jpg  : 추출 이미지
    - app/src/main/assets/heritage/{heritage_id}_cover.jpg  : Android 앱용 대표 이미지

PDF 파일명 → Android heritage_id 매핑은 PDF_HERITAGE_MAP에 정의.
"""

from __future__ import annotations
import fitz  # pymupdf
import json
import os
import re
import unicodedata
from pathlib import Path

# 프로젝트 루트
ROOT = Path(__file__).resolve().parent.parent
DATASET = ROOT / "dataset"
EXTRACTED = DATASET / "extracted"
IMAGES_OUT = EXTRACTED / "images"
ANDROID_ASSETS = ROOT / "app" / "src" / "main" / "assets" / "heritage"

# PDF 파일명 패턴 → Android 앱 heritage_id / poi_id 매핑.
# `poi:` 접두사가 있으면 extraPoiList의 Poi.id와 매핑됨.
# 없으면 HeritageContent.id로 매핑.
PDF_HERITAGE_MAP: dict[str, str | list[str]] = {
    "광화문": "gwanghwamun",
    "흥례문": "poi:gate_heungnyemun",
    "근정전과 근정문": ["geunjeongjeon", "geunjeongmun"],
    "사정전": "sajeongjeon",
    "수정전": "sujeongjeon",
    "경회루": "gyeonghoeru",
    "풍기대": "poi:punggidae",   # 향후 POI로 추가할 수 있음
    "영추문": "poi:gate_west",
    "강녕전": "gangnyeongjeon",
    "교태전과 아미산굴뚝": "gyotaejeon",
    "자경전과 십장생굴뚝": "jagyeongjeon",
    "동궁 영역": "donggung",
    "흥복전": "heungbokjeon",
    "함화당과 집경당": "poi:hamhwadang",  # 향후 POI로 추가할 수 있음
    "건춘문": "poi:gate_east",
    "향원정": "hyangwonjeong",
    "건청궁": "geoncheongung",
    "집옥재": "jibokjae",
    "태원전": "taeweonjeon",
    "신무문": "poi:gate_north",
}

# 갤러리로 추가 저장할 이미지 수 (cover 외).
GALLERY_EXTRA_IMAGES = 3


def clean_text(text: str) -> str:
    """PDF 추출 텍스트 정제 - UI 찌꺼기 제거."""
    # URL 제거
    text = re.sub(r"https?://\S+", "", text)
    # 페이지 번호 / 하단 찌꺼기
    text = re.sub(r"\d+/\d+\s*$", "", text, flags=re.MULTILINE)
    text = re.sub(r"Document\s*", "", text)
    # "경복궁 이야기" 네비게이션 블록 제거
    nav_markers = [
        "전체 다운로드", "이 저작물만 다운로드",
        "궁능소개", "경복궁", "경복궁 이야기",
        "광화문", "흥례문", "근정전과 근정문", "사정전", "수정전", "경회루",
        "풍기대", "영추문", "강녕전", "교태전과 아미산굴뚝", "자경전과 십장생굴뚝",
        "동궁 영역", "흥복전", "함화당과 집경당", "건춘문", "향원정",
        "건청궁", "집옥재", "태원전", "신무문"
    ]
    # 연속된 줄바꿈 정리
    lines = text.split("\n")
    cleaned_lines = []
    for line in lines:
        s = line.strip()
        # 네비 블록 - 짧은 줄만 필터
        if s in nav_markers and len(s) < 15:
            continue
        if not s:
            continue
        cleaned_lines.append(s)
    # 마지막에 한 문단으로 합침
    result = " ".join(cleaned_lines)
    # 과도한 공백 제거
    result = re.sub(r"\s+", " ", result).strip()
    return result


def extract_pdf(pdf_path: Path, target_id: str, is_poi: bool) -> dict:
    """PDF 하나에서 텍스트 + 이미지 추출.

    @param target_id  heritage_id 또는 poi_id (PDF 매핑값에서 'poi:' 제거된 형태)
    @param is_poi     True면 POI용 (asset 파일명 `poi_{id}_cover.jpg` 등), False면 heritage
    """
    doc = fitz.open(pdf_path)
    all_text = []
    # (size_area, img_bytes, width, height) 리스트 — 크기순 정렬 후 cover + 갤러리 선택
    candidate_images: list[tuple[int, bytes, int, int]] = []

    # 추출 이미지 저장 디렉토리 (원본 풀해상도 PNG 보관)
    img_dir = IMAGES_OUT / target_id
    img_dir.mkdir(parents=True, exist_ok=True)

    images_meta = []
    image_idx = 0

    for page_num in range(len(doc)):
        page = doc[page_num]
        page_text = page.get_text()
        if page_text:
            all_text.append(page_text)

        for img_info in page.get_images(full=True):
            xref = img_info[0]
            try:
                pix = fitz.Pixmap(doc, xref)
                if pix.n > 4:
                    pix = fitz.Pixmap(fitz.csRGB, pix)

                img_bytes = pix.tobytes("png")

                # 썸네일/작은 이미지(<30KB) 제외
                if len(img_bytes) < 30_000:
                    pix = None
                    continue

                # 모든 후보 이미지를 풀해상도로 보관 (재생성 가능한 원본)
                img_filename = f"{target_id}_{image_idx}.png"
                img_path = img_dir / img_filename
                with open(img_path, "wb") as f:
                    f.write(img_bytes)

                area = pix.width * pix.height
                candidate_images.append((area, img_bytes, pix.width, pix.height))
                images_meta.append({
                    "index": image_idx,
                    "path": f"extracted/images/{target_id}/{img_filename}",
                    "size_bytes": len(img_bytes),
                    "width": pix.width,
                    "height": pix.height,
                })

                pix = None
                image_idx += 1
            except Exception as e:
                print(f"    [warn] image extract failed xref={xref}: {e}")

    # 면적 내림차순으로 정렬 → 1번이 cover, 2~4번이 갤러리
    candidate_images.sort(key=lambda x: -x[0])

    ANDROID_ASSETS.mkdir(parents=True, exist_ok=True)
    asset_prefix = "poi_" if is_poi else ""

    # Cover 저장
    if candidate_images:
        cover_path = ANDROID_ASSETS / f"{asset_prefix}{target_id}_cover.png"
        with open(cover_path, "wb") as f:
            f.write(candidate_images[0][1])

    # 갤러리 (cover 제외, 최대 GALLERY_EXTRA_IMAGES)
    # POI는 gallery 없이 cover만 저장 (용량 절감 + UX 단순)
    gallery_saved = 0
    if not is_poi:
        for i, (_area, img_bytes, _w, _h) in enumerate(
            candidate_images[1 : 1 + GALLERY_EXTRA_IMAGES]
        ):
            gallery_path = ANDROID_ASSETS / f"{target_id}_{i + 1}.png"
            with open(gallery_path, "wb") as f:
                f.write(img_bytes)
            gallery_saved += 1

    full_text = clean_text("\n".join(all_text))
    doc.close()

    return {
        "heritage_id": target_id,
        "is_poi": is_poi,
        "source_pdf": pdf_path.name,
        "pages": len(all_text),
        "raw_text": full_text,
        "images": images_meta,
        "image_count": len(images_meta),
        "cover_saved": len(candidate_images) > 0,
        "gallery_saved": gallery_saved,
    }


def main():
    if not DATASET.exists():
        print(f"ERROR: dataset/ not found at {DATASET}")
        return

    EXTRACTED.mkdir(parents=True, exist_ok=True)
    IMAGES_OUT.mkdir(parents=True, exist_ok=True)
    ANDROID_ASSETS.mkdir(parents=True, exist_ok=True)

    pdfs = sorted(DATASET.glob("*.pdf"))
    print(f"Found {len(pdfs)} PDFs in dataset/")

    all_results = []
    for pdf_path in pdfs:
        # macOS HFS 파일명은 NFD 정규화 → NFC로 변환해야 한글 매칭 가능
        name = unicodedata.normalize("NFC", pdf_path.stem)
        # "경복궁 이야기_XXX" 파싱
        m = re.search(r"경복궁 이야기_(.+)$", name)
        if not m:
            # 관람안내류는 건너뜀 (heritage별 매핑 없음)
            print(f"  [skip] {pdf_path.name} (not a heritage PDF)")
            continue

        key = m.group(1).strip()
        mapping = PDF_HERITAGE_MAP.get(key)
        if mapping is None:
            print(f"  [warn] no heritage_id mapping for: {key}")
            continue

        # 하나의 PDF가 여러 heritage 매핑 (근정전과 근정문 → geunjeongjeon, geunjeongmun)
        targets = mapping if isinstance(mapping, list) else [mapping]

        for raw_target in targets:
            # 'poi:XXX' 접두사 파싱
            is_poi = raw_target.startswith("poi:")
            target_id = raw_target[4:] if is_poi else raw_target
            kind = "POI" if is_poi else "HERITAGE"
            print(f"  [extract] {pdf_path.name} → [{kind}] {target_id}")
            result = extract_pdf(pdf_path, target_id, is_poi=is_poi)
            all_results.append(result)

    # 모든 결과를 JSON 하나로 저장
    output_path = EXTRACTED / "heritage_pdf_data.json"
    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(all_results, f, ensure_ascii=False, indent=2)

    print(f"\n=== Summary ===")
    print(f"PDFs processed: {len(all_results)}")
    print(f"Output JSON: {output_path}")
    print(f"Images dir: {IMAGES_OUT}")
    print(f"Android assets: {ANDROID_ASSETS}")


if __name__ == "__main__":
    main()
