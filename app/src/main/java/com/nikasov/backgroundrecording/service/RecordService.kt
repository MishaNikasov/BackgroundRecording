package com.nikasov.backgroundrecording.service

import android.app.Service
import android.content.Context
import android.content.Intent
import com.nikasov.backgroundrecording.service.RecordEvent.Cancel
import com.nikasov.backgroundrecording.service.RecordEvent.Pause
import com.nikasov.backgroundrecording.service.RecordEvent.Resume
import com.nikasov.backgroundrecording.service.RecordEvent.Start
import com.nikasov.backgroundrecording.service.RecordEvent.Stop
import com.nikasov.common.manager.recordManager.RecordManager
import com.nikasov.domain.mediaStorage.MediaStorageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecordService : Service() {

    @Inject
    lateinit var recordManager: RecordManager

    @Inject
    lateinit var mediaStorageManager: MediaStorageManager

    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        fun sendEvent(context: Context, recordEvent: RecordEvent) {
            Intent(context, RecordService::class.java).apply {
                action = recordEvent.name
                context.startService(this)
            }
        }
    }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { handleEvent(RecordEvent.valueOf(it)) }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleEvent(recordEvent: RecordEvent) {
        when (recordEvent) {
            Start -> startRecording()
            Pause -> pauseRecording()
            Stop -> stopRecording()
            Resume -> resumeRecording()
            Cancel -> cancelRecording()
        }
    }

    private fun startRecording() {
        scope.launch {
            mediaStorageManager.createMediaUri("sampleAudio.mp3")?.let { recordManager.start(it) }
        }
    }

    private fun pauseRecording() {
        recordManager.pause()
    }

    private fun resumeRecording() {
        recordManager.resume()
    }

    private fun stopRecording() {
        recordManager.stop()
    }

    private fun cancelRecording() {
        recordManager.cancel()
    }

}