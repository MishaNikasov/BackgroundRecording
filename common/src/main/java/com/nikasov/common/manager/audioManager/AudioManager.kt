package com.nikasov.common.manager.audioManager

import android.net.Uri

interface AudioManager {
    fun play(uri: Uri)
    fun pause()
    fun stop()
}