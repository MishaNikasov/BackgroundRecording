package com.nikasov.common.manager.recordManager

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException
import javax.inject.Inject

class RecordManagerImpl @Inject constructor(
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
        get() = recordingState

    override fun start(uri: Uri) {
        val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "w")
        createRecorder().apply {
            setAudioSource(audioSource)
            setOutputFormat(outputFormat)
            setAudioEncoder(audioEncoder)
            setAudioEncodingBitRate(bitRate)
            setAudioSamplingRate(sampleRate)
            try {
                setOutputFile(fileDescriptor?.fileDescriptor)
                prepare()
                start()
                mediaRecorder = this
                recordingState.tryEmit(RecordingState.InProgress)
            } catch (e: IOException) {
                fileDescriptor?.close()
                e.printStackTrace()
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

    override fun cancel() {
        mediaRecorder?.apply {
            reset()
            release()
            mediaRecorder = null
            recordingState.tryEmit(RecordingState.Stopped)
        }
    }
}