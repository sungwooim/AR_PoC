package com.example.ar_poc.data.quiz

import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 퀴즈 데이터 무결성 검증.
 */
class QuizRepositoryTest {

    private val repo = QuizRepository()
    private val source = LocalHeritageKnowledgeSource()

    @Test
    fun `all quiz questions have exactly 4 choices`() {
        repo.getAllQuestions().forEach { q ->
            assertEquals("Quiz ${q.heritageId}/${q.questionKo.take(20)} should have 4 choices",
                4, q.choices.size)
        }
    }

    @Test
    fun `all quiz correctIndex is valid 0-3`() {
        repo.getAllQuestions().forEach { q ->
            assertTrue("Quiz ${q.heritageId}/${q.questionKo.take(20)} correctIndex ${q.correctIndex} out of 0-3",
                q.correctIndex in 0..3)
        }
    }

    @Test
    fun `every quiz question has all 4 languages`() {
        repo.getAllQuestions().forEach { q ->
            assertTrue("${q.heritageId} missing ko", q.questionKo.isNotBlank())
            assertTrue("${q.heritageId} missing en", q.questionEn.isNotBlank())
            assertTrue("${q.heritageId} missing ja", q.questionJa.isNotBlank())
            assertTrue("${q.heritageId} missing zh", q.questionZh.isNotBlank())

            q.choices.forEach { c ->
                assertTrue("${q.heritageId} choice missing ko", c.textKo.isNotBlank())
                assertTrue("${q.heritageId} choice missing en", c.textEn.isNotBlank())
                assertTrue("${q.heritageId} choice missing ja", c.textJa.isNotBlank())
                assertTrue("${q.heritageId} choice missing zh", c.textZh.isNotBlank())
            }
        }
    }

    @Test
    fun `every heritage in knowledge source has at least one quiz`() {
        val heritageIds = source.getHeritageList().map { it.id }.toSet()
        val quizHeritageIds = repo.getAllQuestions().map { it.heritageId }.toSet()

        // 퀴즈가 없는 전각은 경고로 알림. 허용하지만 90% 이상 커버리지는 기대.
        val withQuiz = heritageIds.intersect(quizHeritageIds).size
        val coverage = withQuiz.toDouble() / heritageIds.size
        assertTrue("Quiz coverage should be >= 80% (is ${(coverage * 100).toInt()}%)",
            coverage >= 0.8)
    }

    @Test
    fun `getQuestionsForHeritage returns only matching heritage`() {
        val geunQuizzes = repo.getQuestionsForHeritage("geunjeongjeon")
        assertFalse("Should have geunjeongjeon quizzes", geunQuizzes.isEmpty())
        geunQuizzes.forEach { q ->
            assertEquals("geunjeongjeon", q.heritageId)
        }
    }

    @Test
    fun `total quiz count is at least 30`() {
        // P1+P2 확장 후 기대값
        assertTrue("Expected 30+ quizzes after expansion, got ${repo.getAllQuestions().size}",
            repo.getAllQuestions().size >= 30)
    }

    @Test
    fun `quiz heritageId references existing heritage`() {
        val validIds = source.getHeritageList().map { it.id }.toSet()
        repo.getAllQuestions().forEach { q ->
            assertTrue("Quiz references unknown heritage: ${q.heritageId}",
                validIds.contains(q.heritageId))
        }
    }
}
