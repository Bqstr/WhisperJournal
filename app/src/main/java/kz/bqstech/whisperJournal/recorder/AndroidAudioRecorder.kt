package kz.bqstech.whisperJournal.recorder

import android.content.ContentValues
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

class AndroidAudioRecorder(
    private val context: Context
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var file: File? = null

    fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun startRecording(file: File,onRecorderStarting:() ->Unit) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)//TODO: read about formats,choose best option
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)//TODO: read about this too
            setOutputFile(FileOutputStream(file).fd)

            prepare()
            start()

            recorder = this
            onRecorderStarting.invoke()

        }
    }

    override fun stopRecording() {
        recorder?.stop()
        recorder?.reset()
        file.let {
            copyFileToPublicMusicFolder(
                context,
                sourceFile = it!!,
                fileName = file?.name ?: "some_shit"
            )
        }
    }

    //TODO:think later about place of this function , (like can i use context from koin to this fucntion )
    override fun createAudioFile(fileName: String): File {
        val timestamp = System.currentTimeMillis()
        val fileName = "${fileName}"
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)//TODO read about directories
        file = File(storageDir, fileName)
        return file!! //TODO chnage that
    }


    fun copyFileToPublicMusicFolder(context: Context, sourceFile: File, fileName: String): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, "$fileName.m4a")
            put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp4")
            put(
                MediaStore.Audio.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_MUSIC}/WhisperRecordings"
            )
            put(
                MediaStore.Audio.Media.IS_PENDING,
                1
            ) // mark file as pending (not visible until done)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri)?.use { outputStream ->
                sourceFile.inputStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            // Mark as not pending so it becomes visible to other apps
            contentValues.clear()
            contentValues.put(MediaStore.Audio.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)

            return true
        }

        return false
    }


}