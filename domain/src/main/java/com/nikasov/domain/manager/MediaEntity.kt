package com.nikasov.domain.manager

import android.net.Uri

data class MediaEntity(
    val id: Long,
    val name: String,
    val uri: Uri,
    val duration: Long,
)