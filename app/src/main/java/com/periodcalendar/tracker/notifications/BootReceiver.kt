package com.periodcalendar.tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            // Reschedule all pending notifications after boot
            rescheduleNotifications(context)
        }
    }

    private fun rescheduleNotifications(context: Context) {
        val prefs = context.getSharedPreferences("period_tracker_prefs", Context.MODE_PRIVATE)
        
        // Check if reminders are enabled and reschedule them
        val pillReminderEnabled = prefs.getBoolean("pill_reminder_enabled", false)
        if (pillReminderEnabled) {
            val reminderTime = prefs.getString("pill_reminder_time", "08:00") ?: "08:00"
            schedulePillReminder(context, reminderTime)
        }

        val periodReminderEnabled = prefs.getBoolean("period_reminder_enabled", true)
        if (periodReminderEnabled) {
            // Schedule period reminder for 3 days before expected period
            val daysBefore = prefs.getInt("period_reminder_days", 3)
            schedulePeriodReminder(context, daysBefore)
        }
    }

    private fun schedulePillReminder(context: Context, time: String) {
        val (hour, minute) = time.split(":").map { it.toInt() }
        
        val workRequest = OneTimeWorkRequestBuilder<PillReminderWorker>()
            .setInitialDelay(calculateDelay(hour, minute), TimeUnit.MILLISECONDS)
            .addTag("pill_reminder")
            .build()
        
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    private fun schedulePeriodReminder(context: Context, daysBefore: Int) {
        val workRequest = OneTimeWorkRequestBuilder<PeriodReminderWorker>()
            .addTag("period_reminder")
            .build()
        
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    private fun calculateDelay(hour: Int, minute: Int): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
        }
        
        val delay = calendar.timeInMillis - System.currentTimeMillis()
        return if (delay < 0) {
            delay + 24 * 60 * 60 * 1000 // Schedule for tomorrow
        } else {
            delay
        }
    }
}

// Simple workers for scheduling
class PillReminderWorker(
    appContext: android.content.Context,
    params: androidx.work.WorkerParameters
) : androidx.work.Worker(appContext, params) {
    override fun doWork(): Result {
        val prefs = applicationContext.getSharedPreferences("period_tracker_prefs", android.content.Context.MODE_PRIVATE)
        val reminderText = prefs.getString("pill_notification_text", "Time to take your pill") ?: "Time to take your pill"
        val isDiscreet = prefs.getBoolean("pill_discreet_mode", false)
        val discreetText = prefs.getString("pill_discreet_text", "Reminder") ?: "Reminder"

        android.app.NotificationManagerCompat.from(applicationContext).notify(
            1002,
            androidx.core.app.NotificationCompat.Builder(applicationContext, NotificationManager.PILL_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(if (isDiscreet) discreetText else "Pill Reminder")
                .setContentText(if (isDiscreet) discreetText else reminderText)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
        )
        
        return Result.success()
    }
}

class PeriodReminderWorker(
    appContext: android.content.Context,
    params: androidx.work.WorkerParameters
) : androidx.work.Worker(appContext, params) {
    override fun doWork(): Result {
        // This would be enhanced with actual cycle prediction logic
        return Result.success()
    }
}
