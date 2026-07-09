package com.periodcalendar.tracker.data

import androidx.room.*
import com.periodcalendar.tracker.models.PillReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface PillReminderDao {
    @Query("SELECT * FROM pill_reminders ORDER BY name")
    fun getAllReminders(): Flow<List<PillReminder>>

    @Query("SELECT * FROM pill_reminders ORDER BY name")
    suspend fun getAllRemindersList(): List<PillReminder>

    @Query("SELECT * FROM pill_reminders WHERE id = :id")
    suspend fun getReminderById(id: Long): PillReminder?

    @Query("SELECT * FROM pill_reminders WHERE enabled = 1")
    suspend fun getEnabledReminders(): List<PillReminder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: PillReminder): Long

    @Update
    suspend fun updateReminder(reminder: PillReminder)

    @Delete
    suspend fun deleteReminder(reminder: PillReminder)

    @Query("DELETE FROM pill_reminders")
    suspend fun deleteAllReminders()
}
