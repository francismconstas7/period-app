package com.periodcalendar.tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class OvulationReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val daysUntilOvulation = intent.getIntExtra("DAYS_UNTIL_OVULATION", -1)
        val isDiscreet = intent.getBooleanExtra("IS_DISCREET", false)

        val title = if (isDiscreet) "Reminder" else "Ovulation Alert"
        val message = when {
            isDiscreet -> "Your reminder"
            daysUntilOvulation == 0 -> "Today is your predicted ovulation day!"
            daysUntilOvulation == 1 -> "Ovulation expected tomorrow!"
            else -> "Ovulation expected in $daysUntilOvulation days"
        }

        android.app.NotificationManagerCompat.from(context).notify(
            1003,
            androidx.core.app.NotificationCompat.Builder(context, NotificationManager.OVULATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()
        )
    }
}
