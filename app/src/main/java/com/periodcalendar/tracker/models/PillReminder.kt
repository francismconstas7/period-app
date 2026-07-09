package com.periodcalendar.tracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pill_reminders")
data class PillReminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "Birth Control Pill",
    val reminderTime: String, // HH:mm format
    val enabled: Boolean = true,
    val type: ContraceptiveType = ContraceptiveType.PILL,
    val dosage: String = "",
    val instructions: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val daysOfWeek: Set<Int> = setOf(0, 1, 2, 3, 4, 5, 6), // Sunday to Saturday
    val notificationText: String = "Time to take your pill",
    val discreetMode: Boolean = false,
    val discreetText: String = "Reminder"
)

enum class ContraceptiveType {
    PILL, RING, PATCH, INJECTION, IUD, IMPLANT, OTHER
}
