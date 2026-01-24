package jp.co.sysmac.trainkanji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.sysmac.trainkanji.data.KanjiItem
import jp.co.sysmac.trainkanji.data.KanjiStats
import jp.co.sysmac.trainkanji.data.QuizDataStore
import jp.co.sysmac.trainkanji.data.TotalStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WeakKanjiWithStats(
    val kanji: KanjiItem,
    val stats: KanjiStats
)

class ReportViewModel(
    private val dataStore: QuizDataStore
) : ViewModel() {

    val totalStats: StateFlow<TotalStats> = dataStore.getTotalStats()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TotalStats()
        )

    private val _weakKanjiList = MutableStateFlow<List<WeakKanjiWithStats>>(emptyList())
    val weakKanjiList: StateFlow<List<WeakKanjiWithStats>> = _weakKanjiList.asStateFlow()

    init {
        loadWeakKanjiList()
    }

    private fun loadWeakKanjiList() {
        viewModelScope.launch {
            val allStats = dataStore.getAllKanjiStats()
            val weakList = dataStore.getWeakKanjiList()

            _weakKanjiList.value = weakList.map { kanji ->
                WeakKanjiWithStats(
                    kanji = kanji,
                    stats = allStats[kanji.id]!!
                )
            }
        }
    }
}
