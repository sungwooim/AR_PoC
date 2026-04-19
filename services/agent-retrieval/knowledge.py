"""
서버측 청크 데이터.

Android LocalHeritageKnowledgeSource의 핵심 데이터를 서버에 미러링.
실제 운영에서는 DB(PostgreSQL 등)로 교체한다.
"""

from __future__ import annotations

from models import ChunkData, HeritageData

# ── 근정전 청크 ─────────────────────────────────────────
_GEUNJEONGJEON_CHUNKS = [
    ChunkData(
        chunk_id="geun_history",
        section="역사",
        title="근정전의 역사",
        content="근정전은 1395년 경복궁 창건 시 건립되었으며, 조선 왕조의 법궁 정전으로 사용되었습니다. "
                "임진왜란(1592년) 때 소실된 후, 1867년 흥선대원군의 경복궁 중건 시 다시 지어졌습니다.",
        keywords=["역사", "창건", "임진왜란", "중건", "흥선대원군", "1395"],
    ),
    ChunkData(
        chunk_id="geun_architecture",
        section="건축",
        title="근정전의 건축 양식",
        content="근정전은 정면 5칸, 측면 5칸의 중층 팔작지붕 건물입니다. "
                "이중 기단 위에 세워져 있으며, 앞마당에는 품계석과 박석이 깔려 있습니다.",
        keywords=["건축", "팔작지붕", "기단", "품계석", "박석", "중층"],
    ),
    ChunkData(
        chunk_id="geun_viewing",
        section="관람 포인트",
        title="근정전 관람 포인트",
        content="근정전 내부에는 임금이 앉던 어좌(용상)와 그 뒤의 일월오봉도 병풍이 있습니다. "
                "천장의 칠조룡 장식과 앞마당의 정교한 돌짐승 조각도 놓치지 마세요.",
        keywords=["어좌", "일월오봉도", "칠조룡", "돌짐승", "병풍", "관람"],
    ),
    ChunkData(
        chunk_id="geun_trivia",
        section="트리비아",
        title="근정전의 숨은 이야기",
        content="'근정'이라는 이름은 '부지런히 정사를 돌본다'는 뜻입니다. "
                "박석 바닥은 눈부심 방지와 배수를 위한 것이며, 비 오는 날에도 물이 고이지 않습니다.",
        keywords=["근정", "이름", "박석", "배수", "눈부심", "트리비아"],
    ),
]

# ── 경회루 청크 ─────────────────────────────────────────
_GYEONGHOERU_CHUNKS = [
    ChunkData(
        chunk_id="gyeong_history",
        section="역사",
        title="경회루의 역사",
        content="경회루는 1412년(태종 12년)에 현재의 규모로 확장 건립되었습니다. "
                "외국 사신 접대와 국가 연회의 장소로 사용되었으며, 국보 제224호로 지정되어 있습니다.",
        keywords=["역사", "태종", "1412", "국보", "연회", "사신"],
    ),
    ChunkData(
        chunk_id="gyeong_architecture",
        section="건축",
        title="경회루의 건축 양식",
        content="경회루는 정면 7칸, 측면 5칸의 2층 누각으로, 48개의 돌기둥 위에 세워져 있습니다. "
                "인공 연못 위에 떠 있는 형태이며, 3개의 돌다리로 연결됩니다.",
        keywords=["건축", "누각", "돌기둥", "연못", "돌다리", "2층"],
    ),
]

# ── 광화문 청크 ─────────────────────────────────────────
_GWANGHWAMUN_CHUNKS = [
    ChunkData(
        chunk_id="gwang_history",
        section="역사",
        title="광화문의 역사",
        content="광화문은 1395년 경복궁 창건과 함께 세워진 정문입니다. "
                "일제강점기에 이전되었다가 2010년 원래 위치에 복원되었습니다.",
        keywords=["역사", "1395", "정문", "일제강점기", "복원", "2010"],
    ),
    ChunkData(
        chunk_id="gwang_architecture",
        section="건축",
        title="광화문의 건축 양식",
        content="광화문은 화강암 석축 위에 세워진 중층 목조 누각입니다. "
                "양쪽에 해태상이 배치되어 있으며, '빛으로 교화한다'는 의미를 담고 있습니다.",
        keywords=["건축", "화강암", "해태", "누각", "교화", "석축"],
    ),
]

# ── 사정전 청크 ─────────────────────────────────────────
_SAJEONGJEON_CHUNKS = [
    ChunkData(
        chunk_id="saj_history",
        section="역사",
        title="사정전의 역사",
        content="사정전은 왕이 매일 신하들과 국정을 논의하던 편전입니다. '사정(思政)'이란 '깊이 생각하여 정사를 돌본다'는 뜻으로, 세종대왕은 이곳에서 경연을 1,898회 열었고 한글 창제의 밑그림을 그렸습니다.",
        keywords=["편전", "세종", "경연", "한글", "집무"],
    ),
    ChunkData(
        chunk_id="saj_architecture",
        section="건축",
        title="사정전의 건축 양식",
        content="사정전은 근정전과 달리 단층 구조로 실용성을 중시했습니다. 온돌이 설치되어 겨울에도 집무 가능하며, 좌우에 만춘전과 천추전이 날개처럼 배치되어 보좌 업무 공간 역할을 했습니다.",
        keywords=["단층", "온돌", "만춘전", "천추전", "팔작지붕"],
    ),
]

# ── 수정전 청크 ─────────────────────────────────────────
_SUJEONGJEON_CHUNKS = [
    ChunkData(
        chunk_id="suj_history",
        section="역사",
        title="수정전과 집현전",
        content="수정전 일대는 세종 시대 집현전(集賢殿)이 있던 곳으로, 한글(훈민정음)이 연구·창제된 장소입니다. 1894년 갑오개혁 당시에는 군국기무처가 설치되어 근대화 개혁이 논의되었습니다.",
        keywords=["집현전", "세종", "한글", "갑오개혁", "군국기무처"],
    ),
    ChunkData(
        chunk_id="suj_architecture",
        section="건축",
        title="경복궁 최장의 전각",
        content="수정전은 정면 10칸의 긴 장방형 건물로, 경복궁 전각 중 가장 깁니다. 다수 관료가 동시에 업무를 볼 수 있는 사무 공간으로 설계되었습니다.",
        keywords=["10칸", "장방형", "사무", "행각"],
    ),
]

# ── 강녕전 청크 ─────────────────────────────────────────
_GANGNYEONGJEON_CHUNKS = [
    ChunkData(
        chunk_id="gang_history",
        section="역사",
        title="왕의 침전 강녕전",
        content="강녕전은 왕의 침전으로, '강녕(康寧)'은 오복 중 하나인 '건강과 편안함'을 의미합니다. 1553년, 1876년 등 세 번 이상 화재로 소실되어 현재 건물은 1995년 복원되었습니다.",
        keywords=["침전", "왕", "오복", "화재", "복원"],
    ),
    ChunkData(
        chunk_id="gang_architecture",
        section="건축",
        title="용마루 없는 지붕",
        content="강녕전은 용마루(지붕 최상부의 수평 마루)가 없는 특별한 지붕을 가집니다. 왕이 곧 '용'이므로 그 위에 용마루를 올리지 않았다는 상징적 이유에서 비롯됩니다. 온돌과 대청마루가 결합된 구조입니다.",
        keywords=["용마루", "상징", "온돌", "대청마루"],
    ),
]

# ── 교태전 청크 ─────────────────────────────────────────
_GYOTAEJEON_CHUNKS = [
    ChunkData(
        chunk_id="gyo_history",
        section="역사",
        title="왕비의 침전 교태전",
        content="교태전은 왕비의 공식 거처인 중궁전(中宮殿)입니다. '교태(交泰)'는 주역에서 '천지가 어울려 만물이 소통한다'는 뜻으로, 왕(하늘)과 왕비(땅)의 조화를 상징합니다.",
        keywords=["왕비", "중궁전", "주역", "내명부"],
    ),
    ChunkData(
        chunk_id="gyo_amisan",
        section="관람 포인트",
        title="아미산 굴뚝 (보물 제811호)",
        content="교태전 뒤편 아미산 정원에는 보물 제811호 굴뚝 4기가 있습니다. 육각형 벽돌에 덩굴·나비·꽃 등의 문양이 새겨져 장식 예술의 극치를 보여주며, 왕비 전용 계단식 화단과 함께 조성되었습니다.",
        keywords=["아미산", "굴뚝", "보물", "811호", "꽃담", "화계"],
    ),
]

# ── 자경전 청크 ─────────────────────────────────────────
_JAGYEONGJEON_CHUNKS = [
    ChunkData(
        chunk_id="jag_history",
        section="역사",
        title="대왕대비를 위한 자경전",
        content="자경전은 1865년 흥선대원군이 경복궁 중건 시 대왕대비 조씨(신정왕후)를 위해 지었습니다. 경복궁에서 일제강점기의 훼손을 면하고 원형을 가장 잘 보존한 침전입니다.",
        keywords=["대왕대비", "효", "신정왕후", "원형보존"],
    ),
    ChunkData(
        chunk_id="jag_sipjangsaeng",
        section="관람 포인트",
        title="십장생 굴뚝 (보물 제810호)",
        content="자경전 서쪽 담장에는 보물 제810호 십장생 굴뚝이 있습니다. 해·산·물·구름·소나무·불로초·거북·학·사슴·대나무 등 십장생 문양을 벽돌에 정교하게 조각했으며, 장수와 복을 기원하는 의미를 담고 있습니다.",
        keywords=["십장생", "굴뚝", "보물", "810호", "장수", "꽃담"],
    ),
]

# ── 향원정 청크 ─────────────────────────────────────────
_HYANGWONJEONG_CHUNKS = [
    ChunkData(
        chunk_id="hyang_history",
        section="역사",
        title="고종의 휴식처 향원정",
        content="향원정은 1873년 고종이 건청궁을 지으면서 함께 조성한 2층 육각 정자입니다. '향원(香遠)'은 주돈이의 《애련설》에서 따온 말로 '향기가 멀리 퍼진다'는 뜻이며, 인공 연못 향원지 위에 떠 있습니다.",
        keywords=["고종", "건청궁", "육각정", "애련설"],
    ),
    ChunkData(
        chunk_id="hyang_chwihyang",
        section="관람 포인트",
        title="취향교와 포토 스팟",
        content="원래 북쪽에 있던 취향교는 일제 때 남쪽으로 옮겨졌다가, 2021년 문화재청의 고증으로 다시 북쪽 원위치에 복원되었습니다. 사계절 모두 아름답지만 특히 잔잔한 수면에 반영이 비치는 날이 최고의 촬영 시점입니다.",
        keywords=["취향교", "다리", "복원", "포토스팟", "반영"],
    ),
]

# ── 건청궁 청크 ─────────────────────────────────────────
_GEONCHEONGUNG_CHUNKS = [
    ChunkData(
        chunk_id="geon_history",
        section="역사",
        title="고종의 근대화 거점 건청궁",
        content="건청궁은 1873년 고종이 친정을 시작하면서 직접 지은 왕의 사저입니다. 1887년 한국 최초의 전등이 설치된 근대화 상징 공간이었으나, 1895년 이곳에서 명성황후가 시해된 을미사변이 발생하여 한국 근대사 최대 비극의 현장이 되었습니다.",
        keywords=["고종", "전등", "명성황후", "을미사변", "근대화"],
    ),
    ChunkData(
        chunk_id="geon_architecture",
        section="건축",
        title="궁궐 속의 작은 궁궐",
        content="건청궁은 경복궁 안에 있지만 독립적인 궁궐 형태입니다. 고종의 거처 장안당, 명성황후의 거처 곤녕합, 주변 행각과 정원으로 구성되며, 자연 지형을 살린 자유로운 배치가 특징입니다. 2007년 복원되었습니다.",
        keywords=["장안당", "곤녕합", "독립궁궐", "복원"],
    ),
]

# ── 집옥재 청크 ─────────────────────────────────────────
_JIBOKJAE_CHUNKS = [
    ChunkData(
        chunk_id="jib_history",
        section="역사",
        title="고종의 서재 집옥재",
        content="집옥재는 1891년 고종이 건청궁 옆에 지은 개인 서재 겸 외교 접견실입니다. '집옥(集玉)'은 '옥(보배로운 지식)을 모으는 곳'이라는 뜻으로, 고종은 이곳에 수천 권의 서적을 소장하고 서양의 법률·과학·외교 서적을 탐독했습니다.",
        keywords=["고종", "서재", "외교", "서양서적"],
    ),
    ChunkData(
        chunk_id="jib_architecture",
        section="건축",
        title="경복궁 유일의 중국식 건축",
        content="집옥재는 경복궁 안에서 유일하게 중국식 건축 양식으로 지어졌습니다. 벽돌과 유리창을 사용하고, 회랑으로 좌우 팔우정(팔각정)과 협길당을 연결하는 독특한 구조입니다.",
        keywords=["중국식", "벽돌", "유리", "팔우정", "회랑"],
    ),
]

# ── 동궁 청크 ─────────────────────────────────────────
_DONGGUNG_CHUNKS = [
    ChunkData(
        chunk_id="dong_history",
        section="역사",
        title="미래의 왕을 위한 공간",
        content="동궁은 '동쪽 궁궐'로, 해가 뜨는 방향처럼 성장하는 세자(왕세자)의 거처입니다. 세자는 이곳에서 서연(書筵)에 참석해 유교 경전과 제왕학을 학습하며, 하루 5회의 강의로 왕위 계승 교육을 받았습니다.",
        keywords=["세자", "서연", "동쪽", "제왕학"],
    ),
    ChunkData(
        chunk_id="dong_architecture",
        section="건축",
        title="비현각과 자선당",
        content="동궁의 핵심 건물은 세자가 학문을 배우는 비현각(丕顯閣)과 세자의 일상 거처 자선당(資善堂)입니다. 두 건물은 행각으로 연결되며, 현재 복원 과정 중이지만 일부 기단과 초석이 남아 옛 규모를 짐작할 수 있습니다.",
        keywords=["비현각", "자선당", "세자", "복원"],
    ),
]

# ── 태원전 청크 ─────────────────────────────────────────
_TAEWEONJEON_CHUNKS = [
    ChunkData(
        chunk_id="tae_history",
        section="역사",
        title="선왕을 모시던 태원전",
        content="태원전은 경복궁 서쪽에 위치한 전각으로, 돌아가신 선왕의 어진(초상화)을 모시는 혼전(魂殿) 역할을 했습니다. 왕이 승하하면 3년간 이곳에서 제사를 올린 후 종묘로 이안했습니다.",
        keywords=["혼전", "어진", "선왕", "제사", "종묘"],
    ),
]

# ── 소주방 청크 ─────────────────────────────────────────
_SOJUBANG_CHUNKS = [
    ChunkData(
        chunk_id="so_history",
        section="역사",
        title="궁중 주방 소주방",
        content="소주방은 왕과 왕비의 일상 식사인 '수라(水刺)'를 준비하던 궁중 주방입니다. 강녕전과 교태전 사이에 위치해 신속한 배선이 가능했으며, 최고의 숙수(조리사)와 상궁들이 하루 5회 수라상을 준비했습니다.",
        keywords=["수라", "주방", "궁중음식", "숙수", "상궁"],
    ),
]

# ── 흥복전 청크 ─────────────────────────────────────────
_HEUNGBOKJEON_CHUNKS = [
    ChunkData(
        chunk_id="heung_history",
        section="역사",
        title="흥복전과 국립고궁박물관",
        content="흥복전은 '복을 일으킨다'는 뜻의 보조 전각으로, 근정전 동쪽에 위치했습니다. 일제강점기에 철거되어 그 자리에 현재 국립고궁박물관이 들어서 있으며, 박물관 내에서 조선 왕실 유물을 감상할 수 있습니다.",
        keywords=["흥복전", "박물관", "국립고궁박물관", "유물"],
    ),
]

# ── 근정문 청크 ─────────────────────────────────────────
_GEUNJEONGMUN_CHUNKS = [
    ChunkData(
        chunk_id="gmun_history",
        section="역사",
        title="의례의 시작 근정문",
        content="근정문은 근정전 앞마당(조정)으로 들어가는 정문으로, 중층 문루 건물입니다. 문을 지난 신하들은 품계석에 따라 섰으며, 문 안쪽 가운데 길(어도)은 왕만이 걸을 수 있는 상징적 경계였습니다. 좌우에 일화문과 월화문이 배치되었습니다.",
        keywords=["정문", "문루", "어도", "품계석", "일화문"],
    ),
]

# ── 영제교 청크 ─────────────────────────────────────────
_YEONGJEOGYO_CHUNKS = [
    ChunkData(
        chunk_id="yeo_history",
        section="역사",
        title="금천 위 영제교",
        content="영제교는 경복궁 내 금천(禁川) 위에 놓인 돌다리로, 풍수적으로 나쁜 기운을 씻어내는 경계 역할을 합니다. 다리 난간에는 천록(天祿)을 비롯한 서수(상서로운 동물) 조각이 새겨져 있어 예술적·상징적 의미가 큽니다.",
        keywords=["금천", "풍수", "천록", "서수", "돌다리"],
    ),
]

# ── 전체 데이터 레지스트리 ────────────────────────────────
HERITAGE_DB: dict[str, HeritageData] = {
    "geunjeongjeon": HeritageData(
        heritage_id="geunjeongjeon",
        title="근정전",
        palace="경복궁",
        short_description="경복궁의 정전(正殿)으로, 왕의 즉위식과 조회가 열린 곳입니다.",
        chunks=_GEUNJEONGJEON_CHUNKS,
    ),
    "gyeonghoeru": HeritageData(
        heritage_id="gyeonghoeru",
        title="경회루",
        palace="경복궁",
        short_description="경복궁의 연회장으로, 외국 사신 접대와 과거 시험이 열린 누각입니다.",
        chunks=_GYEONGHOERU_CHUNKS,
    ),
    "gwanghwamun": HeritageData(
        heritage_id="gwanghwamun",
        title="광화문",
        palace="경복궁",
        short_description="경복궁의 정문으로, '빛으로 교화한다'는 뜻을 담고 있습니다.",
        chunks=_GWANGHWAMUN_CHUNKS,
    ),
    "sajeongjeon": HeritageData(
        heritage_id="sajeongjeon",
        title="사정전",
        palace="경복궁",
        short_description="왕이 매일 신하들과 국사를 논의하던 편전입니다.",
        chunks=_SAJEONGJEON_CHUNKS,
    ),
    "sujeongjeon": HeritageData(
        heritage_id="sujeongjeon",
        title="수정전",
        palace="경복궁",
        short_description="집현전이 있던 곳으로, 한글 창제의 산실입니다.",
        chunks=_SUJEONGJEON_CHUNKS,
    ),
    "gangnyeongjeon": HeritageData(
        heritage_id="gangnyeongjeon",
        title="강녕전",
        palace="경복궁",
        short_description="왕의 침전으로, 용마루가 없는 특별한 지붕이 특징입니다.",
        chunks=_GANGNYEONGJEON_CHUNKS,
    ),
    "gyotaejeon": HeritageData(
        heritage_id="gyotaejeon",
        title="교태전",
        palace="경복궁",
        short_description="왕비의 침전이자 아미산 굴뚝(보물 제811호)이 유명합니다.",
        chunks=_GYOTAEJEON_CHUNKS,
    ),
    "jagyeongjeon": HeritageData(
        heritage_id="jagyeongjeon",
        title="자경전",
        palace="경복궁",
        short_description="대왕대비의 거처로, 십장생 굴뚝(보물 제810호)이 유명합니다.",
        chunks=_JAGYEONGJEON_CHUNKS,
    ),
    "hyangwonjeong": HeritageData(
        heritage_id="hyangwonjeong",
        title="향원정",
        palace="경복궁",
        short_description="경복궁 후원 연못 위의 아름다운 육각 정자입니다.",
        chunks=_HYANGWONJEONG_CHUNKS,
    ),
    "geoncheongung": HeritageData(
        heritage_id="geoncheongung",
        title="건청궁",
        palace="경복궁",
        short_description="고종의 거처이자 을미사변의 현장입니다.",
        chunks=_GEONCHEONGUNG_CHUNKS,
    ),
    "jibokjae": HeritageData(
        heritage_id="jibokjae",
        title="집옥재",
        palace="경복궁",
        short_description="고종의 서재이자 경복궁 유일의 중국식 건축입니다.",
        chunks=_JIBOKJAE_CHUNKS,
    ),
    "donggung": HeritageData(
        heritage_id="donggung",
        title="동궁",
        palace="경복궁",
        short_description="세자의 거처이자 학습 공간입니다.",
        chunks=_DONGGUNG_CHUNKS,
    ),
    "taeweonjeon": HeritageData(
        heritage_id="taeweonjeon",
        title="태원전",
        palace="경복궁",
        short_description="선왕의 어진을 모시던 제례 공간입니다.",
        chunks=_TAEWEONJEON_CHUNKS,
    ),
    "sojubang": HeritageData(
        heritage_id="sojubang",
        title="소주방",
        palace="경복궁",
        short_description="왕과 왕비의 수라를 준비하던 궁중 주방입니다.",
        chunks=_SOJUBANG_CHUNKS,
    ),
    "heungbokjeon": HeritageData(
        heritage_id="heungbokjeon",
        title="흥복전",
        palace="경복궁",
        short_description="근정전 동쪽의 보조 전각으로, 현재 국립고궁박물관 부지입니다.",
        chunks=_HEUNGBOKJEON_CHUNKS,
    ),
    "geunjeongmun": HeritageData(
        heritage_id="geunjeongmun",
        title="근정문",
        palace="경복궁",
        short_description="근정전으로 들어가는 정문입니다.",
        chunks=_GEUNJEONGMUN_CHUNKS,
    ),
    "yeongjeogyo": HeritageData(
        heritage_id="yeongjeogyo",
        title="영제교",
        palace="경복궁",
        short_description="금천 위에 놓인 돌다리로, 천록 조각이 유명합니다.",
        chunks=_YEONGJEOGYO_CHUNKS,
    ),
}


def get_heritage(heritage_id: str) -> HeritageData | None:
    return HERITAGE_DB.get(heritage_id)
