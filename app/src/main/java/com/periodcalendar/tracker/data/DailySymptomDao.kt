package com.periodcalendar.tracker.data

import androidx.room.*
import com.periodcalendar.tracker.models.DailySymptom
import kotlinx.coroutines.flow.Flow

@Dao
interface DailySymptomDao {
    @Query("SELECT * FROM daily_symptoms ORDER BY date DESC")
    fun getAllSymptoms(): Flow<List<DailySymptom>>

    @Query("SELECT * FROM daily_symptoms ORDER BY date DESC")
    suspend fun getAllSymptomsList(): List<DailySymptom>

    @Query("SELECT * FROM daily_symptoms WHERE id = :id")
    suspend fun getSymptomById(id: Long): DailySymptom?

    @Query("SELECT * FROM daily_symptoms WHERE date = :timestamp")
    suspend fun getSymptomsByDate(timestamp: Long): DailySymptom?

    @Query("SELECT * FROM daily_symptoms WHERE date >= :startTimestamp AND date <= :endTimestamp")
    suspend fun getSymptomsBetweenDates(startTimestamp: Long, endTimestamp: Long): List<DailySymptom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymptom(symptom: DailySymptom): Long

    @Update
    suspend fun updateSymptom(symptom: DailySymptom)

    @Delete
    suspend fun deleteSymptom(symptom: DailySymptom)

    @Query("DELETE FROM daily_symptoms")
    suspend fun deleteAllSymptoms()
}
