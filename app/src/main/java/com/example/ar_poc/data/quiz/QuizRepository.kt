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
    }

    /** 특정 건물의 퀴즈 목록 반환 */
    fun getQuestionsForHeritage(heritageId: String): List<QuizQuestion> =
        questions.filter { it.heritageId == heritageId }

    /** 전체 퀴즈 목록 */
    fun getAllQuestions(): List<QuizQuestion> = questions
}
