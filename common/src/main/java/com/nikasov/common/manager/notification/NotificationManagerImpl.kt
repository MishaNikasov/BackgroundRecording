package com.nikasov.common.manager.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import javax.inject.Inject

class NotificationManagerImpl @Inject constructor(
    private val context: Context,
) : NotificationManager {

    companion object {
        private const val CHANNEL_ID = "123"
        private const val CHANNEL_NAME = "Record channel"
    }

    private val notificationManager: android.app.NotificationManager
        get() = context.getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager

    override fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            android.app.NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun createNotification(): Notification =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("Recording")
            .setContentText("In progress")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

}