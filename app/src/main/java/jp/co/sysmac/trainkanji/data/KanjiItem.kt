package jp.co.sysmac.trainkanji.data

data class KanjiItem(
    val id: Int,
    val kanji: String,
    val reading: String,
    val sentence: String,
    val category: KanjiCategory
)
