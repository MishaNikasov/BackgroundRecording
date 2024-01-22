package com.nikasov.domain.repository.entity

import android.net.Uri

data class Media(
    val id: Long,
    val name: String,
    val uri: Uri,
    val duration: Long,
)