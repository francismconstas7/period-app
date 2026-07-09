package com.periodcalendar.tracker.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.periodcalendar.tracker.R
import com.periodcalendar.tracker.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val PERIOD_CHANNEL_ID = "period_reminder_channel"
        const val PILL_CHANNEL_ID = "pill_reminder_channel"
        const val OVULATION_CHANNEL_ID = "ovulation_reminder_channel"
        const val FERTILITY_CHANNEL_ID = "fertility_channel"
        
        const val PERIOD_NOTIFICATION_ID = 1001
        const val PILL_NOTIFICATION_ID = 1002
        const val OVULATION_NOTIFICATION_ID = 1003
        const val FERTILITY_NOTIFICATION_ID = 1004
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Period Reminder Channel
            val periodChannel = NotificationChannel(
                PERIOD_CHANNEL_ID,
                "Period Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for upcoming periods"
                enableVibration(true)
            }

            // Pill Reminder Channel
            val pillChannel = NotificationChannel(
                PILL_CHANNEL_ID,
                "Pill Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for contraceptive pill reminders"
                enableVibration(true)
            }

            // Ovulation Channel
            val ovulationChannel = NotificationChannel(
                OVULATION_CHANNEL_ID,
                "Ovulation Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for ovulation predictions"
                enableVibration(false)
            }

            // Fertility Channel
            val fertilityChannel = NotificationChannel(
                FERTILITY_CHANNEL_ID,
                "Fertility Updates",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Daily fertility status updates"
                enableVibration(false)
            }

            notificationManager.createNotificationChannels(
                listOf(periodChannel, pillChannel, ovulationChannel, fertilityChannel)
            )
        }
    }

    fun showPeriodReminder(daysUntilPeriod: Int, isDiscreet: Boolean = false) {
        val title = if (isDiscreet) "Reminder" else "Period Reminder"
        val message = when {
            isDiscreet -> "Your reminder"
            daysUntilPeriod == 0 -> "Your period is expected today!"
            daysUntilPeriod == 1 -> "Your period is expected tomorrow!"
            else -> "Your period is expected in $daysUntilPeriod days"
        }

        showNotification(
            channelId = PERIOD_CHANNEL_ID,
            notificationId = PERIOD_NOTIFICATION_ID,
            title = title,
            message = message,
            isDiscreet = isDiscreet
        )
    }

    fun showPillReminder(reminderText: String, isDiscreet: Boolean = false, discreetText: String = "Reminder") {
        val title = if (isDiscreet) discreetText else "Pill Reminder"
        val message = if (isDiscreet) discreetText else reminderText

        showNotification(
            channelId = PILL_CHANNEL_ID,
            notificationId = PILL_NOTIFICATION_ID,
            title = title,
            message = message,
            isDiscreet = isDiscreet
        )
    }

    fun showOvulationReminder(daysUntilOvulation: Int, isDiscreet: Boolean = false) {
        val title = if (isDiscreet) "Reminder" else "Ovulation Alert"
        val message = when {
            isDiscreet -> "Your reminder"
            daysUntilOvulation == 0 -> "Today is your predicted ovulation day!"
            daysUntilOvulation == 1 -> "Ovulation expected tomorrow!"
            else -> "Ovulation expected in $daysUntilOvulation days"
        }

        showNotification(
            channelId = OVULATION_CHANNEL_ID,
            notificationId = OVULATION_NOTIFICATION_ID,
            title = title,
            message = message,
            isDiscreet = isDiscreet
        )
    }

    fun showFertilityUpdate(fertilityLevel: String, conceptionChance: Double, isDiscreet: Boolean = false) {
        val title = if (isDiscreet) "Update" else "Fertility Update"
        val message = if (isDiscreet) {
            "Daily update"
        } else {
            "Fertility: $fertilityLevel (${(conceptionChance * 100).toInt()}% chance)"
        }

        showNotification(
            channelId = FERTILITY_CHANNEL_ID,
            notificationId = FERTILITY_NOTIFICATION_ID,
            title = title,
            message = message,
            isDiscreet = isDiscreet
        )
    }

    private fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        message: String,
        isDiscreet: Boolean = false
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}
