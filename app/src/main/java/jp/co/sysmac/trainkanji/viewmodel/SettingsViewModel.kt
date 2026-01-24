package jp.co.sysmac.trainkanji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.sysmac.trainkanji.data.QuestionCount
import jp.co.sysmac.trainkanji.data.QuizDataStore
import jp.co.sysmac.trainkanji.data.TotalStats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: QuizDataStore
) : ViewModel() {

    val questionCount: StateFlow<QuestionCount> = dataStore.getQuestionCount()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            QuestionCount.TEN
        )

    val totalStats: StateFlow<TotalStats> = dataStore.getTotalStats()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TotalStats()
        )

    fun setQuestionCount(count: QuestionCount) {
        viewModelScope.launch {
            dataStore.saveQuestionCount(count)
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            dataStore.resetAllData()
        }
    }
}
