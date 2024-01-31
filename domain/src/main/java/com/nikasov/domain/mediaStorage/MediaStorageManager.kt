package com.nikasov.domain.mediaStorage

import android.net.Uri

interface MediaStorageManager {
    val mediaStoreVersion: String
    suspend fun getMediaList(): List<MediaEntity>
    suspend fun createMediaUri(name: String): Uri?
    suspend fun removeMedia(uri: Uri)
}