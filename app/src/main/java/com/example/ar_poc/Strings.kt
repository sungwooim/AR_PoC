package com.example.ar_poc

/**
 * Static string translations for fixed UI text.
 * Use this instead of calling the LLM for hardcoded strings — saves tokens.
 */
object Strings {

    // ──────────────────────────────────────────────
    // 1. Suggested Questions (카메라 화면 추천 질문)
    // ──────────────────────────────────────────────
    private val suggestedQuestionsMap = mapOf(
        "ko" to listOf("왜 중요한가요?", "누가 사용했나요?", "건축 특징은 무엇인가요?"),
        "en" to listOf("Why is it important?", "Who used it?", "What are its architectural features?"),
        "ja" to listOf("なぜ重要なのですか？", "誰が使用しましたか？", "建築的な特徴は何ですか？"),
        "zh" to listOf("为什么它很重要？", "谁使用过它？", "它的建筑特色是什么？")
    )

    fun getSuggestedQuestions(lang: String): List<String> =
        suggestedQuestionsMap[lang] ?: suggestedQuestionsMap["ko"]!!

    // 전각별 특화 추천 질문 (공통 질문에 추가로 표시)
    private val heritageSuggestedQuestions = mapOf(
        "geunjeongjeon" to mapOf(
            "ko" to listOf("칠조룡은 왜 7발톱인가요?", "박석은 왜 울퉁불퉁한가요?"),
            "en" to listOf("Why does the dragon have 7 claws?", "Why are the floor stones rough?"),
            "ja" to listOf("七爪竜はなぜ7つの爪なのですか？", "床石はなぜ凸凹しているのですか？"),
            "zh" to listOf("七爪龙为什么有7个爪子？", "地石为什么凹凸不平？")
        ),
        "gyeonghoeru" to mapOf(
            "ko" to listOf("기둥이 왜 원형과 사각형인가요?", "연못에서 발견된 것은 무엇인가요?"),
            "en" to listOf("Why are the pillars round and square?", "What was found in the pond?"),
            "ja" to listOf("柱はなぜ円形と四角形なのですか？", "池から発見されたものは何ですか？"),
            "zh" to listOf("柱子为什么有圆形和方形？", "池塘里发现了什么？")
        ),
        "gwanghwamun" to mapOf(
            "ko" to listOf("해태는 어떤 동물인가요?", "수문장 교대식은 언제 볼 수 있나요?"),
            "en" to listOf("What kind of creature is the Haetae?", "When can I see the guard ceremony?"),
            "ja" to listOf("ヘテはどんな動物ですか？", "守門将交代式はいつ見られますか？"),
            "zh" to listOf("獬豸是什么动物？", "换岗仪式什么时候举行？")
        ),
        "sajeongjeon" to mapOf(
            "ko" to listOf("세종대왕은 여기서 무엇을 했나요?", "경연은 어떤 행사인가요?"),
            "en" to listOf("What did King Sejong do here?", "What is Gyeongyeon?"),
            "ja" to listOf("世宗大王はここで何をしましたか？", "経筵とはどんな行事ですか？"),
            "zh" to listOf("世宗大王在这里做了什么？", "经筵是什么活动？")
        ),
        "gangnyeongjeon" to mapOf(
            "ko" to listOf("왜 용마루가 없나요?", "몇 번이나 불에 탔나요?"),
            "en" to listOf("Why is there no roof ridge?", "How many times did it burn?"),
            "ja" to listOf("なぜ龍棟がないのですか？", "何度焼失しましたか？"),
            "zh" to listOf("为什么没有正脊？", "被烧了几次？")
        ),
        "gyotaejeon" to mapOf(
            "ko" to listOf("아미산 굴뚝은 왜 유명한가요?", "왕비의 하루는 어땠나요?"),
            "en" to listOf("Why are the Amisan chimneys famous?", "What was the queen's daily life like?"),
            "ja" to listOf("峨嵋山の煙突はなぜ有名ですか？", "王妃の一日はどうでしたか？"),
            "zh" to listOf("峨眉山烟囱为什么有名？", "王妃的一天是怎样的？")
        ),
        "jagyeongjeon" to mapOf(
            "ko" to listOf("십장생은 무엇인가요?", "꽃담에 어떤 문양이 있나요?"),
            "en" to listOf("What are the Ten Longevity Symbols?", "What patterns are on the flower walls?"),
            "ja" to listOf("十長生とは何ですか？", "花塀にはどんな文様がありますか？"),
            "zh" to listOf("十长生是什么？", "花墙上有什么纹样？")
        ),
        "hyangwonjeong" to mapOf(
            "ko" to listOf("취향교가 왜 북쪽으로 옮겨졌나요?", "가장 아름다운 계절은 언제인가요?"),
            "en" to listOf("Why was the bridge moved north?", "Which season is the most beautiful?"),
            "ja" to listOf("醉香橋はなぜ北に移されたのですか？", "最も美しい季節はいつですか？"),
            "zh" to listOf("醉香桥为什么移到了北侧？", "最美的季节是什么时候？")
        ),
        "geoncheongung" to mapOf(
            "ko" to listOf("을미사변이 무엇인가요?", "한국 최초의 전등은 어떤 것이었나요?"),
            "en" to listOf("What was the Eulmi Incident?", "What were Korea's first electric lights like?"),
            "ja" to listOf("乙未事変とは何ですか？", "韓国初の電灯はどんなものでしたか？"),
            "zh" to listOf("乙未事变是什么？", "韩国最早的电灯是什么样的？")
        ),
        "jibokjae" to mapOf(
            "ko" to listOf("왜 중국식으로 지었나요?", "고종은 어떤 책을 읽었나요?"),
            "en" to listOf("Why was it built in Chinese style?", "What books did King Gojong read?"),
            "ja" to listOf("なぜ中国式で建てたのですか？", "高宗はどんな本を読みましたか？"),
            "zh" to listOf("为什么建成中国式？", "高宗读了什么书？")
        ),
        "donggung" to mapOf(
            "ko" to listOf("세자의 하루 일과는 어땠나요?", "서연은 무엇인가요?"),
            "en" to listOf("What was the crown prince's daily life like?", "What is Seoyeon?"),
            "ja" to listOf("世子の一日の日課はどうでしたか？", "書筵とは何ですか？"),
            "zh" to listOf("世子的一天是怎样的？", "书筵是什么？")
        )
    )

    fun getHeritageSuggestedQuestions(heritageId: String, lang: String): List<String> {
        val specific = heritageSuggestedQuestions[heritageId]?.get(lang) ?: emptyList()
        val common = getSuggestedQuestions(lang)
        return (specific + common).distinct()
    }

    // ──────────────────────────────────────────────
    // 2. Palace Names (궁궐 이름)
    // ──────────────────────────────────────────────
    private val palaceNameMap = mapOf(
        "geunjeongjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "gyeonghoeru" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "gwanghwamun" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "sajeongjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "sujeongjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "gangnyeongjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "gyotaejeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "jagyeongjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "hyangwonjeong" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "geoncheongung" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "jibokjae" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "donggung" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "taeweonjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "sojubang" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "heungbokjeon" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "geunjeongmun" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        ),
        "yeongjeogyo" to mapOf(
            "ko" to "경복궁 (Gyeongbokgung)",
            "en" to "Gyeongbokgung Palace",
            "ja" to "景福宮（キョンボックン）",
            "zh" to "景福宫"
        )
    )

    fun getPalaceName(heritageId: String, lang: String): String? =
        palaceNameMap[heritageId]?.get(lang) ?: palaceNameMap[heritageId]?.get("en")

    // ──────────────────────────────────────────────
    // 3. Section Labels (청크 섹션 이름)
    // ──────────────────────────────────────────────
    private val sectionNameMap = mapOf(
        "역사"      to mapOf("en" to "History",          "ja" to "歴史",   "zh" to "历史"),
        "건축"      to mapOf("en" to "Architecture",     "ja" to "建築",   "zh" to "建筑"),
        "관람 포인트" to mapOf("en" to "Highlights",      "ja" to "見どころ","zh" to "观赏亮点"),
        "관람 가이드" to mapOf("en" to "Visitor Guide",   "ja" to "観覧ガイド","zh" to "参观指南"),
        "트리비아"   to mapOf("en" to "Trivia",           "ja" to "トリビア","zh" to "趣味知识")
    )

    fun getSection(koreanSection: String, lang: String): String =
        if (lang == "ko") koreanSection
        else sectionNameMap[koreanSection]?.get(lang) ?: koreanSection

    // ──────────────────────────────────────────────
    // 4. Sub-element Label (세부 요소 발견 라벨)
    // ──────────────────────────────────────────────
    private val subElementLabelMap = mapOf(
        "en" to "Discovered Detail",
        "ja" to "発見された詳細",
        "zh" to "发现的细节"
    )

    fun getSubElementLabel(lang: String): String =
        if (lang == "ko") "발견된 세부 요소"
        else subElementLabelMap[lang] ?: "Discovered Detail"

    // ──────────────────────────────────────────────
    // 5. Common UI Labels (공통 UI 문구)
    // ──────────────────────────────────────────────
    fun getBackLabel(lang: String): String = when (lang) {
        "ko" -> "뒤로가기"
        "en" -> "Back"
        "ja" -> "戻る"
        "zh" -> "返回"
        else -> "Back"
    }

    fun getNoInfoLabel(lang: String): String = when (lang) {
        "ko" -> "정보를 불러올 수 없습니다."
        "en" -> "Information could not be loaded."
        "ja" -> "情報を読み込めませんでした。"
        "zh" -> "无法加载信息。"
        else -> "Information could not be loaded."
    }

    fun getErrorPrefix(lang: String): String = when (lang) {
        "ko" -> "오류가 발생했습니다: "
        "en" -> "An error occurred: "
        "ja" -> "エラーが発生しました: "
        "zh" -> "发生了错误: "
        else -> "An error occurred: "
    }

    fun getSendLabel(lang: String): String = when (lang) {
        "ko" -> "전송"
        "en" -> "Send"
        "ja" -> "送信"
        "zh" -> "发送"
        else -> "Send"
    }

    // ──────────────────────────────────────────────
    // 6. Camera Screen UI Labels (카메라 화면 안내 문구)
    // ──────────────────────────────────────────────
    data class ARUiLabels(
        val detail: String,
        val ask: String,
        val point: String,
        val hold: String,
        val recognizing: String,
        val questionTitle: String,
        val questionPlaceholder: String,
        val waitingAnswer: String
    )

    fun getCameraLabels(lang: String): ARUiLabels = when (lang) {
        "en" -> ARUiLabels("View Detail", "Ask Question", "Point at heritage", "Hold steady", "Recognizing...", "Ask Heritage", "Type your question", "Waiting for answer...")
        "ja" -> ARUiLabels("詳細を見る", "質問する", "文化遺産に向けて", "そのまま固定して", "認識中...", "質問する", "質問を入力してください", "回答を待っています...")
        "zh" -> ARUiLabels("查看详情", "提问", "请对准文化遗产", "请保持稳定", "识别中...", "向文化遗产提问", "输入您的问题", "正在等待回答...")
        else -> ARUiLabels("상세 가이드 보기", "질문하기", "문화유산을 중앙에 비춰주세요", "중앙에 고정하세요", "인식 중...", "문화유산에게 질문하기", "궁금한 점을 물어보세요", "답변을 기다리는 중입니다...")
    }

    // ──────────────────────────────────────────────
    // 7. Stamp Collection UI Labels (도장 수집 화면 문구)
    // ──────────────────────────────────────────────
    data class StampUiLabels(
        val title: String,
        val completeMessage: String,
        val lockedLabel: String,
        val discoveredLabel: String
    )

    fun getStampLabels(lang: String): StampUiLabels = when (lang) {
        "en" -> StampUiLabels("🏮 Heritage Passport", "🎊 All collected! Gyeongbokgung complete!", "Locked \uD83D\uDD12", "✨ Discovered")
        "ja" -> StampUiLabels("🏮 遺産パスポート", "🎊 全て発見！景福宮コンプリート！", "未発見 \uD83D\uDD12", "✨ 発見済み")
        "zh" -> StampUiLabels("🏮 文化遗产护照", "🎊 全部发现！景福宫集章完成！", "未发现 \uD83D\uDD12", "✨ 已发现")
        else -> StampUiLabels("🏮 궁궐 유산 도장", "🎊 모든 유산 발견 완료! 경복궁 완전 정복!", "미발견 \uD83D\uDD12", "✨ 발견")
    }

    fun getStampScoreLabel(lang: String, discovered: Int, total: Int): String = when (lang) {
        "en" -> "Discovered $discovered / $total"
        "ja" -> "発見済み $discovered / $total"
        "zh" -> "已发现 $discovered / $total"
        else -> "발견한 유산 $discovered / $total"
    }

    fun getStampRemainingLabel(lang: String, remaining: Int): String = when (lang) {
        "en" -> "$remaining remaining to discover"
        "ja" -> "残り${remaining}件を発見しましょう"
        "zh" -> "还剩${remaining}处待发现"
        else -> "발견하지 못한 유산: ${remaining}건"
    }

    fun getHiddenTreasureTitle(lang: String, title: String): String = when (lang) {
        "en" -> "👑 Hidden Treasures of $title"
        "ja" -> "👑 $title の隠された宝物"
        "zh" -> "👑 $title 的隐藏宝藏"
        else -> "👑 [$title]의 숨겨진 보물들"
    }

    fun getHiddenTreasureUnknown(lang: String): String = when (lang) {
        "en" -> "❓ Unknown Treasure"
        "ja" -> "❓ 未発見の宝物"
        "zh" -> "❓ 未知宝藏"
        else -> "❓ ??? (숨겨진 보물)"
    }

    fun getHintMessage(lang: String, chunkTitle: String): String = when(lang) {
        "en" -> "💡 Hint: Look closely at '${chunkTitle}'"
        "ja" -> "💡 ヒント: '${chunkTitle}' の近くを探してください"
        "zh" -> "💡 提示: 仔细观察 '${chunkTitle}' 附近"
        else -> "💡 힌트: '${chunkTitle}' 관련 설명을 읽고 주변을 찾아보세요!"
    }
}

