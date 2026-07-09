package com.periodcalendar.tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PeriodReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        // Hilt doesn't support broadcast receivers directly, so we'll use a simple approach
        val daysUntilPeriod = intent.getIntExtra("DAYS_UNTIL_PERIOD", -1)
        val isDiscreet = intent.getBooleanExtra("IS_DISCREET", false)
        
        if (daysUntilPeriod >= 0) {
            showPeriodNotification(context, daysUntilPeriod, isDiscreet)
        }
    }

    private fun showPeriodNotification(context: Context, daysUntilPeriod: Int, isDiscreet: Boolean) {
        val title = if (isDiscreet) "Reminder" else "Period Reminder"
        val message = when {
            isDiscreet -> "Your reminder"
            daysUntilPeriod == 0 -> "Your period is expected today!"
            daysUntilPeriod == 1 -> "Your period is expected tomorrow!"
            else -> "Your period is expected in $daysUntilPeriod days"
        }

        android.app.NotificationManagerCompat.from(context).notify(
            1001,
            androidx.core.app.NotificationCompat.Builder(context, NotificationManager.PERIOD_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()
        )
    }
}
