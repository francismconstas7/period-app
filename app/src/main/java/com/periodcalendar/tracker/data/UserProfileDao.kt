package com.periodcalendar.tracker.data

import androidx.room.*
import com.periodcalendar.tracker.models.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles ORDER BY id DESC LIMIT 1")
    fun getCurrentProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profiles ORDER BY id DESC LIMIT 1")
    suspend fun getCurrentProfileSync(): UserProfile?

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    suspend fun getProfileById(id: Long): UserProfile?

    @Query("SELECT * FROM user_profiles WHERE accountType = 'GOOGLE' AND googleAccountId = :googleAccountId")
    suspend fun getProfileByGoogleId(googleAccountId: String): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile): Long

    @Update
    suspend fun updateProfile(profile: UserProfile)

    @Delete
    suspend fun deleteProfile(profile: UserProfile)

    @Query("DELETE FROM user_profiles")
    suspend fun deleteAllProfiles()
}
