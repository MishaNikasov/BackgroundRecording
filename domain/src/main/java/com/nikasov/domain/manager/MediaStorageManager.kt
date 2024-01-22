package com.nikasov.domain.manager

import android.net.Uri

interface MediaStorageManager {
    suspend fun getMediaList(): List<MediaEntity>
    suspend fun createMediaUri(name: String): Uri?
    suspend fun removeMedia(uri: Uri)
}