package com.mwojnar.studentcalendar.helpers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mwojnar.studentcalendar.R
import java.util.concurrent.TimeUnit

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getLongExtra(context?.getString(R.string.notification_id), 0L)
        val message = intent?.getStringExtra(context?.getString(R.string.notification_message))

        val notificationManager = ContextCompat.getSystemService(context!!, NotificationManager::class.java)
        notificationManager?.sendReminderNotification(context, notificationId!!, message!!)
    }
}

class ScheduledNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getLongExtra(context?.getString(R.string.notification_id), 0L)!!
        val message = intent.getStringExtra(context?.getString(R.string.notification_message))!!
        // Remind in 15 minutes
        val notificationTime = TimeUnit.MINUTES.toMillis(15) + System.currentTimeMillis()

        scheduleNotification(context!!, notificationId, message, notificationTime)
    }
}