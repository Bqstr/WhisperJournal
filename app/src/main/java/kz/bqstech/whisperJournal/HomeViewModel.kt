package kz.bqstech.whisperJournal

import android.content.Context
import androidx.lifecycle.ViewModel
import kz.bqstech.whisperJournal.audio.AudioPlayer
import kz.bqstech.whisperJournal.recorder.AudioRecorder
import java.io.File

class HomeViewModel(
    private val context: Context,//TODO read about passing context to viewModel, is it safe?
    private val audioPlayer: AudioPlayer,
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    var audioFile: File? = null//TODO :should i put it to stateFlow

    fun startRecording(
        onRecorderStarting: () -> Unit
    ) {
        val file = audioRecorder.createAudioFile("abobus").apply {
            audioFile = this
        }
        audioRecorder.startRecording(file, onRecorderStarting)
    }

    fun stopRecording() {
        audioRecorder.stopRecording()
    }

    fun playAudioTest() {
        audioFile.let { file ->
            audioPlayer.playFile(file!!)
        }
    }

    fun stopAudioTest() {
        audioPlayer.stop()
    }


}