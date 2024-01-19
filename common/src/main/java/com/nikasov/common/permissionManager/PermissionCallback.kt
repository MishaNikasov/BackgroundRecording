package com.nikasov.common.permissionManager

sealed class PermissionCallback {
    data object Granted : PermissionCallback()
    data object NotGranted : PermissionCallback()
    data object Rationale : PermissionCallback()
}