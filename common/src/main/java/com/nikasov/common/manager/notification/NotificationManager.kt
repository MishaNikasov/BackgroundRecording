package com.nikasov.common.manager.notification

import android.app.Notification

interface NotificationManager {
    fun createChannel()
    fun createNotification(): Notification
}