package com.broprojects.studentcalendar.helpers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.broprojects.studentcalendar.R

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
        val notificationTime = intent.getLongExtra(context?.getString(R.string.notification_time), 0L)

        scheduleNotification(context!!, notificationId, message, notificationTime)
    }
}