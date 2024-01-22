package com.nikasov.data.recordStorage

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.nikasov.common.Constants
import com.nikasov.domain.repository.entity.Media

class RecordStorageManagerImpl(
    private val context: Context,
) : RecordStorageManager {

    override fun getRecordingsList(): List<Media> {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
        )
        val sortOrder = "${MediaStore.Audio.Media.DATE_TAKEN} DESC"
        val selection = "${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("%${Constants.RECORDINGS_FOLDER_NAME}%")
        val audios = mutableListOf<Media>()
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
                audios.add(Media(id, name, uri, duration))
            }
        }
        return audios.toList()
    }

    override fun createRecordUri(name: String) = context.contentResolver.insert(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, name)
            put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000).toInt())
            put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3")
            put(MediaStore.Audio.Media.RELATIVE_PATH, Constants.RECORDINGS_FOLDER_NAME)
        }
    )

    override fun removeRecord(uri: Uri) {
//        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
        context.contentResolver.delete(uri, null, null)
    }

}