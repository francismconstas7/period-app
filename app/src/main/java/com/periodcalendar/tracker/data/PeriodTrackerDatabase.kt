package com.periodcalendar.tracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.periodcalendar.tracker.models.*

@Database(
    entities = [
        MenstrualCycle::class,
        DailySymptom::class,
        OvulationData::class,
        PillReminder::class,
        UserProfile::class
    ],
    version = 1,
    exportSchema = true
)
abstract class PeriodTrackerDatabase : RoomDatabase() {
    abstract fun menstrualCycleDao(): MenstrualCycleDao
    abstract fun dailySymptomDao(): DailySymptomDao
    abstract fun ovulationDataDao(): OvulationDataDao
    abstract fun pillReminderDao(): PillReminderDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        const val DATABASE_NAME = "period_tracker_db"
    }
}
