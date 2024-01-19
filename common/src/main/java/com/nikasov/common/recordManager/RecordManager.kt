package com.nikasov.common.recordManager

import kotlinx.coroutines.flow.Flow
import java.io.File

interface RecordManager {
    val state: Flow<RecordingState>
    fun start(outputFile: File)
    fun pause()
    fun resume()
    fun stop()
}