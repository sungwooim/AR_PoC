"""
로컬 지식 소스 — Android LocalHeritageKnowledgeSource의 서버 미러.

현재는 최소한의 메타데이터만 포함. 실제 청크 데이터는 Android 앱에서 전송하거나
DB에서 로드하는 방식으로 확장 예정.
"""

# 건물 메타데이터 (Android LocalHeritageKnowledgeSource와 동기화)
HERITAGE_META: dict[str, dict] = {
    "geunjeongjeon": {
        "title_map": {
            "ko": "근정전",
            "en": "Geunjeongjeon Hall",
            "ja": "勤政殿",
            "zh": "勤政殿",
        },
        "short_desc_map": {
            "ko": "경복궁의 정전(正殿)으로, 왕의 즉위식과 조회가 열린 곳입니다.",
            "en": "The main throne hall of Gyeongbokgung Palace, where coronations and royal audiences were held.",
            "ja": "景福宮の正殿で、王の即位式や朝会が行われた場所です。",
            "zh": "景福宫的正殿，是举行即位仪式和朝会的地方。",
        },
        "palace": "경복궁",
    },
    "gyeonghoeru": {
        "title_map": {
            "ko": "경회루",
            "en": "Gyeonghoeru Pavilion",
            "ja": "慶会楼",
            "zh": "庆会楼",
        },
        "short_desc_map": {
            "ko": "경복궁의 연회장으로, 외국 사신 접대와 과거 시험이 열린 누각입니다.",
            "en": "A grand banquet pavilion in Gyeongbokgung, used for state banquets and civil service examinations.",
            "ja": "景福宮の宴会場で、外国使臣の接待や科挙試験が行われた楼閣です。",
            "zh": "景福宫的宴会楼阁，用于国宴和科举考试。",
        },
        "palace": "경복궁",
    },
    "gwanghwamun": {
        "title_map": {
            "ko": "광화문",
            "en": "Gwanghwamun Gate",
            "ja": "光化門",
            "zh": "光化门",
        },
        "short_desc_map": {
            "ko": "경복궁의 정문으로, '빛으로 교화한다'는 뜻을 담고 있습니다.",
            "en": "The main gate of Gyeongbokgung Palace, meaning 'Gate of Radiant Transformation'.",
            "ja": "景福宮の正門で、「光で教化する」という意味があります。",
            "zh": "景福宫的正门，意为"以光教化"。",
        },
        "palace": "경복궁",
    },
    "sajeongjeon": {
        "title_map": {"ko": "사정전", "en": "Sajeongjeon Hall", "ja": "思政殿", "zh": "思政殿"},
        "short_desc_map": {
            "ko": "왕이 매일 신하들과 국사를 논의하던 편전입니다.",
            "en": "The king's daily office hall for deliberating state affairs.",
            "ja": "王が毎日臣下と国事を議論した便殿です。",
            "zh": "国王每天与大臣讨论国事的便殿。",
        },
        "palace": "경복궁",
    },
    "sujeongjeon": {
        "title_map": {"ko": "수정전", "en": "Sujeongjeon Hall", "ja": "修政殿", "zh": "修政殿"},
        "short_desc_map": {
            "ko": "집현전이 있던 곳으로, 한글 창제의 산실입니다.",
            "en": "Former site of Jiphyeonjeon, birthplace of Hangul.",
            "ja": "集賢殿があった場所で、ハングル創製の発祥地です。",
            "zh": "集贤殿所在地，韩文创制的发源地。",
        },
        "palace": "경복궁",
    },
    "gangnyeongjeon": {
        "title_map": {"ko": "강녕전", "en": "Gangnyeongjeon Hall", "ja": "康寧殿", "zh": "康宁殿"},
        "short_desc_map": {
            "ko": "왕의 침전으로, 용마루가 없는 특별한 지붕이 특징입니다.",
            "en": "The king's bedchamber, noted for its ridgeless roof.",
            "ja": "王の寝殿で、龍棟のない特別な屋根が特徴です。",
            "zh": "国王的寝殿，以没有正脊的特殊屋顶著称。",
        },
        "palace": "경복궁",
    },
    "gyotaejeon": {
        "title_map": {"ko": "교태전", "en": "Gyotaejeon Hall", "ja": "交泰殿", "zh": "交泰殿"},
        "short_desc_map": {
            "ko": "왕비의 침전으로, 아미산 굴뚝(보물 제811호)이 유명합니다.",
            "en": "The queen's bedchamber, famous for the Amisan chimneys (Treasure No. 811).",
            "ja": "王妃の寝殿で、峨嵋山の煙突（宝物第811号）が有名です。",
            "zh": "王妃的寝殿，以峨眉山烟囱（宝物第811号）闻名。",
        },
        "palace": "경복궁",
    },
    "jagyeongjeon": {
        "title_map": {"ko": "자경전", "en": "Jagyeongjeon Hall", "ja": "慈慶殿", "zh": "慈庆殿"},
        "short_desc_map": {
            "ko": "대왕대비의 거처로, 십장생 굴뚝(보물 제810호)이 유명합니다.",
            "en": "Residence of the Queen Dowager, famous for Ten Longevity chimney (Treasure No. 810).",
            "ja": "大王大妃の居所で、十長生の煙突（宝物第810号）が有名です。",
            "zh": "大王大妃的居所，以十长生烟囱（宝物第810号）闻名。",
        },
        "palace": "경복궁",
    },
    "hyangwonjeong": {
        "title_map": {"ko": "향원정", "en": "Hyangwonjeong Pavilion", "ja": "香遠亭", "zh": "香远亭"},
        "short_desc_map": {
            "ko": "경복궁 후원 연못 위의 아름다운 육각 정자입니다.",
            "en": "A beautiful hexagonal pavilion on a pond in the rear garden.",
            "ja": "景福宮の後苑の池の上にある美しい六角亭です。",
            "zh": "景福宫后花园池塘上的美丽六角亭。",
        },
        "palace": "경복궁",
    },
    "geoncheongung": {
        "title_map": {"ko": "건청궁", "en": "Geoncheongung Palace", "ja": "乾清宮", "zh": "乾清宫"},
        "short_desc_map": {
            "ko": "고종의 거처이자 을미사변(명성황후 시해)의 현장입니다.",
            "en": "King Gojong's residence and site of the Eulmi Incident.",
            "ja": "高宗の居所であり乙未事変の現場です。",
            "zh": "高宗的居所，也是乙未事变的现场。",
        },
        "palace": "경복궁",
    },
    "jibokjae": {
        "title_map": {"ko": "집옥재", "en": "Jibokjae Library", "ja": "集玉斎", "zh": "集玉斋"},
        "short_desc_map": {
            "ko": "고종의 서재이자 경복궁 유일의 중국식 건축입니다.",
            "en": "King Gojong's library, the only Chinese-style building in Gyeongbokgung.",
            "ja": "高宗の書斎で、景福宮唯一の中国式建築です。",
            "zh": "高宗的书房，景福宫唯一的中国式建筑。",
        },
        "palace": "경복궁",
    },
    "donggung": {
        "title_map": {"ko": "동궁", "en": "Donggung (Crown Prince's Palace)", "ja": "東宮", "zh": "东宫"},
        "short_desc_map": {
            "ko": "세자의 거처이자 학습 공간입니다.",
            "en": "The crown prince's residence and study area.",
            "ja": "世子の住まいであり学習空間です。",
            "zh": "世子的居所兼学习空间。",
        },
        "palace": "경복궁",
    },
    "taeweonjeon": {
        "title_map": {"ko": "태원전", "en": "Taeweonjeon Hall", "ja": "泰元殿", "zh": "泰元殿"},
        "short_desc_map": {
            "ko": "선왕의 어진을 모시던 제례 공간입니다.",
            "en": "A ceremonial hall for enshrining royal portraits.",
            "ja": "先王の御真を祀った祭礼空間です。",
            "zh": "供奉先王御真的祭礼空间。",
        },
        "palace": "경복궁",
    },
    "sojubang": {
        "title_map": {"ko": "소주방", "en": "Sojubang (Royal Kitchen)", "ja": "焼厨房", "zh": "烧厨房"},
        "short_desc_map": {
            "ko": "왕과 왕비의 수라를 준비하던 궁중 주방입니다.",
            "en": "The royal kitchen where the king and queen's meals were prepared.",
            "ja": "王と王妃の水刺を準備した宮中の厨房です。",
            "zh": "为国王和王妃准备膳食的宫廷厨房。",
        },
        "palace": "경복궁",
    },
    "heungbokjeon": {
        "title_map": {"ko": "흥복전", "en": "Heungbokjeon Hall", "ja": "興福殿", "zh": "兴福殿"},
        "short_desc_map": {
            "ko": "근정전 동쪽의 보조 전각으로, 현재 국립고궁박물관 부지입니다.",
            "en": "An auxiliary hall east of Geunjeongjeon, now the National Palace Museum site.",
            "ja": "勤政殿の東の補助殿閣で、現在は国立古宮博物館の敷地です。",
            "zh": "勤政殿东侧的辅助殿阁，现为国立故宫博物馆所在地。",
        },
        "palace": "경복궁",
    },
    "geunjeongmun": {
        "title_map": {"ko": "근정문", "en": "Geunjeongmun Gate", "ja": "勤政門", "zh": "勤政门"},
        "short_desc_map": {
            "ko": "근정전으로 들어가는 정문입니다.",
            "en": "The main gate leading to Geunjeongjeon.",
            "ja": "勤政殿への正門です。",
            "zh": "通往勤政殿的正门。",
        },
        "palace": "경복궁",
    },
    "yeongjeogyo": {
        "title_map": {"ko": "영제교", "en": "Yeongjeogyo Bridge", "ja": "永済橋", "zh": "永济桥"},
        "short_desc_map": {
            "ko": "금천 위에 놓인 돌다리로, 천록 조각이 유명합니다.",
            "en": "A stone bridge over Geumcheon stream, famous for Cheonrok sculptures.",
            "ja": "禁川の上の石橋で、天禄の彫刻が有名です。",
            "zh": "横跨禁川的石桥，以天禄雕刻闻名。",
        },
        "palace": "경복궁",
    },
}


def get_heritage_meta(heritage_id: str) -> dict | None:
    return HERITAGE_META.get(heritage_id)


def get_localized_title(heritage_id: str, lang: str) -> str:
    meta = HERITAGE_META.get(heritage_id)
    if meta is None:
        return heritage_id
    return meta["title_map"].get(lang, meta["title_map"]["ko"])


def get_localized_desc(heritage_id: str, lang: str) -> str:
    meta = HERITAGE_META.get(heritage_id)
    if meta is None:
        return ""
    return meta["short_desc_map"].get(lang, meta["short_desc_map"]["ko"])
