package com.periodcalendar.tracker.data

import androidx.room.*
import com.periodcalendar.tracker.models.MenstrualCycle
import kotlinx.coroutines.flow.Flow

@Dao
interface MenstrualCycleDao {
    @Query("SELECT * FROM menstrual_cycles ORDER BY startDate DESC")
    fun getAllCycles(): Flow<List<MenstrualCycle>>

    @Query("SELECT * FROM menstrual_cycles ORDER BY startDate DESC")
    suspend fun getAllCyclesList(): List<MenstrualCycle>

    @Query("SELECT * FROM menstrual_cycles WHERE id = :id")
    suspend fun getCycleById(id: Long): MenstrualCycle?

    @Query("SELECT * FROM menstrual_cycles WHERE startDate >= :startTimestamp AND startDate <= :endTimestamp")
    suspend fun getCyclesBetweenDates(startTimestamp: Long, endTimestamp: Long): List<MenstrualCycle>

    @Query("SELECT * FROM menstrual_cycles ORDER BY startDate DESC LIMIT 1")
    suspend fun getLatestCycle(): MenstrualCycle?

    @Query("SELECT COUNT(*) FROM menstrual_cycles")
    suspend fun getCycleCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCycle(cycle: MenstrualCycle): Long

    @Update
    suspend fun updateCycle(cycle: MenstrualCycle)

    @Delete
    suspend fun deleteCycle(cycle: MenstrualCycle)

    @Query("DELETE FROM menstrual_cycles")
    suspend fun deleteAllCycles()
}
