package com.nikasov.data.recordStorage

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.nikasov.common.Constants
import com.nikasov.domain.manager.MediaEntity
import com.nikasov.domain.manager.MediaStorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaStorageManagerImpl(
    private val context: Context,
) : MediaStorageManager {

    override suspend fun getMediaList(): List<MediaEntity> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
        )
        val sortOrder = "${MediaStore.Audio.Media.DATE_TAKEN} DESC"
        val selection = "${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("%${Constants.RECORDINGS_FOLDER_NAME}%")
        val mediaList = mutableListOf<MediaEntity>()
        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                mediaList.add(MediaEntity(id, name, uri, duration))
            }
        }
        return@withContext mediaList.toList()
    }

    override suspend fun createMediaUri(name: String): Uri? = withContext(Dispatchers.IO) {
        val uri = context.contentResolver.insert(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.Audio.Media.DISPLAY_NAME, name)
                put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000).toInt())
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3")
                put(MediaStore.Audio.Media.RELATIVE_PATH, Constants.RECORDINGS_FOLDER_NAME)
            }
        )
        return@withContext uri
    }

    override suspend fun removeMedia(uri: Uri): Unit = withContext(Dispatchers.IO) {
        context.contentResolver.delete(uri, null, null)
    }

}