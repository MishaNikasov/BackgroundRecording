package com.nikasov.backgroundrecording

import android.app.Application
import com.nikasov.common.manager.notification.NotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.createChannel()
    }

}