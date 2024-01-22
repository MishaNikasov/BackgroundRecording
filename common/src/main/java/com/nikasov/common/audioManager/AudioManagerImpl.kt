package com.nikasov.common.audioManager

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.IOException

class AudioManagerImpl(
    private val context: Context,
) : AudioManager {

    private var player: MediaPlayer? = null

    override fun play(uri: Uri) {
        MediaPlayer().apply {
            setDataSource(context, uri)
            try {
                prepare()
                start()
                player = this
            } catch (e: IOException) {
                Log.e("AudioManagerImpl", "prepare() failed")
            }
        }
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.apply {
            stop()
            release()
            player = null
        }
    }
}