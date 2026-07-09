package com.periodcalendar.tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PillReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderText = intent.getStringExtra("REMINDER_TEXT") ?: "Time to take your pill"
        val isDiscreet = intent.getBooleanExtra("IS_DISCREET", false)
        val discreetText = intent.getStringExtra("DISCREET_TEXT") ?: "Reminder"

        val title = if (isDiscreet) discreetText else "Pill Reminder"
        val message = if (isDiscreet) discreetText else reminderText

        android.app.NotificationManagerCompat.from(context).notify(
            1002,
            androidx.core.app.NotificationCompat.Builder(context, NotificationManager.PILL_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
        )
    }
}
