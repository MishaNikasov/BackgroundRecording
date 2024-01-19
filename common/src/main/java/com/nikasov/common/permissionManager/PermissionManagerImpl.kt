package com.nikasov.common.permissionManager

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManagerImpl: PermissionManager {

    override fun requestPermission(activity: Activity, permission: String, callback: (PermissionCallback) -> Unit) {
        when {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED -> callback(PermissionCallback.Granted)
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> callback(PermissionCallback.Rationale)
            else -> callback(PermissionCallback.NotGranted)
        }
    }

}