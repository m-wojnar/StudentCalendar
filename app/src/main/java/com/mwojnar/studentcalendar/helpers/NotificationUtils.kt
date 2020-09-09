package com.mwojnar.studentcalendar.helpers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import com.mwojnar.studentcalendar.MainActivity
import com.mwojnar.studentcalendar.R
import java.util.concurrent.TimeUnit

fun createChannel(context:Context, channelId: String, channelName: String, channelDescription: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                enableVibration(true)
                description = channelDescription
            }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

fun NotificationManager.sendReminderNotification(context: Context, notificationId: Long, message: String) {
    // Go to MainActivity after click on notification
    val contentIntent = Intent(context, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context,
        notificationId.toInt(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Add "Remind me in 15 minutes" button
    val notifyPendingIntent = createScheduledNotifyPendingIntent(
        context, notificationId, message, TimeUnit.SECONDS.toMillis(15) + System.currentTimeMillis()
    )

    val builder =
        NotificationCompat.Builder(context, context.getString(R.string.reminders_channel_id))
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_baseline_refresh_24,
                context.getString(R.string.remind_in_15_min),
                notifyPendingIntent
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(notificationId.toInt(), builder.build())
}

fun scheduleNotification(context: Context, notificationId: Long, message: String, notificationTime: Long) {
    val notifyPendingIntent = createNotifyPendingIntent(
        context, notificationId, message
    )
    schedulePendingIntent(
        context, notifyPendingIntent, notificationTime
    )
}

private fun createNotifyPendingIntent(
    context: Context, notificationId: Long, message: String
) : PendingIntent {
    val notifyIntent = Intent(context, NotificationReceiver::class.java)
    notifyIntent.putExtra(context.getString(R.string.notification_id), notificationId)
    notifyIntent.putExtra(context.getString(R.string.notification_message), message)

    return PendingIntent.getBroadcast(
        context, notificationId.toInt(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
}

private fun createScheduledNotifyPendingIntent(
    context: Context, notificationId: Long, message: String, notificationTime: Long
) : PendingIntent {
    val notifyIntent = Intent(context, ScheduledNotificationReceiver::class.java)
    notifyIntent.putExtra(context.getString(R.string.notification_id), notificationId)
    notifyIntent.putExtra(context.getString(R.string.notification_message), message)
    notifyIntent.putExtra(context.getString(R.string.notification_time), notificationTime)

    return PendingIntent.getBroadcast(
        context, notificationId.toInt(), notifyIntent, 0
    )
}

private fun schedulePendingIntent(context: Context, pendingIntent: PendingIntent, notificationTime: Long) {
    AlarmManagerCompat.setAndAllowWhileIdle(
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager,
        AlarmManager.RTC_WAKEUP,
        notificationTime,
        pendingIntent
    )
}