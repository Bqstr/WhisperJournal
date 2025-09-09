package kz.bqstech.whisperJournal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kz.bqstech.whisperJournal.data.remote.JournalRequestItem
import kz.bqstech.whisperJournal.repository.AudiosRepository
import org.koin.android.ext.android.inject
import java.io.File

const val RECORDING_STATE_CHANGED = "RECORDING_STATE_CHANGED"
const val RECORDING_EXTRA = "isRecording"


class AudioRecordingService : Service() {
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var outputFile: File
    private var startTieMillis: Long = 0L
    private var timerJob: Job? = null

    private val NOTIFICATION_SERVICE = "notification_service"
    private val timer: Int? = null

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val audioRepo: AudiosRepository by inject()


    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification())
    }

    private fun notifyUIRecordingStarted(value: Boolean) {
        val intent = Intent(RECORDING_STATE_CHANGED)
        intent.putExtra(RECORDING_EXTRA, value)
        sendBroadcast(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        outputFile = createAudioFile()
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(outputFile.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            prepare()
            start()
            notifyUIRecordingStarted(true)
        }
        mediaRecorder?.setOnInfoListener { a, b, c ->
            Log.d("sadfasdfasdffrfrfr", "$a   ${b}   ${c}")
        }
        return START_NOT_STICKY
    }


    fun stopRecording(): File? {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        serviceScope.launch {
            audioRepo.addTestAudio(
                JournalRequestItem(
                    audio = outputFile,
                    name = "",
                    time = ""
                )
            )
        }

        return outputFile
    }


    override fun onDestroy() {


        stopRecording()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_SERVICE,
                "Audio Recording",
                NotificationManager.IMPORTANCE_HIGH,

                )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, NOTIFICATION_SERVICE)
            .setContentTitle("Recording Audio ")
            .setContentText("Your voice is being recorded.")
            .setSmallIcon(R.drawable.mic_image)
            .build()
    }


    private fun startTimer() {
        startTieMillis = System.currentTimeMillis()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        timerJob = CoroutineScope(Dispatchers.Default).launch {
            val elapsedMillis = System.currentTimeMillis() - startTieMillis
            val seconds = (elapsedMillis / 1000) % 60
            val minutes = (elapsedMillis / 1000) / 60

            val timeFormatted = String.format("%02d:%02d", minutes, seconds)
            val notification =
                NotificationCompat.Builder(this@AudioRecordingService, NOTIFICATION_SERVICE)
                    .setContentTitle("Recording Audio")
                    .setContentText("Recording for $timeFormatted")
                    .setSmallIcon(R.drawable.mic_image)
                    .build()
            notificationManager.notify(1, notification)
        }


    }

    private fun createAudioFile(): File {
        val fileName = "audio_${System.currentTimeMillis()}.3gp"
        return File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)
    }


}
