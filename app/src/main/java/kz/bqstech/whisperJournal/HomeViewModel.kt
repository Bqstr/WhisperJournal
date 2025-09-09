package kz.bqstech.whisperJournal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.platform.DisableContentCapture
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.bqstech.whisperJournal.audio.AudioPlayer
import kz.bqstech.whisperJournal.recorder.AudioRecorder
import kz.bqstech.whisperJournal.repository.AudiosRepository
import kz.bqstech.whisperJournal.util.ApiResponse
import java.io.File

class HomeViewModel(
    private val context: Context,//TODO read about passing context to viewModel, is it safe?
    private val audioPlayer: AudioPlayer,
    private val audioRecorder: AudioRecorder,
    private val audiosRepository: AudiosRepository
) : ViewModel() {

    var audioFile: File? = null//TODO :should i put it to stateFlow








    fun test(){
        viewModelScope.launch(Dispatchers.IO) {
           Log.d("sdfjsakdfjlkasdfjl;asdf",audiosRepository.test().toString())

        }
    }

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

