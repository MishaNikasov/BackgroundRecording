package com.nikasov.common.audioManager

import android.net.Uri

interface AudioManager {
    fun play(uri: Uri)
    fun pause()
    fun stop()
}