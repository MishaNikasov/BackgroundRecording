package com.nikasov.common.manager.permissionManager

sealed class PermissionCallback {
    data object Granted : PermissionCallback()
    data object NotGranted : PermissionCallback()
    data object Rationale : PermissionCallback()
}