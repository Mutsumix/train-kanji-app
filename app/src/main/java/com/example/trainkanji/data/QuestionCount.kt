package com.example.trainkanji.data

enum class QuestionCount(val value: String, val displayName: String, val count: Int?) {
    TEN("10", "10もん", 10),
    TWENTY("20", "20もん", 20),
    ALL("all", "ぜんぶ", null);

    companion object {
        fun fromValue(value: String): QuestionCount {
            return when (value) {
                "20" -> TWENTY
                "all" -> ALL
                else -> TEN
            }
        }
    }
}
