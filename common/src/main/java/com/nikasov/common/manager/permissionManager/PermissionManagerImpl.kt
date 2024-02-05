package com.nikasov.common.manager.permissionManager

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject

class PermissionManagerImpl @Inject constructor(): PermissionManager {

    override fun requestPermission(activity: Activity, permission: String, callback: (PermissionCallback) -> Unit) {
        when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> callback(PermissionCallback.Granted)
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> callback(PermissionCallback.Rationale)
            else -> callback(PermissionCallback.NotGranted)
        }
    }

}