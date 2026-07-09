package com.periodcalendar.tracker.data

import androidx.room.*
import com.periodcalendar.tracker.models.OvulationData
import kotlinx.coroutines.flow.Flow

@Dao
interface OvulationDataDao {
    @Query("SELECT * FROM ovulation_data ORDER BY date DESC")
    fun getAllOvulationData(): Flow<List<OvulationData>>

    @Query("SELECT * FROM ovulation_data ORDER BY date DESC")
    suspend fun getAllOvulationDataList(): List<OvulationData>

    @Query("SELECT * FROM ovulation_data WHERE id = :id")
    suspend fun getOvulationDataById(id: Long): OvulationData?

    @Query("SELECT * FROM ovulation_data WHERE date = :timestamp")
    suspend fun getOvulationDataByDate(timestamp: Long): OvulationData?

    @Query("SELECT * FROM ovulation_data WHERE date >= :startTimestamp AND date <= :endTimestamp")
    suspend fun getOvulationDataBetweenDates(startTimestamp: Long, endTimestamp: Long): List<OvulationData>

    @Query("SELECT * FROM ovulation_data WHERE ovulationTestResult = 'POSITIVE' ORDER BY date DESC")
    suspend fun getPositiveOvulationTests(): List<OvulationData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOvulationData(data: OvulationData): Long

    @Update
    suspend fun updateOvulationData(data: OvulationData)

    @Delete
    suspend fun deleteOvulationData(data: OvulationData)

    @Query("DELETE FROM ovulation_data")
    suspend fun deleteAllOvulationData()
}
