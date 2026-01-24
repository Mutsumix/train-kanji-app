package com.example.trainkanji.data

data class KanjiStats(
    val kanjiId: Int,
    val correctCount: Int = 0,
    val wrongCount: Int = 0
)
