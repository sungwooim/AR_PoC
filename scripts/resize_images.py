#!/usr/bin/env python3
"""
Android assets의 heritage cover 이미지를 리사이즈/압축해서 APK 크기 감소.

- 최대 너비 1024px로 리사이즈
- PNG → JPEG 변환 (품질 85)
- 평균적으로 5MB → 200~400KB 수준으로 감소

사용법: python3 scripts/resize_images.py
"""
from pathlib import Path
from PIL import Image

ROOT = Path(__file__).resolve().parent.parent
ASSETS = ROOT / "app" / "src" / "main" / "assets" / "heritage"

MAX_WIDTH = 1024
JPEG_QUALITY = 85


def main():
    if not ASSETS.exists():
        print(f"No assets dir at {ASSETS}")
        return

    pngs = sorted(ASSETS.glob("*.png"))
    print(f"Found {len(pngs)} PNG files to convert")

    total_before = 0
    total_after = 0
    for png_path in pngs:
        before_size = png_path.stat().st_size
        total_before += before_size

        try:
            img = Image.open(png_path).convert("RGB")
            # 리사이즈 (aspect ratio 유지)
            if img.width > MAX_WIDTH:
                ratio = MAX_WIDTH / img.width
                new_size = (MAX_WIDTH, int(img.height * ratio))
                img = img.resize(new_size, Image.LANCZOS)

            # JPEG로 저장
            jpg_path = png_path.with_suffix(".jpg")
            img.save(jpg_path, "JPEG", quality=JPEG_QUALITY, optimize=True)
            after_size = jpg_path.stat().st_size
            total_after += after_size

            # 원본 PNG 삭제
            png_path.unlink()

            ratio_pct = (1 - after_size / before_size) * 100
            print(f"  {png_path.stem}: {before_size // 1024}KB → {after_size // 1024}KB "
                  f"({ratio_pct:.0f}% smaller)")
        except Exception as e:
            print(f"  [error] {png_path.name}: {e}")

    print(f"\nTotal: {total_before // 1024 // 1024}MB → {total_after // 1024 // 1024}MB "
          f"({(1 - total_after / total_before) * 100:.0f}% reduction)")


if __name__ == "__main__":
    main()
