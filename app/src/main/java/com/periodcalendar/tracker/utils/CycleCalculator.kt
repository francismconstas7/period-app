package com.periodcalendar.tracker.utils

import com.periodcalendar.tracker.models.FertilityLevel
import com.periodcalendar.tracker.models.FertilityPrediction
import com.periodcalendar.tracker.models.MenstrualCycle
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object CycleCalculator {

    /**
     * Calculate the predicted next period start date based on historical cycles
     */
    fun predictNextPeriod(cycles: List<MenstrualCycle>, averageCycleLength: Int): LocalDate? {
        if (cycles.isEmpty()) return null
        
        val latestCycle = cycles.maxByOrNull { it.startDate } ?: return null
        val lastStartDate = java.time.Instant.ofEpochMilli(latestCycle.startDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        
        return lastStartDate.plusDays(averageCycleLength.toLong())
    }

    /**
     * Calculate predicted ovulation date (typically 14 days before next period)
     */
    fun predictOvulationDate(nextPeriodDate: LocalDate, lutealPhaseLength: Int = 14): LocalDate {
        return nextPeriodDate.minusDays(lutealPhaseLength.toLong())
    }

    /**
     * Calculate fertile window (5 days before ovulation + ovulation day)
     */
    fun getFertileWindow(ovulationDate: LocalDate): Pair<LocalDate, LocalDate> {
        val windowStart = ovulationDate.minusDays(5)
        val windowEnd = ovulationDate.plusDays(1)
        return Pair(windowStart, windowEnd)
    }

    /**
     * Calculate fertility predictions for a range of dates
     */
    fun calculateFertilityPredictions(
        cycles: List<MenstrualCycle>,
        userProfile: com.periodcalendar.tracker.models.UserProfile,
        daysToPredict: Int = 60
    ): List<FertilityPrediction> {
        val predictions = mutableListOf<FertilityPrediction>()
        val today = LocalDate.now()
        
        if (cycles.isEmpty()) {
            for (i in 0 until daysToPredict) {
                val date = today.plusDays(i.toLong())
                predictions.add(
                    FertilityPrediction(
                        date = atStartOfDay(date),
                        fertilityLevel = FertilityLevel.NON_FERTILE,
                        conceptionChance = 0.0,
                        isOvulationDay = false,
                        isPeriodExpected = false
                    )
                )
            }
            return predictions
        }

        val averageCycleLength = userProfile.averageCycleLength
        val lutealPhaseLength = userProfile.lutealPhaseLength
        
        val latestCycle = cycles.maxByOrNull { it.startDate } ?: return predictions
        val lastPeriodStart = java.time.Instant.ofEpochMilli(latestCycle.startDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        for (i in 0 until daysToPredict) {
            val currentDate = today.plusDays(i.toLong())
            
            val predictedNextPeriod = lastPeriodStart.plusDays(averageCycleLength.toLong())
            val daysUntilPeriod = ChronoUnit.DAYS.between(today, predictedNextPeriod).toInt()
            
            val predictedOvulation = predictedNextPeriod.minusDays(lutealPhaseLength.toLong())
            val daysUntilOvulation = ChronoUnit.DAYS.between(today, predictedOvulation).toInt()
            
            val isOvulationDay = currentDate == predictedOvulation
            val isPeriodExpected = currentDate == predictedNextPeriod
            
            val (fertileStart, fertileEnd) = getFertileWindow(predictedOvulation)
            val isInFertileWindow = !currentDate.isBefore(fertileStart) && !currentDate.isAfter(fertileEnd)
            
            val fertilityLevel = when {
                isOvulationDay -> FertilityLevel.PEAK
                isInFertileWindow -> {
                    val daysToOvulation = ChronoUnit.DAYS.between(currentDate, predictedOvulation).toInt()
                    when {
                        daysToOvulation <= 1 -> FertilityLevel.HIGH
                        daysToOvulation <= 3 -> FertilityLevel.MEDIUM
                        else -> FertilityLevel.LOW
                    }
                }
                else -> FertilityLevel.NON_FERTILE
            }
            
            val conceptionChance = when (fertilityLevel) {
                FertilityLevel.PEAK -> 0.30
                FertilityLevel.HIGH -> 0.20
                FertilityLevel.MEDIUM -> 0.10
                FertilityLevel.LOW -> 0.05
                FertilityLevel.NON_FERTILE -> 0.01
            }
            
            predictions.add(
                FertilityPrediction(
                    date = atStartOfDay(currentDate),
                    fertilityLevel = fertilityLevel,
                    conceptionChance = conceptionChance,
                    isOvulationDay = isOvulationDay,
                    isPeriodExpected = isPeriodExpected,
                    daysUntilPeriod = if (daysUntilPeriod >= 0) daysUntilPeriod else null,
                    daysUntilOvulation = if (daysUntilOvulation >= 0) daysUntilOvulation else null
                )
            )
        }
        
        return predictions
    }

    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        val heightM = heightCm / 100.0
        return weightKg / (heightM * heightM)
    }

    fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal weight"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun atStartOfDay(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
