package com.nikasov.common.manager.recordManager

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface RecordManager {
    val state: Flow<RecordingState>
    fun start(uri: Uri)
    fun pause()
    fun resume()
    fun stop()
    fun cancel()
}