package com.periodcalendar.tracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ovulation_data")
data class OvulationData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long, // timestamp
    val cervicalMucus: CervicalMucusType = CervicalMucusType.DRY,
    val cervicalPosition: CervicalPosition = CervicalPosition.LOW,
    val cervicalFirmness: CervicalFirmness = CervicalFirmness.FIRM,
    val basalBodyTemperature: Double? = null, // in Celsius or Fahrenheit
    val ovulationTestResult: OvulationTestResult = OvulationTestResult.NEGATIVE,
    val sexualActivity: Boolean = false,
    val protectionUsed: ProtectionType? = null,
    val notes: String = ""
)

enum class CervicalMucusType {
    DRY, STICKY, CREAMY, WATERY, EGG_WHITE
}

enum class CervicalPosition {
    LOW, MEDIUM, HIGH
}

enum class CervicalFirmness {
    FIRM, MEDIUM, SOFT
}

enum class OvulationTestResult {
    NEGATIVE, POSITIVE, INVALID
}

enum class ProtectionType {
    NONE, CONDOM, PILL, IUD, PATCH, RING, INJECTION, OTHER
}
