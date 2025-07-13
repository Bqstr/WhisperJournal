package kz.bqstech.whisperJournal.recorder

import android.content.Context
import java.io.File

interface AudioRecorder {
    fun startRecording(file: File,onRecorderStarting:() ->Unit)
    fun stopRecording()
    fun createAudioFile(fileName:String): File
}