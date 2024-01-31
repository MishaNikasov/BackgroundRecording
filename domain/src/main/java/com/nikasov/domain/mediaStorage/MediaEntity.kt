package com.nikasov.domain.mediaStorage

import android.net.Uri

data class MediaEntity(
    val id: Long,
    val date: String,
    val name: String,
    val uri: Uri,
    val duration: Long,
)