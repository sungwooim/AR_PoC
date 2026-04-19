"""
템플릿 기반 해설 생성.

LLM보다 먼저 시도된다. 커버리지 밖의 요청만 LLM으로 넘어간다.
"""

from __future__ import annotations
from models import Audience

# ═══════════════════════════════════════════════════════════════
# 템플릿 레지스트리
# 키: (heritage_id, language, audience)
# ═══════════════════════════════════════════════════════════════

_TEMPLATES: dict[tuple[str, str, Audience], str] = {
    # ── 근정전 ────────────────────────────────────────────

    # short_default (ko)
    ("geunjeongjeon", "ko", Audience.DEFAULT): (
        "근정전은 경복궁의 정전(正殿)으로, 왕의 즉위식과 조회가 열린 곳입니다. "
        "1395년 창건 후 임진왜란으로 소실되었다가 1867년 중건되었습니다. "
        "이중 기단 위의 중층 팔작지붕 건물이며, 앞마당의 품계석과 박석이 특징입니다."
    ),
    # short_default (en)
    ("geunjeongjeon", "en", Audience.DEFAULT): (
        "Geunjeongjeon is the main throne hall of Gyeongbokgung Palace, "
        "where coronations and royal audiences were held. "
        "Originally built in 1395, it was destroyed during the Japanese invasions of 1592 "
        "and reconstructed in 1867."
    ),
    # short_default (ja)
    ("geunjeongjeon", "ja", Audience.DEFAULT): (
        "勤政殿は景福宮の正殿で、王の即位式や朝会が行われた場所です。"
        "1395年に創建され、壬辰倭乱で焼失した後、1867年に再建されました。"
    ),
    # short_default (zh)
    ("geunjeongjeon", "zh", Audience.DEFAULT): (
        "勤政殿是景福宫的正殿，是举行即位仪式和朝会的地方。"
        "始建于1395年，壬辰倭乱中被烧毁，1867年重建。"
    ),

    # short_kid (ko)
    ("geunjeongjeon", "ko", Audience.KID): (
        "근정전은 옛날 임금님이 나라 일을 보던 아주 크고 멋진 건물이에요! 🏯 "
        "지금으로 치면 대통령이 일하는 청와대 같은 곳이죠. "
        "앞마당의 울퉁불퉁한 돌바닥은 햇빛이 눈부시지 않게 해주고, "
        "비 올 때 물이 잘 빠지게 해주는 아주 똑똑한 설계예요!"
    ),
    # short_kid (en)
    ("geunjeongjeon", "en", Audience.KID): (
        "Geunjeongjeon is a super cool building where the king used to do important stuff! 🏯 "
        "Think of it like the White House, but way older — it was built over 600 years ago! "
        "The bumpy stone floor out front isn't a mistake — it stops sunlight from blinding you!"
    ),

    # long_expert (ko)
    ("geunjeongjeon", "ko", Audience.EXPERT): (
        "근정전(勤政殿)은 조선 태조 4년(1395) 경복궁 창건 시 건립된 법궁의 정전으로, "
        "정면 5칸·측면 5칸의 중층 팔작지붕 구조입니다. "
        "이중 기단 형식은 중국 자금성의 태화전과 유사하나, 지붕 위 잡상의 수(11개)는 "
        "조선 고유의 상징 체계를 따릅니다. "
        "1592년 임진왜란으로 소실된 후 270여 년간 폐허였다가, "
        "흥선대원군의 경복궁 중건(1867) 시 현재의 모습으로 재건되었습니다. "
        "어좌(御座) 뒤의 일월오봉도(日月五峯圖)는 왕권의 상징이며, "
        "천장의 칠조룡(七爪龍)은 조선 왕실만이 사용할 수 있는 도안입니다. "
        "앞마당의 박석(薄石)은 눈부심 방지·배수·보행 안정의 삼중 기능을 수행합니다."
    ),

    # ── 경회루 ────────────────────────────────────────────

    ("gyeonghoeru", "ko", Audience.DEFAULT): (
        "경회루는 경복궁 안에 있는 아름다운 누각으로, 외국 사신을 접대하고 "
        "국가 연회를 열던 곳입니다. 국보 제224호로 지정되어 있으며, "
        "48개의 돌기둥 위에 세워진 2층 목조 건물입니다."
    ),
    ("gyeonghoeru", "en", Audience.DEFAULT): (
        "Gyeonghoeru is a stunning pavilion within Gyeongbokgung Palace, "
        "used for state banquets and diplomatic receptions. "
        "Designated as National Treasure No. 224, it stands on 48 stone pillars over an artificial pond."
    ),
    ("gyeonghoeru", "ko", Audience.KID): (
        "경회루는 물 위에 떠 있는 것처럼 보이는 아주 멋진 2층 건물이에요! 🌊 "
        "옛날에 외국에서 손님이 오면 여기서 파티를 열었대요. "
        "48개나 되는 돌기둥이 건물을 떠받치고 있어요!"
    ),

    # ── 광화문 ────────────────────────────────────────────

    ("gwanghwamun", "ko", Audience.DEFAULT): (
        "광화문은 경복궁의 정문으로, '빛으로 교화한다'는 뜻을 담고 있습니다. "
        "1395년 창건되었으며, 일제강점기에 이전되었다가 2010년 원래 위치에 복원되었습니다. "
        "양쪽의 해태상이 궁궐을 지키고 있습니다."
    ),
    ("gwanghwamun", "en", Audience.DEFAULT): (
        "Gwanghwamun is the main gate of Gyeongbokgung Palace, meaning "
        "'Gate of Radiant Transformation.' Built in 1395, it was relocated during "
        "the Japanese colonial period and restored to its original position in 2010."
    ),
    ("gwanghwamun", "ko", Audience.KID): (
        "광화문은 경복궁의 정문이에요! 🦁 "
        "문 앞에 있는 해태는 상상 속 동물인데, 착한 사람과 나쁜 사람을 "
        "구별할 수 있대요. 나쁜 사람은 들어올 수 없죠!"
    ),

    # ── 사정전 ────────────────────────────────────────────
    ("sajeongjeon", "ko", Audience.DEFAULT): (
        "사정전은 왕이 매일 신하들과 국정을 논의하던 편전입니다. "
        "'사정(思政)'이란 '깊이 생각하여 정사를 돌본다'는 뜻으로, "
        "세종대왕은 이곳에서 경연을 1,898회 열고 한글 창제의 밑그림을 그렸습니다."
    ),
    ("sajeongjeon", "en", Audience.DEFAULT): (
        "Sajeongjeon was the king's daily office hall for deliberating state affairs. "
        "'Sajeong' means 'to govern by thinking deeply.' King Sejong held 1,898 royal lectures (Gyeongyeon) "
        "here and conceived the creation of Hangul in this very hall."
    ),
    ("sajeongjeon", "ja", Audience.DEFAULT): (
        "思政殿は王が毎日臣下と国政を議論した便殿です。"
        "「思政」とは「深く考えて政事を行う」という意味で、"
        "世宗大王はここで経筵を1,898回開き、ハングル創製の構想を練りました。"
    ),
    ("sajeongjeon", "zh", Audience.DEFAULT): (
        "思政殿是国王每天与大臣讨论国事的便殿。"
        "「思政」意为「深思熟虑地治理政事」，"
        "世宗大王在此举行1898次经筵，构思了韩文创制。"
    ),

    # ── 수정전 ────────────────────────────────────────────
    ("sujeongjeon", "ko", Audience.DEFAULT): (
        "수정전은 세종대왕의 집현전이 있던 곳으로, 한글이 연구·창제된 산실입니다. "
        "정면 10칸의 경복궁 최장 건물이며, 1894년 갑오개혁 당시 군국기무처가 설치된 역사적 공간입니다."
    ),
    ("sujeongjeon", "en", Audience.DEFAULT): (
        "Sujeongjeon stands on the former site of Jiphyeonjeon (Hall of Worthies), "
        "the birthplace of Hangul. At 10 bays wide, it is the longest hall in Gyeongbokgung, "
        "and housed the Grand Council during the 1894 Gabo Reform."
    ),
    ("sujeongjeon", "ja", Audience.DEFAULT): (
        "修政殿は世宗大王の集賢殿があった場所で、ハングル創製の発祥地です。"
        "正面10間の景福宮で最も長い建物で、1894年の甲午改革の際に軍国機務処が置かれました。"
    ),
    ("sujeongjeon", "zh", Audience.DEFAULT): (
        "修政殿是世宗大王集贤殿所在地，是韩文创制的发源地。"
        "正面十间为景福宫最长建筑，1894年甲午改革时曾设军国机务处。"
    ),

    # ── 강녕전 ────────────────────────────────────────────
    ("gangnyeongjeon", "ko", Audience.DEFAULT): (
        "강녕전은 왕의 침전으로, '강녕(康寧)'은 오복 중 '건강과 편안함'을 의미합니다. "
        "지붕에 용마루가 없는 특별한 구조인데, 왕이 곧 '용'이므로 그 위에 용마루를 올릴 수 없다는 상징적 이유입니다."
    ),
    ("gangnyeongjeon", "en", Audience.DEFAULT): (
        "Gangnyeongjeon is the king's bedchamber, with 'Gangnyeong' meaning 'health and tranquility' — "
        "one of the Five Blessings. Its roof uniquely lacks a ridge (yongmaru), "
        "symbolically because the king himself was the 'dragon.'"
    ),
    ("gangnyeongjeon", "ja", Audience.DEFAULT): (
        "康寧殿は王の寝殿で、「康寧」は五福の一つである「健康と穏やか」を意味します。"
        "屋根に龍棟がない特別な構造で、王が「龍」であるため龍棟を載せないという象徴的な理由からです。"
    ),
    ("gangnyeongjeon", "zh", Audience.DEFAULT): (
        "       “康宁殿是国王的寝殿，”康宁“意为五福中的”健康安宁“。"
        "       “屋顶没有正脊的特殊结构，象征意义在于国王本身就是”龙“，不能在其上再设龙脊。"
    ),

    # ── 교태전 ────────────────────────────────────────────
    ("gyotaejeon", "ko", Audience.DEFAULT): (
        "교태전은 왕비의 공식 거처인 중궁전입니다. '교태(交泰)'는 주역에서 '천지가 어울려 만물이 소통한다'는 뜻이며, "
        "왕(하늘)과 왕비(땅)의 조화를 상징합니다. 뒤편 아미산 정원의 굴뚝은 보물 제811호입니다."
    ),
    ("gyotaejeon", "en", Audience.DEFAULT): (
        "Gyotaejeon is the queen's official residence. 'Gyotae' from the I Ching means "
        "'heaven and earth harmonize,' symbolizing royal harmony. The Amisan chimneys in the rear garden "
        "are designated Treasure No. 811."
    ),
    ("gyotaejeon", "ja", Audience.DEFAULT): (
        "交泰殿は王妃の公式住居である中宮殿です。「交泰」は周易の「天地が交わり万物が通じる」という意味で、"
        "王（天）と王妃（地）の調和を象徴します。裏の峨嵋山庭園の煙突は宝物第811号です。"
    ),
    ("gyotaejeon", "zh", Audience.DEFAULT): (
        "       “交泰殿是王妃的正式居所中宫殿。”交泰“源自《周易》”天地交合万物通泰“，"
        "象征国王（天）与王妃（地）的和谐。后方峨眉山花园的烟囱是宝物第811号。"
    ),

    # ── 자경전 ────────────────────────────────────────────
    ("jagyeongjeon", "ko", Audience.DEFAULT): (
        "자경전은 1865년 흥선대원군이 대왕대비 조씨를 위해 지은 전각으로, "
        "경복궁에서 원형을 가장 잘 보존한 침전입니다. 서쪽 담장의 십장생 굴뚝은 보물 제810호입니다."
    ),
    ("jagyeongjeon", "en", Audience.DEFAULT): (
        "Jagyeongjeon was built in 1865 by Heungseon Daewongun for Queen Dowager Jo. "
        "It is the best-preserved original bedchamber in Gyeongbokgung, and its Ten Longevity chimney "
        "on the western wall is Treasure No. 810."
    ),
    ("jagyeongjeon", "ja", Audience.DEFAULT): (
        "慈慶殿は1865年に興宣大院君が大王大妃の趙氏のために建てた殿閣で、"
        "景福宮で原形を最もよく保存した寝殿です。西側の塀の十長生煙突は宝物第810号です。"
    ),
    ("jagyeongjeon", "zh", Audience.DEFAULT): (
        "慈庆殿是1865年兴宣大院君为大王大妃赵氏所建的殿阁，"
        "是景福宫原貌保存最好的寝殿。西侧围墙上的十长生烟囱是宝物第810号。"
    ),

    # ── 향원정 ────────────────────────────────────────────
    ("hyangwonjeong", "ko", Audience.DEFAULT): (
        "향원정은 1873년 고종이 건청궁과 함께 지은 2층 육각 정자입니다. "
        "'향원'은 '향기가 멀리 퍼진다'는 뜻이며, 연못 위 인공 섬에 자리해 경복궁 최고의 사진 명소로 꼽힙니다."
    ),
    ("hyangwonjeong", "en", Audience.DEFAULT): (
        "Hyangwonjeong is a two-story hexagonal pavilion built in 1873 alongside Geoncheongung. "
        "'Hyangwon' means 'fragrance travels far.' Situated on an artificial island, "
        "it is Gyeongbokgung's most popular photo spot."
    ),
    ("hyangwonjeong", "ja", Audience.DEFAULT): (
        "香遠亭は1873年に高宗が乾清宮と共に建てた二階建て六角亭です。"
        "「香遠」は「香りが遠くまで広がる」という意味で、池の人工島にあり、景福宮随一の撮影スポットです。"
    ),
    ("hyangwonjeong", "zh", Audience.DEFAULT): (
        "香远亭是1873年高宗与乾清宫一起建造的两层六角亭。"
        "       “”香远“意为”香气远播“，坐落于池中人工岛上，是景福宫最佳拍照胜地。"
    ),

    # ── 건청궁 ────────────────────────────────────────────
    ("geoncheongung", "ko", Audience.DEFAULT): (
        "건청궁은 1873년 고종이 친정 시작과 함께 지은 왕의 사저입니다. "
        "1887년 한국 최초의 전등이 설치된 근대화 거점이었으나, "
        "1895년 명성황후가 시해된 을미사변의 비극적 현장이기도 합니다."
    ),
    ("geoncheongung", "en", Audience.DEFAULT): (
        "Geoncheongung was built in 1873 by King Gojong at the start of his direct rule. "
        "It was the site of Korea's first electric lights (1887) — a symbol of modernization — "
        "but also the tragic site of Empress Myeongseong's assassination (Eulmi Incident, 1895)."
    ),
    ("geoncheongung", "ja", Audience.DEFAULT): (
        "乾清宮は1873年に高宗が親政を始める際に建てた王の私邸です。"
        "1887年に韓国初の電灯が設置された近代化の拠点でしたが、"
        "1895年に明成皇后が暗殺された乙未事変の悲劇的な現場でもあります。"
    ),
    ("geoncheongung", "zh", Audience.DEFAULT): (
        "乾清宫是1873年高宗开始亲政时建造的国王私邸。"
        "1887年此处安装了韩国最早的电灯，成为近代化的象征，"
        "但1895年也是明成皇后在乙未事变中遇害的悲剧现场。"
    ),

    # ── 집옥재 ────────────────────────────────────────────
    ("jibokjae", "ko", Audience.DEFAULT): (
        "집옥재는 1891년 고종이 지은 개인 서재이자 외교 접견실입니다. "
        "'집옥'은 '옥(보배로운 지식)을 모으는 곳'이라는 뜻이며, 경복궁에서 유일하게 중국식 건축으로 지어졌습니다."
    ),
    ("jibokjae", "en", Audience.DEFAULT): (
        "Jibokjae was built in 1891 as King Gojong's private library and diplomatic reception room. "
        "'Jibok' means 'a place to collect jade (precious knowledge).' "
        "It is the only Chinese-style building in Gyeongbokgung."
    ),
    ("jibokjae", "ja", Audience.DEFAULT): (
        "集玉斎は1891年に高宗が建てた個人書斎兼外交接見室です。"
        "「集玉」は「玉（貴重な知識）を集める場所」という意味で、景福宮唯一の中国式建築です。"
    ),
    ("jibokjae", "zh", Audience.DEFAULT): (
        "集玉斋是1891年高宗建造的私人书房兼外交接见室。"
        "       “”集玉“意为”收集玉石（珍贵知识）之所“，是景福宫唯一的中国式建筑。"
    ),

    # ── 동궁 ────────────────────────────────────────────
    ("donggung", "ko", Audience.DEFAULT): (
        "동궁은 조선 왕세자의 거처이자 학습 공간으로, 해가 뜨는 동쪽에 배치되었습니다. "
        "세자는 이곳의 비현각에서 서연(書筵)을 통해 제왕학을 익히고, 자선당에서 생활했습니다."
    ),
    ("donggung", "en", Audience.DEFAULT): (
        "Donggung served as the Joseon crown prince's residence and study, "
        "positioned on the eastern (sunrise) side. The prince studied the art of kingship at Bihyeongak "
        "through royal lectures (Seoyeon) and resided at Jaseondang."
    ),
    ("donggung", "ja", Audience.DEFAULT): (
        "東宮は朝鮮の王世子の住まいであり学習空間で、日の出の方角である東側に配置されました。"
        "世子は丕顕閣で書筵を通して帝王学を学び、資善堂で生活しました。"
    ),
    ("donggung", "zh", Audience.DEFAULT): (
        "东宫是朝鲜王世子的居所兼学习空间，位于日出的东侧。"
        "世子在丕显阁通过书筵学习帝王之学，在资善堂生活。"
    ),

    # ── 태원전 ────────────────────────────────────────────
    ("taeweonjeon", "ko", Audience.DEFAULT): (
        "태원전은 경복궁 서쪽의 전각으로, 돌아가신 선왕의 어진(초상화)을 모시는 혼전 역할을 했습니다. "
        "왕이 승하하면 3년간 이곳에서 제사를 올린 뒤 종묘로 이안했습니다."
    ),
    ("taeweonjeon", "en", Audience.DEFAULT): (
        "Taeweonjeon in the western area of Gyeongbokgung served as a honjeon (spirit hall) "
        "enshrining royal portraits of deceased kings. Ancestral rites were held here for three years "
        "before tablets were transferred to Jongmyo Shrine."
    ),
    ("taeweonjeon", "ja", Audience.DEFAULT): (
        "泰元殿は景福宮の西側の殿閣で、亡くなった先王の御真を祀る魂殿の役割を果たしました。"
        "王が崩御すると3年間ここで祭祀を行った後、宗廟に移しました。"
    ),
    ("taeweonjeon", "zh", Audience.DEFAULT): (
        "泰元殿位于景福宫西侧，用作供奉已故先王御真（肖像画）的魂殿。"
        "国王驾崩后在此举行三年祭祀，然后移至宗庙。"
    ),

    # ── 소주방 ────────────────────────────────────────────
    ("sojubang", "ko", Audience.DEFAULT): (
        "소주방은 왕과 왕비의 일상 식사인 '수라'를 준비하던 궁중 주방입니다. "
        "강녕전과 교태전 사이에 위치하여 신속한 배선이 가능했으며, 숙수와 상궁들이 하루 5회 수라상을 차렸습니다."
    ),
    ("sojubang", "en", Audience.DEFAULT): (
        "Sojubang was the royal kitchen where 'Sura' (the king and queen's meals) were prepared. "
        "Located between Gangnyeongjeon and Gyotaejeon for quick serving, "
        "master cooks and court ladies prepared five meal services daily."
    ),
    ("sojubang", "ja", Audience.DEFAULT): (
        "焼厨房は王と王妃の日常の食事「水刺」を準備した宮中の厨房です。"
        "康寧殿と交泰殿の間に位置し迅速な配膳が可能で、熟手と尚宮たちが1日5回水刺膳を準備しました。"
    ),
    ("sojubang", "zh", Audience.DEFAULT): (
        "       “烧厨房是准备国王和王妃日常膳食”水刺“的宫廷厨房。"
        "位于康宁殿与交泰殿之间便于快速送餐，熟手和尚宫们每天准备五次膳食。"
    ),

    # ── 흥복전 ────────────────────────────────────────────
    ("heungbokjeon", "ko", Audience.DEFAULT): (
        "흥복전은 '복을 일으킨다'는 뜻의 보조 전각으로 근정전 동쪽에 위치했습니다. "
        "일제강점기에 철거되어 그 자리에 현재 국립고궁박물관이 들어서 있습니다."
    ),
    ("heungbokjeon", "en", Audience.DEFAULT): (
        "Heungbokjeon ('Hall That Raises Fortune') was an auxiliary hall east of Geunjeongjeon. "
        "Demolished during the Japanese colonial period, "
        "its former site is now occupied by the National Palace Museum of Korea."
    ),
    ("heungbokjeon", "ja", Audience.DEFAULT): (
        "興福殿は「福を興す」という意味の補助殿閣で、勤政殿の東側に位置していました。"
        "日本統治時代に撤去され、その場所に現在の国立古宮博物館が建っています。"
    ),
    ("heungbokjeon", "zh", Audience.DEFAULT): (
        "       “兴福殿是”兴起福气“之意的辅助殿阁，位于勤政殿东侧。"
        "日据时期被拆除，现在国立故宫博物馆建在其旧址上。"
    ),

    # ── 근정문 ────────────────────────────────────────────
    ("geunjeongmun", "ko", Audience.DEFAULT): (
        "근정문은 근정전 앞마당(조정)으로 들어가는 중층 문루의 정문입니다. "
        "문 안쪽 가운데 길인 어도(御道)는 왕만이 걸을 수 있었고, 신하들은 좌우 길로 품계석에 따라 섰습니다."
    ),
    ("geunjeongmun", "en", Audience.DEFAULT): (
        "Geunjeongmun is the double-story main gate leading to Geunjeongjeon's front courtyard (Jojeong). "
        "The central path inside, the Eodo, was reserved exclusively for the king, "
        "while officials took positions by rank stones along the side paths."
    ),
    ("geunjeongmun", "ja", Audience.DEFAULT): (
        "勤政門は勤政殿の前庭（朝廷）に入る重層門楼の正門です。"
        "門の内側の真ん中の道である御道は王だけが歩け、臣下たちは左右の道で品階石に従って立ちました。"
    ),
    ("geunjeongmun", "zh", Audience.DEFAULT): (
        "勤政门是通往勤政殿前庭（朝廷）的重层门楼正门。"
        "门内中间的御道只有国王才能行走，大臣们则按品阶石站在左右两侧道上。"
    ),

    # ── 영제교 ────────────────────────────────────────────
    ("yeongjeogyo", "ko", Audience.DEFAULT): (
        "영제교는 경복궁 내 금천 위에 놓인 돌다리로, 풍수적으로 나쁜 기운을 씻어내는 경계 역할을 합니다. "
        "난간에는 천록을 비롯한 상서로운 동물 조각이 새겨져 있어 예술적 가치도 높습니다."
    ),
    ("yeongjeogyo", "en", Audience.DEFAULT): (
        "Yeongjeogyo is a stone bridge over the Geumcheon stream within Gyeongbokgung, "
        "serving as a feng shui boundary that cleanses negative energy. "
        "Its railings bear carvings of auspicious creatures including Cheonrok, adding artistic value."
    ),
    ("yeongjeogyo", "ja", Audience.DEFAULT): (
        "永済橋は景福宮内の禁川の上に架けられた石橋で、風水的に悪い気を洗い流す境界の役割を果たします。"
        "欄干には天禄をはじめとする瑞獣の彫刻が施され、芸術的価値も高いです。"
    ),
    ("yeongjeogyo", "zh", Audience.DEFAULT): (
        "永济桥是横跨景福宫内禁川的石桥，从风水角度看具有洗涤不良气息的分界作用。"
        "栏杆上雕刻有天禄等瑞兽，艺术价值颇高。"
    ),
}


def get_template(
    heritage_id: str,
    language: str,
    audience: Audience,
) -> str | None:
    """정확히 매칭되는 템플릿을 반환. 없으면 None."""
    return _TEMPLATES.get((heritage_id, language, audience))


def get_template_with_fallback(
    heritage_id: str,
    language: str,
    audience: Audience,
) -> tuple[str | None, Audience]:
    """
    정확 매칭 → DEFAULT 폴백 → ko DEFAULT 폴백 순으로 시도.
    반환: (text, 실제 사용된 audience)
    """
    # 1. 정확 매칭
    t = get_template(heritage_id, language, audience)
    if t is not None:
        return t, audience

    # 2. 같은 언어의 DEFAULT로 폴백
    if audience != Audience.DEFAULT:
        t = get_template(heritage_id, language, Audience.DEFAULT)
        if t is not None:
            return t, Audience.DEFAULT

    # 3. 한국어 DEFAULT로 폴백
    if language != "ko":
        t = get_template(heritage_id, "ko", Audience.DEFAULT)
        if t is not None:
            return t, Audience.DEFAULT

    return None, audience
