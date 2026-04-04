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

