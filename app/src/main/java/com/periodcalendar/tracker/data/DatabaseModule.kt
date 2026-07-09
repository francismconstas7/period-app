package com.periodcalendar.tracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.periodcalendar.tracker.models.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PeriodTrackerDatabase {
        return Room.databaseBuilder(
            context,
            PeriodTrackerDatabase::class.java,
            PeriodTrackerDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMenstrualCycleDao(database: PeriodTrackerDatabase): MenstrualCycleDao {
        return database.menstrualCycleDao()
    }

    @Provides
    @Singleton
    fun provideDailySymptomDao(database: PeriodTrackerDatabase): DailySymptomDao {
        return database.dailySymptomDao()
    }

    @Provides
    @Singleton
    fun provideOvulationDataDao(database: PeriodTrackerDatabase): OvulationDataDao {
        return database.ovulationDataDao()
    }

    @Provides
    @Singleton
    fun providePillReminderDao(database: PeriodTrackerDatabase): PillReminderDao {
        return database.pillReminderDao()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(database: PeriodTrackerDatabase): UserProfileDao {
        return database.userProfileDao()
    }
}
