package com.periodcalendar.tracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_symptoms")
data class DailySymptom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // timestamp
    val cramps: Int = 0, // 0-3 scale
    val headaches: Int = 0, // 0-3 scale
    val bloating: Int = 0, // 0-3 scale
    val moodSwings: Int = 0, // 0-3 scale
    val acne: Int = 0, // 0-3 scale
    val fatigue: Int = 0, // 0-3 scale
    val breastTenderness: Int = 0, // 0-3 scale
    val nausea: Int = 0, // 0-3 scale
    val backache: Int = 0, // 0-3 scale
    val insomnia: Int = 0, // 0-3 scale
    val appetite: Int = 0, // 0-3 scale
    val libido: Int = 0, // 0-3 scale
    val otherSymptoms: String = ""
)

enum class SymptomIntensity(val value: Int) {
    NONE(0), MILD(1), MODERATE(2), SEVERE(3)
}
