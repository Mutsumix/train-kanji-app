package jp.co.sysmac.trainkanji.data

data class TotalStats(
    val totalQuestions: Int = 0,
    val totalCorrect: Int = 0
) {
    val accuracyPercent: Int
        get() = if (totalQuestions > 0) {
            (totalCorrect * 100 / totalQuestions)
        } else 0
}
