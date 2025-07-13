package kz.bqstech.whisperJournal.audio

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()

}