package com.periodcalendar.tracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Long? = null,
    val averageCycleLength: Int = 28, // days
    val averagePeriodLength: Int = 5, // days
    val lutealPhaseLength: Int = 14, // days
    val follicularPhaseLength: Int = 14, // days
    val weight: Double? = null, // kg or lbs
    val height: Double? = null, // cm or inches
    val unitSystem: UnitSystem = UnitSystem.METRIC,
    val tryingToConceive: Boolean = false,
    val usingBirthControl: Boolean = false,
    val accountType: AccountType = AccountType.LOCAL,
    val googleAccountId: String? = null,
    val lastBackupDate: Long? = null
)

enum class UnitSystem {
    METRIC, IMPERIAL
}

enum class AccountType {
    LOCAL, GOOGLE
}

data class FertilityPrediction(
    val date: Long,
    val fertilityLevel: FertilityLevel,
    val conceptionChance: Double, // 0.0 to 1.0
    val isOvulationDay: Boolean = false,
    val isPeriodExpected: Boolean = false,
    val daysUntilPeriod: Int? = null,
    val daysUntilOvulation: Int? = null
)

enum class FertilityLevel {
    LOW, MEDIUM, HIGH, PEAK, NON_FERTILE
}
