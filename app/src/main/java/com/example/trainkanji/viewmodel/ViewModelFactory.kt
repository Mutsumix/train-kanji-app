package com.example.trainkanji.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trainkanji.data.QuizDataStore

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    private val dataStore = QuizDataStore(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> {
                QuizViewModel(dataStore) as T
            }
            modelClass.isAssignableFrom(StartViewModel::class.java) -> {
                StartViewModel(dataStore) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(dataStore) as T
            }
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(dataStore) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
