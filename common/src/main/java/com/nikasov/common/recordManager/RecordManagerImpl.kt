package com.nikasov.common.recordManager

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.IOException

class RecordManagerImpl(
    private val context: Context,
) : RecordManager {

    private val audioSource: Int = MediaRecorder.AudioSource.MIC

    private val audioEncoder: Int = MediaRecorder.AudioEncoder.AAC
    private val outputFormat: Int = MediaRecorder.OutputFormat.MPEG_4

    private val bitDepth = 16
    private val sampleRate = 44100
    private val bitRate = sampleRate * bitDepth

    private var mediaRecorder: MediaRecorder? = null

    private val recordingState = MutableStateFlow(RecordingState.Stopped)

    private fun createRecorder() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        MediaRecorder()
    }

    override val state: Flow<RecordingState>
        get() = recordingState.asStateFlow()

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(audioSource)
            setOutputFormat(outputFormat)
            setAudioEncoder(audioEncoder)
            setOutputFile(outputFile)
            setAudioEncodingBitRate(bitRate)
            setAudioSamplingRate(sampleRate)

            try {
                prepare()
                start()
                mediaRecorder = this
                recordingState.tryEmit(RecordingState.InProgress)
            } catch (e: IOException) {
                Log.e("RecordManagerImpl", e.localizedMessage.orEmpty())
            }
        }
    }

    override fun resume() {
        mediaRecorder?.apply {
            resume()
            recordingState.tryEmit(RecordingState.InProgress)
        }
    }

    override fun pause() {
        mediaRecorder?.apply {
            pause()
            recordingState.tryEmit(RecordingState.Paused)
        }
    }

    override fun stop() {
        mediaRecorder?.apply {
            stop()
            release()
            mediaRecorder = null
            recordingState.tryEmit(RecordingState.Stopped)
        }
    }
}