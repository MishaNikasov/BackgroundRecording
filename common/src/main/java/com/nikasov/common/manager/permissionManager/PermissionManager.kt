package com.nikasov.common.manager.permissionManager

import android.app.Activity

interface PermissionManager {
    fun requestPermission(activity: Activity, permission: String, callback: (PermissionCallback) -> Unit)
}