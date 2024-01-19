package com.nikasov.common.audioManager

import java.io.File

interface AudioManager {
    fun play(file: File)
    fun pause()
    fun stop()
}