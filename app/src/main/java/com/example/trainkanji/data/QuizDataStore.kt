package com.example.trainkanji.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_data")

class QuizDataStore(private val context: Context) {
    // 漢字別統計の保存
    suspend fun saveKanjiStats(kanjiId: Int, correct: Int, wrong: Int) {
        context.dataStore.edit { prefs ->
            prefs[intPreferencesKey("kanji_${kanjiId}_correct")] = correct
            prefs[intPreferencesKey("kanji_${kanjiId}_wrong")] = wrong
        }
    }

    // 漢字別統計の取得
    fun getKanjiStats(kanjiId: Int): Flow<KanjiStats> {
        return context.dataStore.data.map { prefs ->
            KanjiStats(
                kanjiId = kanjiId,
                correctCount = prefs[intPreferencesKey("kanji_${kanjiId}_correct")] ?: 0,
                wrongCount = prefs[intPreferencesKey("kanji_${kanjiId}_wrong")] ?: 0
            )
        }
    }

    // 全漢字の統計を取得
    suspend fun getAllKanjiStats(): Map<Int, KanjiStats> {
        return context.dataStore.data.first().let { prefs ->
            (1..80).associate { id ->
                id to KanjiStats(
                    kanjiId = id,
                    correctCount = prefs[intPreferencesKey("kanji_${id}_correct")] ?: 0,
                    wrongCount = prefs[intPreferencesKey("kanji_${id}_wrong")] ?: 0
                )
            }
        }
    }

    // 全体統計の保存
    suspend fun saveTotalStats(total: Int, correct: Int) {
        context.dataStore.edit { prefs ->
            prefs[intPreferencesKey("total_questions")] = total
            prefs[intPreferencesKey("total_correct")] = correct
        }
    }

    // 全体統計の取得
    fun getTotalStats(): Flow<TotalStats> {
        return context.dataStore.data.map { prefs ->
            TotalStats(
                totalQuestions = prefs[intPreferencesKey("total_questions")] ?: 0,
                totalCorrect = prefs[intPreferencesKey("total_correct")] ?: 0
            )
        }
    }

    // 設定の保存
    suspend fun saveQuestionCount(count: QuestionCount) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey("question_count")] = count.value
        }
    }

    // 設定の取得
    fun getQuestionCount(): Flow<QuestionCount> {
        return context.dataStore.data.map { prefs ->
            QuestionCount.fromValue(
                prefs[stringPreferencesKey("question_count")] ?: "10"
            )
        }
    }

    // 全データリセット
    suspend fun resetAllData() {
        context.dataStore.edit { it.clear() }
    }

    // 苦手漢字の判定
    fun isWeakKanji(stats: KanjiStats): Boolean {
        return stats.wrongCount > stats.correctCount
    }

    // 苦手漢字リストの取得
    suspend fun getWeakKanjiList(): List<KanjiItem> {
        val allStats = getAllKanjiStats()
        return allStats
            .filter { (_, stats) -> isWeakKanji(stats) }
            .mapNotNull { (id, _) -> KanjiData.getById(id) }
            .sortedByDescending { kanji ->
                val stats = allStats[kanji.id]!!
                stats.wrongCount - stats.correctCount
            }
    }
}
