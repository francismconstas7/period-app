package com.periodcalendar.tracker.wear

import android.content.Context
import androidx.wear.complications.data.*
import androidx.wear.complications.rendering.ComplicationDrawable
import kotlinx.coroutines.flow.first
import java.time.ZoneId
import java.time.ZonedDateTime

class PeriodTileService : androidx.wear.complications.data.SuspendingComplicationProviderService() {
    
    override suspend fun onComplicationRequest(
        complicationId: Int,
        requestedType: ComplicationType,
        zonedDateTime: ZonedDateTime
    ): ComplicationData? {
        return when (requestedType) {
            ComplicationType.SHORT_TEXT -> createShortTextComplication()
            ComplicationType.LONG_TEXT -> createLongTextComplication()
            ComplicationType.RANGED_VALUE -> createRangedValueComplication()
            else -> null
        }
    }

    private suspend fun createShortTextComplication(): ShortTextComplicationData {
        // Get cycle data from repository (would be injected in production)
        val daysUntilPeriod = 3 // This would come from actual calculation
        
        return ShortTextComplicationData.Builder(
            PlainComplicationText.Builder("Day $daysUntilPeriod").build(),
            PlainComplicationText.Builder("Period").build()
        )
        .setMonochromaticImage(
            MonochromaticImage.Builder(null).build()
        )
        .build()
    }

    private suspend fun createLongTextComplication(): LongTextComplicationData {
        val daysUntilPeriod = 3
        val fertilityStatus = "High"
        
        return LongTextComplicationData.Builder(
            PlainComplicationText.Builder("Period in $daysUntilPeriod days").build(),
            PlainComplicationText.Builder("Fertility: $fertilityStatus").build()
        )
        .setTitle(PlainComplicationText.Builder("Cycle Tracker").build())
        .build()
    }

    private suspend fun createRangedValueComplication(): RangedValueComplicationData {
        val cycleDay = 14
        val totalCycleDays = 28
        
        return RangedValueComplicationData.Builder(
            cycleDay.toFloat(),
            1f,
            totalCycleDays.toFloat()
        )
        .setText(
            PlainComplicationText.Builder("$cycleDay/$totalCycleDays").build()
        )
        .setValueIndicator(
            ValueIndicator(
                cycleDay.toFloat() / totalCycleDays,
                ValueIndicatorStyle.FILL
            )
        )
        .build()
    }

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return when (type) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(
                PlainComplicationText.Builder("3").build(),
                PlainComplicationText.Builder("Days").build()
            ).build()
            ComplicationType.LONG_TEXT -> LongTextComplicationData.Builder(
                PlainComplicationText.Builder("Period in 3 days").build(),
                PlainComplicationText.Builder("Fertility: High").build()
            ).setTitle(PlainComplicationText.Builder("Cycle Tracker").build()).build()
            ComplicationType.RANGED_VALUE -> RangedValueComplicationData.Builder(
                14f, 1f, 28f
            ).setText(PlainComplicationText.Builder("14/28").build()).build()
            else -> null
        }
    }

    override fun getSupportedTypes(complicationId: Int): List<ComplicationType> {
        return listOf(
            ComplicationType.SHORT_TEXT,
            ComplicationType.LONG_TEXT,
            ComplicationType.RANGED_VALUE
        )
    }
}
