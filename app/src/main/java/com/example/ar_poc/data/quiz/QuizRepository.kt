package com.example.ar_poc.data.quiz

import com.example.ar_poc.domain.model.QuizChoice
import com.example.ar_poc.domain.model.QuizQuestion

/**
 * 건물별 퀴즈 문제 저장소 — 외부 API 없이 하드코딩된 4지선다 문항.
 * 각 문제는 HeritageChunk 내용에서 도출되었습니다.
 */
class QuizRepository {

    private val questions: List<QuizQuestion> = buildList {

        // ──────────────────────────────────────────────
        // 근정전 (geunjeongjeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "geunjeongjeon",
            questionKo = "근정전 앞마당 박석의 역할로 가장 올바른 것은?",
            questionEn = "What is the main purpose of the rough stone paving (Baksuk) in the courtyard of Geunjeongjeon?",
            questionJa = "勤政殿の前庭にある薄石（バクソク）の主な役割は何ですか？",
            questionZh = "勤政殿前院薄石（박석）的主要作用是什么？",
            choices = listOf(
                QuizChoice("무기 제조에 사용한 재료", "Material used to make weapons", "武器製造に使われた材料", "用于制造武器的材料"),
                QuizChoice("눈부심 방지 및 배수", "To reduce glare and drain water", "眩しさ防止と排水", "防眩光和排水"),
                QuizChoice("왕의 가마 충격 완화", "To cushion the royal palanquin", "王の輿の衝撃緩和", "缓冲王轿的冲击"),
                QuizChoice("불의 전파를 막는 방화재", "Fireproof barrier", "火の延焼を防ぐ防火材", "防火屏障")
            ),
            correctIndex = 1
        ))

        add(QuizQuestion(
            heritageId = "geunjeongjeon",
            questionKo = "근정전 지붕에 줄지어 있는 잡상(작은 조각상)의 첫 번째는?",
            questionEn = "What does the first Japsang (small roof figure) on Geunjeongjeon represent?",
            questionJa = "勤政殿の屋根に並ぶ雑像、その最初の像は何を表していますか？",
            questionZh = "勤政殿屋顶上排列的杂像，第一个是什么？",
            choices = listOf(
                QuizChoice("용(龍)", "Dragon", "龍（りゅう）", "龙"),
                QuizChoice("호랑이", "Tiger", "虎", "老虎"),
                QuizChoice("삼장법사(선재동자)", "Monk (Sanzang/Sudhana)", "三蔵法師（善財童子）", "三藏法师（善财童子）"),
                QuizChoice("불사조", "Phoenix", "不死鳥", "凤凰")
            ),
            correctIndex = 2
        ))

        add(QuizQuestion(
            heritageId = "geunjeongjeon",
            questionKo = "근정전 월대(2단 기단)의 네 모서리와 계단 양쪽에 배치된 동물 석상은?",
            questionEn = "What stone animals are placed at the four corners and stairways of Geunjeongjeon's Woldae (raised platform)?",
            questionJa = "勤政殿の月台（2段基壇）の四隅と階段の両側に配置された動物の石像は？",
            questionZh = "勤政殿月台四角和台阶两侧放置的动物石像是什么？",
            choices = listOf(
                QuizChoice("십이지신상", "Twelve Zodiac Figures", "十二支神像", "十二生肖像"),
                QuizChoice("해태(해치)", "Haetae (Haechi)", "獬豸（ヘテ）", "獬豸（해치）"),
                QuizChoice("호랑이와 거북이", "Tiger and Turtle", "虎と亀", "老虎和乌龟"),
                QuizChoice("사자와 용", "Lion and Dragon", "ライオンと龍", "狮子和龙")
            ),
            correctIndex = 0
        ))

        // ──────────────────────────────────────────────
        // 경회루 (gyeonghoeru)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "gyeonghoeru",
            questionKo = "경회루를 지탱하는 돌기둥은 모두 몇 개인가?",
            questionEn = "How many stone pillars support Gyeonghoeru Pavilion?",
            questionJa = "慶会楼を支える石柱はすべで何本ありますか？",
            questionZh = "支撑庆会楼的石柱共有多少根？",
            choices = listOf(
                QuizChoice("24개", "24", "24本", "24根"),
                QuizChoice("36개", "36", "36本", "36根"),
                QuizChoice("48개", "48", "48本", "48根"),
                QuizChoice("60개", "60", "60本", "60根")
            ),
            correctIndex = 2
        ))

        add(QuizQuestion(
            heritageId = "gyeonghoeru",
            questionKo = "1997년 경회루 연못 준설 중 발견된 것은 무엇인가?",
            questionEn = "What was discovered during the dredging of Gyeonghoeru Pond in 1997?",
            questionJa = "1997年の慶会楼の池の浚渫中に発見されたものは何ですか？",
            questionZh = "1997年疏浚庆会楼池塘时发现了什么？",
            choices = listOf(
                QuizChoice("금관", "Gold crown", "金冠", "金冠"),
                QuizChoice("청동 용 조각", "Bronze dragon sculpture", "青銅の龍の彫刻", "铜龙雕塑"),
                QuizChoice("조선왕조 옥새", "Royal seal of Joseon", "朝鮮王朝の御璽", "朝鲜王朝玉玺"),
                QuizChoice("도자기 항아리", "Ceramic jar", "陶磁器の甕", "陶瓷罐")
            ),
            correctIndex = 1
        ))

        add(QuizQuestion(
            heritageId = "gyeonghoeru",
            questionKo = "경회루 안쪽 원형 기둥이 상징하는 것은?",
            questionEn = "What do the inner round pillars of Gyeonghoeru symbolize?",
            questionJa = "慶会楼の内側の円柱が象徴するものは何ですか？",
            questionZh = "庆会楼内侧圆柱象征什么？",
            choices = listOf(
                QuizChoice("땅(지)", "Earth", "地", "地"),
                QuizChoice("하늘(천)", "Heaven", "天", "天"),
                QuizChoice("물(수)", "Water", "水", "水"),
                QuizChoice("불(화)", "Fire", "火", "火")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 광화문 (gwanghwamun)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "gwanghwamun",
            questionKo = "광화문(光化門)이라는 이름의 뜻은?",
            questionEn = "What does the name 'Gwanghwamun (光化門)' mean?",
            questionJa = "「光化門」という名前の意味は何ですか？",
            questionZh = "「光化门」这个名字是什么意思？",
            choices = listOf(
                QuizChoice("빛으로 세상을 교화하는 문", "Gate that enlightens the world with light", "光で世を教化する門", "以光明教化天下之门"),
                QuizChoice("왕의 빛나는 영광의 문", "Gate of the king's shining glory", "王の輝かしい栄光の門", "王者荣耀之门"),
                QuizChoice("동쪽에서 뜨는 태양의 문", "Gate of the rising sun from the east", "東から昇る太陽の門", "东方旭日之门"),
                QuizChoice("백성을 지키는 빛의 문", "Gate of light protecting the people", "民を守る光の門", "守护百姓的光明之门")
            ),
            correctIndex = 0
        ))

        add(QuizQuestion(
            heritageId = "gwanghwamun",
            questionKo = "조선시대에 광화문 가운데 문(어문)을 통과할 수 있었던 사람은?",
            questionEn = "Who was allowed to pass through the central gate (Royal Gate) of Gwanghwamun during the Joseon Dynasty?",
            questionJa = "朝鮮時代に光化門の中央の門（御門）を通ることができたのは誰ですか？",
            questionZh = "朝鲜时代，谁可以通过光化门中间的御门？",
            choices = listOf(
                QuizChoice("정1품 이상의 고위 관료", "High officials of First Rank or above", "正一品以上の高位官僚", "正一品以上的高级官员"),
                QuizChoice("임금(왕)만", "Only the king", "王のみ", "只有国王"),
                QuizChoice("왕과 왕비만", "Only the king and queen", "王と王妃のみ", "只有国王和王后"),
                QuizChoice("외국 사신", "Foreign envoys", "外国使節", "外国使节")
            ),
            correctIndex = 1
        ))

        add(QuizQuestion(
            heritageId = "gwanghwamun",
            questionKo = "광화문의 좌우에 위치하는 전통 수호 동물은?",
            questionEn = "What traditional guardian animals are placed on either side of Gwanghwamun?",
            questionJa = "光化門の左右に位置する伝統的な守護動物は何ですか？",
            questionZh = "光化门左右两侧的传统守护神兽是什么？",
            choices = listOf(
                QuizChoice("용(龍)", "Dragons", "龍", "龙"),
                QuizChoice("호랑이", "Tigers", "虎", "老虎"),
                QuizChoice("해태(해치)", "Haetae (Haechi)", "獬豸（ヘテ）", "獬豸"),
                QuizChoice("사자", "Lions", "獅子", "狮子")
            ),
            correctIndex = 2
        ))

        // ──────────────────────────────────────────────
        // 사정전 (sajeongjeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "sajeongjeon",
            questionKo = "사정전(思政殿)의 이름 뜻은?",
            questionEn = "What does the name 'Sajeongjeon (思政殿)' mean?",
            questionJa = "思政殿の名前の意味は何ですか？",
            questionZh = "“思政殿”的名字是什么意思？",
            choices = listOf(
                QuizChoice("정치를 바로잡다", "To correct politics", "政治を正す", "矫正政治"),
                QuizChoice("깊이 생각하여 정사를 돌보다", "To govern by thinking deeply", "深く考えて政事を行う", "深思熟虑治理政事"),
                QuizChoice("사방을 다스리다", "To rule in all directions", "四方を治める", "治理四方"),
                QuizChoice("백성의 뜻을 생각하다", "To think of the people's will", "民の意志を考える", "思虑百姓之意")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "sajeongjeon",
            questionKo = "세종대왕이 사정전에서 열었다고 기록된 경연(학문 토론회)의 횟수는?",
            questionEn = "How many royal lectures (Gyeongyeon) did King Sejong hold, mostly at Sajeongjeon?",
            questionJa = "世宗大王が主に思政殿で開いた経筵の回数は？",
            questionZh = "世宗大王主要在思政殿举行了多少次经筵？",
            choices = listOf(
                QuizChoice("약 500회", "About 500", "約500回", "约500次"),
                QuizChoice("약 1,000회", "About 1,000", "約1,000回", "约1000次"),
                QuizChoice("약 1,898회", "About 1,898", "約1,898回", "约1898次"),
                QuizChoice("약 3,000회", "About 3,000", "約3,000回", "约3000次")
            ),
            correctIndex = 2
        ))
        add(QuizQuestion(
            heritageId = "sajeongjeon",
            questionKo = "사정전 좌우에 날개처럼 배치된 부속 건물의 이름은?",
            questionEn = "What are the names of the flanking buildings beside Sajeongjeon?",
            questionJa = "思政殿の左右に翼のように配置された付属建物の名前は？",
            questionZh = "思政殿两侧如翼般配置的附属建筑叫什么？",
            choices = listOf(
                QuizChoice("연생전과 경성전", "Yeongsaengjeon and Gyeongseongjeon", "延生殿と慶成殿", "延生殿和庆成殿"),
                QuizChoice("만춘전과 천추전", "Manchunjeon and Cheonchujeon", "萬春殿と千秋殿", "万春殿和千秋殿"),
                QuizChoice("자선당과 비현각", "Jaseondang and Bihyeongak", "資善堂と丕顯閣", "资善堂和丕显阁"),
                QuizChoice("협길당과 팔우정", "Hyeopgildang and Parujeong", "協吉堂と八隅亭", "协吉堂和八隅亭")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 수정전 (sujeongjeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "sujeongjeon",
            questionKo = "수정전 일대에 있었던 조선 최고의 학문 기관은?",
            questionEn = "What was the premier scholarly institution located in the Sujeongjeon area?",
            questionJa = "修政殿一帯にあった朝鮮最高の学問機関は？",
            questionZh = "修政殿一带曾有的朝鲜最高学术机构是什么？",
            choices = listOf(
                QuizChoice("성균관", "Seonggyungwan", "成均館", "成均馆"),
                QuizChoice("집현전", "Jiphyeonjeon (Hall of Worthies)", "集賢殿", "集贤殿"),
                QuizChoice("규장각", "Kyujanggak", "奎章閣", "奎章阁"),
                QuizChoice("홍문관", "Hongmungwan", "弘文館", "弘文馆")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "sujeongjeon",
            questionKo = "1894년 수정전에서 이루어진 역사적 사건은?",
            questionEn = "What historic event took place at Sujeongjeon in 1894?",
            questionJa = "1894年に修政殿で起きた歴史的な出来事は？",
            questionZh = "1894年在修政殿发生的历史事件是？",
            choices = listOf(
                QuizChoice("임진왜란 종전 협상", "Imjin War peace negotiations", "壬辰倭乱の終戦交渉", "壬辰倭乱停战谈判"),
                QuizChoice("갑오개혁 (군국기무처)", "Gabo Reform (Grand Council)", "甲午改革（軍国機務処）", "甲午改革（军国机务处）"),
                QuizChoice("한일합병 조약 체결", "Korea-Japan Annexation Treaty", "韓日併合条約の締結", "韩日合并条约签订"),
                QuizChoice("독립 선언문 작성", "Declaration of Independence drafting", "独立宣言文の作成", "独立宣言书起草")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "sujeongjeon",
            questionKo = "수정전이 경복궁 전각 중 독특한 건축적 특징은?",
            questionEn = "What is Sujeongjeon's unique architectural feature among Gyeongbokgung's halls?",
            questionJa = "修政殿が景福宮の殿閣の中で独特な建築的特徴は？",
            questionZh = "修政殿在景福宫殿阁中有什么独特的建筑特征？",
            choices = listOf(
                QuizChoice("유일한 2층 건물", "Only two-story building", "唯一の2階建て", "唯一的两层建筑"),
                QuizChoice("가장 긴 전각 (정면 10칸)", "Longest hall (10 bays)", "最も長い殿閣（正面10間）", "最长的殿阁（正面十间）"),
                QuizChoice("유일한 중국식 건물", "Only Chinese-style building", "唯一の中国式建物", "唯一的中国式建筑"),
                QuizChoice("유일한 원형 건물", "Only circular building", "唯一の円形建物", "唯一的圆形建筑")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 강녕전 (gangnyeongjeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "gangnyeongjeon",
            questionKo = "강녕전 지붕의 가장 독특한 특징은?",
            questionEn = "What is the most distinctive feature of Gangnyeongjeon's roof?",
            questionJa = "康寧殿の屋根の最も独特な特徴は？",
            questionZh = "康宁殿屋顶最独特的特征是什么？",
            choices = listOf(
                QuizChoice("청기와를 사용", "Uses blue tiles", "青瓦を使用", "使用青瓦"),
                QuizChoice("용마루가 없음", "No roof ridge (yongmaru)", "龍棟がない", "没有正脊"),
                QuizChoice("금박으로 장식", "Decorated with gold leaf", "金箔で装飾", "金箔装饰"),
                QuizChoice("3층 지붕 구조", "Three-tiered roof structure", "三層の屋根構造", "三层屋顶结构")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "gangnyeongjeon",
            questionKo = "강녕전이 경복궁 역사상 소실된 횟수는?",
            questionEn = "How many times was Gangnyeongjeon destroyed by fire in Gyeongbokgung's history?",
            questionJa = "康寧殿が景福宮の歴史上焼失した回数は？",
            questionZh = "康宁殿在景福宫历史上被烧毁了几次？",
            choices = listOf(
                QuizChoice("1번", "Once", "1回", "1次"),
                QuizChoice("2번", "Twice", "2回", "2次"),
                QuizChoice("3번 이상", "Three or more times", "3回以上", "3次以上"),
                QuizChoice("소실된 적 없음", "Never destroyed", "焼失したことはない", "从未被烧毁")
            ),
            correctIndex = 2
        ))
        add(QuizQuestion(
            heritageId = "gangnyeongjeon",
            questionKo = "강녕전(康寧殿)에서 '강녕'이 뜻하는 것은?",
            questionEn = "What does 'Gangnyeong' in Gangnyeongjeon mean?",
            questionJa = "康寧殿の「康寧」が意味するものは？",
            questionZh = "“康宁殿”中“康宁”的含义是？",
            choices = listOf(
                QuizChoice("강한 힘", "Strong power", "強い力", "强大的力量"),
                QuizChoice("건강하고 편안함 (오복 중 하나)", "Health and tranquility (one of Five Blessings)", "健康と穏やかさ（五福の一つ）", "健康安宁（五福之一）"),
                QuizChoice("강인한 정신", "Strong spirit", "強靭な精神", "坚强的精神"),
                QuizChoice("영원한 평화", "Eternal peace", "永遠の平和", "永恒的和平")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 교태전 (gyotaejeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "gyotaejeon",
            questionKo = "교태전 뒤편 아미산 굴뚝의 문화재 등급은?",
            questionEn = "What is the cultural heritage designation of the Amisan chimneys behind Gyotaejeon?",
            questionJa = "交泰殿の裏の峨嵋山の煙突の文化財等級は？",
            questionZh = "交泰殿后方峨眉山烟囱的文化遗产等级是？",
            choices = listOf(
                QuizChoice("국보", "National Treasure", "国宝", "国宝"),
                QuizChoice("보물 제811호", "Treasure No. 811", "宝物第811号", "宝物第811号"),
                QuizChoice("사적", "Historic Site", "史跡", "史迹"),
                QuizChoice("등록문화재", "Registered Cultural Heritage", "登録文化財", "登录文化遗产")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "gyotaejeon",
            questionKo = "교태전(交泰殿)의 이름이 의미하는 것은?",
            questionEn = "What does the name 'Gyotaejeon (交泰殿)' mean?",
            questionJa = "交泰殿の名前が意味するものは？",
            questionZh = "“交泰殿”的名字意味着什么？",
            choices = listOf(
                QuizChoice("왕비의 아름다운 거처", "The queen's beautiful residence", "王妃の美しい住まい", "王妃美丽的居所"),
                QuizChoice("천지가 어울려 만물이 소통함", "Heaven and earth harmonize, all things communicate", "天地が交わり万物が通じる", "天地交合万物通泰"),
                QuizChoice("태양과 달의 만남", "Meeting of sun and moon", "太陽と月の出会い", "日月相会"),
                QuizChoice("태평성대의 시작", "Beginning of an era of peace", "太平盛代の始まり", "太平盛世的开始")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "gyotaejeon",
            questionKo = "교태전 아미산 정원의 특별한 점은?",
            questionEn = "What makes the Amisan Garden of Gyotaejeon special?",
            questionJa = "交泰殿の峨嵋山庭園の特別な点は？",
            questionZh = "交泰殿峨眉山花园有什么特别之处？",
            choices = listOf(
                QuizChoice("경복궁 유일의 왕비 전용 정원", "Only garden exclusively for the queen", "景福宮唯一の王妃専用庭園", "景福宫唯一的王妃专用花园"),
                QuizChoice("가장 넓은 정원", "Largest garden", "最も広い庭園", "最大的花园"),
                QuizChoice("약초를 재배한 정원", "Herb garden", "薬草を栽培した庭園", "种植药草的花园"),
                QuizChoice("동물을 기르던 정원", "Animal-keeping garden", "動物を飼っていた庭園", "养殖动物的花园")
            ),
            correctIndex = 0
        ))

        // ──────────────────────────────────────────────
        // 자경전 (jagyeongjeon)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "jagyeongjeon",
            questionKo = "자경전 십장생 굴뚝의 문화재 등급은?",
            questionEn = "What is the heritage designation of Jagyeongjeon's Ten Longevity chimney?",
            questionJa = "慈慶殿の十長生煙突の文化財等級は？",
            questionZh = "慈庆殿十长生烟囱的文化遗产等级是？",
            choices = listOf(
                QuizChoice("국보", "National Treasure", "国宝", "国宝"),
                QuizChoice("보물 제810호", "Treasure No. 810", "宝物第810号", "宝物第810号"),
                QuizChoice("사적", "Historic Site", "史跡", "史迹"),
                QuizChoice("등록문화재", "Registered Cultural Heritage", "登録文化財", "登录文化遗产")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "jagyeongjeon",
            questionKo = "자경전이 누구를 위해 지어진 전각인가?",
            questionEn = "For whom was Jagyeongjeon built?",
            questionJa = "慈慶殿は誰のために建てられた殿閣ですか？",
            questionZh = "慈庆殿是为谁建造的殿阁？",
            choices = listOf(
                QuizChoice("왕비", "The queen", "王妃", "王妃"),
                QuizChoice("대왕대비 (왕의 할머니)", "Queen Dowager (king's grandmother)", "大王大妃（王の祖母）", "大王大妃（国王的祖母）"),
                QuizChoice("세자", "The crown prince", "世子", "世子"),
                QuizChoice("후궁", "Royal concubine", "後宮", "后宫")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "jagyeongjeon",
            questionKo = "자경전이 경복궁에서 특별한 이유는?",
            questionEn = "Why is Jagyeongjeon special in Gyeongbokgung?",
            questionJa = "慈慶殿が景福宮で特別な理由は？",
            questionZh = "慈庆殿在景福宫中为何特别？",
            choices = listOf(
                QuizChoice("가장 큰 전각이라서", "Because it's the largest hall", "最も大きい殿閣だから", "因为是最大的殿阁"),
                QuizChoice("원형을 가장 잘 보존한 침전이라서", "Best-preserved original bedchamber", "原形を最もよく保存した寝殿だから", "因为是原貌保存最好的寝殿"),
                QuizChoice("가장 오래된 건물이라서", "Because it's the oldest building", "最も古い建物だから", "因为是最古老的建筑"),
                QuizChoice("유일한 3층 건물이라서", "Because it's the only 3-story building", "唯一の3階建てだから", "因为是唯一的三层建筑")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 향원정 (hyangwonjeong)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "hyangwonjeong",
            questionKo = "향원정으로 건너가는 나무 다리의 이름은?",
            questionEn = "What is the name of the wooden bridge leading to Hyangwonjeong?",
            questionJa = "香遠亭に渡る木の橋の名前は？",
            questionZh = "通往香远亭的木桥叫什么名字？",
            choices = listOf(
                QuizChoice("영제교", "Yeongjeogyo", "永済橋", "永济桥"),
                QuizChoice("취향교", "Chwihyanggyo", "醉香橋", "醉香桥"),
                QuizChoice("금천교", "Geumcheongyo", "禁川橋", "禁川桥"),
                QuizChoice("오작교", "Ojakgyo", "烏鵲橋", "乌鹊桥")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "hyangwonjeong",
            questionKo = "'향원(香遠)'이라는 이름은 어디에서 따온 것인가?",
            questionEn = "Where does the name 'Hyangwon (香遠)' come from?",
            questionJa = "「香遠」という名前はどこから取ったものですか？",
            questionZh = "“香远”这个名字出自哪里？",
            choices = listOf(
                QuizChoice("논어", "The Analects", "論語", "论语"),
                QuizChoice("주돈이의 애련설", "Zhou Dunyi's essay on loving the lotus", "周敦頤の愛蓮説", "周敦颐的《爱莲说》"),
                QuizChoice("삼국지", "Romance of the Three Kingdoms", "三国志", "三国志"),
                QuizChoice("시경", "Book of Songs", "詩経", "诗经")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "hyangwonjeong",
            questionKo = "2021년 취향교가 복원된 위치는?",
            questionEn = "To which side was Chwihyanggyo bridge restored in 2021?",
            questionJa = "2021年に醉香橋が復元された位置は？",
            questionZh = "2021年醉香桥被恢复到了哪一侧？",
            choices = listOf(
                QuizChoice("남쪽 (현재 위치 유지)", "South (kept current position)", "南側（現在の位置を維持）", "南侧（保持现有位置）"),
                QuizChoice("북쪽 (원래 위치로)", "North (original position)", "北側（元の位置へ）", "北侧（恢复原位）"),
                QuizChoice("동쪽으로 이동", "Moved to east", "東側に移動", "移至东侧"),
                QuizChoice("서쪽으로 이동", "Moved to west", "西側に移動", "移至西侧")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 건청궁 (geoncheongung)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "geoncheongung",
            questionKo = "건청궁에서 1895년에 일어난 비극적 사건은?",
            questionEn = "What tragic event occurred at Geoncheongung in 1895?",
            questionJa = "乾清宮で1895年に起きた悲劇的な事件は？",
            questionZh = "1895年在乾清宫发生的悲剧事件是？",
            choices = listOf(
                QuizChoice("대화재로 궁궐 소실", "Palace destroyed by great fire", "大火災で宮殿焼失", "大火烧毁宫殿"),
                QuizChoice("을미사변 (명성황후 시해)", "Eulmi Incident (Empress Myeongseong's assassination)", "乙未事変（明成皇后暗殺）", "乙未事变（明成皇后被害）"),
                QuizChoice("왕의 폐위", "King's deposition", "王の廃位", "国王被废黜"),
                QuizChoice("외국군 침입", "Foreign military invasion", "外国軍の侵入", "外国军队入侵")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "geoncheongung",
            questionKo = "건청궁에 도입된 한국 최초의 근대 시설은?",
            questionEn = "What modern facility was first introduced to Korea at Geoncheongung?",
            questionJa = "乾清宮に導入された韓国初の近代施設は？",
            questionZh = "乾清宫引入的韩国首个近代设施是？",
            choices = listOf(
                QuizChoice("전화", "Telephone", "電話", "电话"),
                QuizChoice("전기 조명 (전등)", "Electric lighting", "電気照明（電灯）", "电灯照明"),
                QuizChoice("수도 (상수도)", "Running water", "水道", "自来水"),
                QuizChoice("증기 기관차", "Steam locomotive", "蒸気機関車", "蒸汽火车")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "geoncheongung",
            questionKo = "건청궁이 복원된 연도는?",
            questionEn = "In what year was Geoncheongung restored?",
            questionJa = "乾清宮が復元された年は？",
            questionZh = "乾清宫复原的年份是？",
            choices = listOf(
                QuizChoice("1995년", "1995", "1995年", "1995年"),
                QuizChoice("2000년", "2000", "2000年", "2000年"),
                QuizChoice("2007년", "2007", "2007年", "2007年"),
                QuizChoice("2015년", "2015", "2015年", "2015年")
            ),
            correctIndex = 2
        ))

        // ──────────────────────────────────────────────
        // 집옥재 (jibokjae)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "jibokjae",
            questionKo = "집옥재가 경복궁에서 유일한 건축적 특징은?",
            questionEn = "What makes Jibokjae architecturally unique in Gyeongbokgung?",
            questionJa = "集玉斎が景福宮で唯一の建築的特徴は？",
            questionZh = "集玉斋在景福宫中有什么独特的建筑特征？",
            choices = listOf(
                QuizChoice("유일한 3층 건물", "Only 3-story building", "唯一の3階建て", "唯一的三层建筑"),
                QuizChoice("유일한 중국식 건축", "Only Chinese-style architecture", "唯一の中国式建築", "唯一的中国式建筑"),
                QuizChoice("유일한 벽돌 건물", "Only brick building", "唯一のレンガ建築", "唯一的砖砌建筑"),
                QuizChoice("유일한 서양식 건물", "Only Western-style building", "唯一の西洋式建物", "唯一的西式建筑")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "jibokjae",
            questionKo = "집옥재(集玉斎)의 이름 뜻은?",
            questionEn = "What does the name 'Jibokjae (集玉斎)' mean?",
            questionJa = "集玉斎の名前の意味は？",
            questionZh = "“集玉斋”的名字是什么意思？",
            choices = listOf(
                QuizChoice("옥(보배로운 지식)을 모으는 곳", "Place to collect jade (precious knowledge)", "玉（貴重な知識）を集める場所", "收集玉石（珍贵知识）之所"),
                QuizChoice("옥으로 만든 건물", "Building made of jade", "玉で作った建物", "用玉石建造的建筑"),
                QuizChoice("보물을 보관하는 곳", "Place to store treasures", "宝物を保管する場所", "保管宝物的地方"),
                QuizChoice("아름다운 돌의 집", "House of beautiful stones", "美しい石の家", "美丽石头的房子")
            ),
            correctIndex = 0
        ))
        add(QuizQuestion(
            heritageId = "jibokjae",
            questionKo = "집옥재 옆에 연결된 팔각형 건물의 이름은?",
            questionEn = "What is the name of the octagonal building connected to Jibokjae?",
            questionJa = "集玉斎の横に繋がった八角形の建物の名前は？",
            questionZh = "与集玉斋相连的八角形建筑叫什么？",
            choices = listOf(
                QuizChoice("경회루", "Gyeonghoeru", "慶会楼", "庆会楼"),
                QuizChoice("팔우정", "Parujeong", "八隅亭", "八隅亭"),
                QuizChoice("향원정", "Hyangwonjeong", "香遠亭", "香远亭"),
                QuizChoice("자선당", "Jaseondang", "資善堂", "资善堂")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 동궁 (donggung)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "donggung",
            questionKo = "동궁이 궁궐의 동쪽에 위치하는 이유는?",
            questionEn = "Why is Donggung located on the eastern side of the palace?",
            questionJa = "東宮が宮殿の東側に位置する理由は？",
            questionZh = "东宫位于宫殿东侧的原因是？",
            choices = listOf(
                QuizChoice("동쪽이 해가 뜨는 방향으로, 성장하는 미래의 왕을 상징", "East is where the sun rises, symbolizing the growing future king", "東は日の出の方角で、成長する未来の王を象徴", "东方是日出的方向，象征成长中的未来国王"),
                QuizChoice("동쪽에 빈 땅이 많아서", "More empty land in the east", "東に空き地が多かったから", "东边空地较多"),
                QuizChoice("동쪽이 풍수적으로 가장 안전해서", "East is safest in feng shui", "東が風水的に最も安全だから", "东方在风水上最安全"),
                QuizChoice("동쪽 궁문과 가까워서", "Close to the eastern palace gate", "東の宮門に近いから", "靠近东侧宫门")
            ),
            correctIndex = 0
        ))
        add(QuizQuestion(
            heritageId = "donggung",
            questionKo = "조선 세자가 동궁에서 참석했던 학문 토론 제도는?",
            questionEn = "What scholarly institution did the crown prince attend at Donggung?",
            questionJa = "朝鮮の世子が東宮で参加した学問討論の制度は？",
            questionZh = "朝鲜世子在东宫参加的学术讨论制度是？",
            choices = listOf(
                QuizChoice("경연", "Gyeongyeon", "経筵", "经筵"),
                QuizChoice("서연", "Seoyeon", "書筵", "书筵"),
                QuizChoice("강연", "Gangyeon", "講演", "讲演"),
                QuizChoice("시험", "Exam", "試験", "考试")
            ),
            correctIndex = 1
        ))
        add(QuizQuestion(
            heritageId = "donggung",
            questionKo = "동궁 영역의 세자 학습 전각 이름은?",
            questionEn = "What is the name of the crown prince's study hall in Donggung?",
            questionJa = "東宮エリアの世子の学習殿閣の名前は？",
            questionZh = "东宫区域世子学习的殿阁叫什么？",
            choices = listOf(
                QuizChoice("사정전", "Sajeongjeon", "思政殿", "思政殿"),
                QuizChoice("비현각", "Bihyeongak", "丕顕閣", "丕显阁"),
                QuizChoice("수정전", "Sujeongjeon", "修政殿", "修政殿"),
                QuizChoice("만춘전", "Manchunjeon", "萬春殿", "万春殿")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 근정문 (geunjeongmun)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "geunjeongmun",
            questionKo = "근정문 안쪽 가운데 길(어도)을 걸을 수 있었던 사람은?",
            questionEn = "Who could walk on the central path (Eodo) inside Geunjeongmun?",
            questionJa = "勤政門の内側の真ん中の道（御道）を歩けた人は？",
            questionZh = "勤政门内中间道路（御道）可以行走的人是？",
            choices = listOf(
                QuizChoice("정1품 이상 관료", "First Rank officials and above", "正一品以上の官僚", "正一品以上官员"),
                QuizChoice("왕만", "Only the king", "王のみ", "只有国王"),
                QuizChoice("모든 신하", "All officials", "全ての臣下", "所有大臣"),
                QuizChoice("수문장", "Gate guards", "守門将", "守门将")
            ),
            correctIndex = 1
        ))

        // ──────────────────────────────────────────────
        // 영제교 (yeongjeogyo)
        // ──────────────────────────────────────────────
        add(QuizQuestion(
            heritageId = "yeongjeogyo",
            questionKo = "영제교 난간에 새겨진 상상의 동물은?",
            questionEn = "What mythical creature is carved on Yeongjeogyo's railings?",
            questionJa = "永済橋の欄干に彫られた想像上の動物は？",
            questionZh = "永济桥栏杆上雕刻的神兽是？",
            choices = listOf(
                QuizChoice("용", "Dragon", "龍", "龙"),
                QuizChoice("해태", "Haetae", "獬豸", "獬豸"),
                QuizChoice("천록", "Cheonrok", "天禄", "天禄"),
                QuizChoice("봉황", "Phoenix", "鳳凰", "凤凰")
            ),
            correctIndex = 2
        ))
    }

    /** 특정 건물의 퀴즈 목록 반환 */
    fun getQuestionsForHeritage(heritageId: String): List<QuizQuestion> =
        questions.filter { it.heritageId == heritageId }

    /** 전체 퀴즈 목록 */
    fun getAllQuestions(): List<QuizQuestion> = questions
}
