package jp.co.sysmac.trainkanji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.sysmac.trainkanji.data.KanjiData
import jp.co.sysmac.trainkanji.data.KanjiItem
import jp.co.sysmac.trainkanji.data.KanjiStats
import jp.co.sysmac.trainkanji.data.QuestionCount
import jp.co.sysmac.trainkanji.data.QuizDataStore
import jp.co.sysmac.trainkanji.data.QuizMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val mode: QuizMode = QuizMode.NORMAL,
    val questions: List<KanjiItem> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val choices: List<String> = emptyList(),
    val isAnswered: Boolean = false,
    val isCorrect: Boolean? = null,
    val selectedReading: String? = null,
    val showHint: Boolean = false,
    val isFinished: Boolean = false,
    val sessionWrongList: List<KanjiItem> = emptyList()
) {
    val currentKanji: KanjiItem?
        get() = questions.getOrNull(currentIndex)

    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentIndex + 1).toFloat() / questions.size
}

class QuizViewModel(
    private val dataStore: QuizDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    /**
     * クイズを開始する
     */
    fun startQuiz(mode: QuizMode) {
        viewModelScope.launch {
            val allStats = dataStore.getAllKanjiStats()
            val questionCount = dataStore.getQuestionCount().first()
            val questions = generateQuizQuestions(mode, questionCount, allStats)

            if (questions.isEmpty() && mode == QuizMode.WEAK) {
                // 苦手漢字がない場合はエラー状態にする
                return@launch
            }

            _uiState.update {
                it.copy(
                    mode = mode,
                    questions = questions,
                    currentIndex = 0,
                    score = 0,
                    choices = KanjiData.generateChoices(questions.first()),
                    isAnswered = false,
                    showHint = false,
                    isFinished = false,
                    sessionWrongList = emptyList()
                )
            }
        }
    }

    /**
     * クイズ問題を生成する
     */
    private suspend fun generateQuizQuestions(
        mode: QuizMode,
        count: QuestionCount,
        allStats: Map<Int, KanjiStats>
    ): List<KanjiItem> {
        val sourceList = when (mode) {
            QuizMode.NORMAL -> KanjiData.allKanji.shuffled()
            QuizMode.WEAK -> {
                val weakList = allStats
                    .filter { (_, stats) -> dataStore.isWeakKanji(stats) }
                    .mapNotNull { (id, _) -> KanjiData.getById(id) }
                    .sortedByDescending { kanji ->
                        val stats = allStats[kanji.id]!!
                        stats.wrongCount - stats.correctCount
                    }
                weakList.shuffled()
            }
        }

        val questionCount = count.count ?: sourceList.size
        return sourceList.take(minOf(questionCount, sourceList.size))
    }

    /**
     * 回答を選択する
     */
    fun selectAnswer(reading: String) {
        val state = _uiState.value
        if (state.isAnswered) return

        val currentKanji = state.currentKanji ?: return
        val isCorrect = reading == currentKanji.reading

        viewModelScope.launch {
            // 統計を更新
            val currentStats = dataStore.getAllKanjiStats()[currentKanji.id]
                ?: KanjiStats(currentKanji.id)

            val newStats = if (isCorrect) {
                currentStats.copy(correctCount = currentStats.correctCount + 1)
            } else {
                currentStats.copy(wrongCount = currentStats.wrongCount + 1)
            }
            dataStore.saveKanjiStats(currentKanji.id, newStats.correctCount, newStats.wrongCount)

            // 全体統計を更新
            val totalStats = dataStore.getTotalStats().first()
            dataStore.saveTotalStats(
                total = totalStats.totalQuestions + 1,
                correct = totalStats.totalCorrect + if (isCorrect) 1 else 0
            )

            // UI状態を更新
            _uiState.update {
                it.copy(
                    isAnswered = true,
                    isCorrect = isCorrect,
                    selectedReading = reading,
                    score = if (isCorrect) it.score + 1 else it.score,
                    sessionWrongList = if (!isCorrect) {
                        it.sessionWrongList + currentKanji
                    } else it.sessionWrongList
                )
            }
        }
    }

    /**
     * 次の問題へ進む
     */
    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= state.questions.size) {
            _uiState.update { it.copy(isFinished = true) }
        } else {
            val nextKanji = state.questions[nextIndex]
            _uiState.update {
                it.copy(
                    currentIndex = nextIndex,
                    choices = KanjiData.generateChoices(nextKanji),
                    isAnswered = false,
                    showHint = false,
                    isCorrect = null,
                    selectedReading = null
                )
            }
        }
    }

    /**
     * ヒントを表示する
     */
    fun showHint() {
        _uiState.update { it.copy(showHint = true) }
    }

    /**
     * クイズを終了する
     */
    fun exitQuiz() {
        _uiState.value = QuizUiState()
    }
}
