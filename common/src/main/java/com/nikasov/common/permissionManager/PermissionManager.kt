package com.nikasov.common.permissionManager

import android.app.Activity

interface PermissionManager {
    fun requestPermission(activity: Activity, permission: String, callback: (PermissionCallback) -> Unit)
}