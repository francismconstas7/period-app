package com.periodcalendar.tracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menstrual_cycles")
data class MenstrualCycle(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startDate: Long, // timestamp
    val endDate: Long?, // timestamp, null if ongoing
    val cycleLength: Int, // days
    val periodLength: Int, // days
    val flowIntensity: FlowIntensity = FlowIntensity.MEDIUM,
    val notes: String = ""
)

enum class FlowIntensity {
    LIGHT, MEDIUM, HEAVY, SPOTTING
}
