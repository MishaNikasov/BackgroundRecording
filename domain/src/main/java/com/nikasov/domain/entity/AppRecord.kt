package com.nikasov.domain.entity

import android.net.Uri
import java.time.LocalDate

data class AppRecord(
    val id: Long,
    val date: LocalDate,
    val name: String,
    val uri: Uri,
    val duration: Long
)
