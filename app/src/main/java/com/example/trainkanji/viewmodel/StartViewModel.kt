package com.example.trainkanji.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainkanji.data.QuizDataStore
import com.example.trainkanji.data.TotalStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartViewModel(
    private val dataStore: QuizDataStore
) : ViewModel() {

    val totalStats: StateFlow<TotalStats> = dataStore.getTotalStats()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TotalStats()
        )

    private val _weakKanjiCount = MutableStateFlow(0)
    val weakKanjiCount: StateFlow<Int> = _weakKanjiCount.asStateFlow()

    init {
        loadWeakKanjiCount()
    }

    private fun loadWeakKanjiCount() {
        viewModelScope.launch {
            val weakList = dataStore.getWeakKanjiList()
            _weakKanjiCount.value = weakList.size
        }
    }
}
