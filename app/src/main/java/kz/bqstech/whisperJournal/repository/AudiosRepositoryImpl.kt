package kz.bqstech.whisperJournal.repository

import kz.bqstech.whisperJournal.data.remote.JournalRequestItem
import kz.bqstech.whisperJournal.data.remote.JournalResponse
import kz.bqstech.whisperJournal.data.remote.JournalResponseItem
import kz.bqstech.whisperJournal.data.remote.MessageResponse
import kz.bqstech.whisperJournal.data.remote.MyApi
import kz.bqstech.whisperJournal.data.remote.toMultipartBodyPart
import kz.bqstech.whisperJournal.util.ApiResponse
import kz.bqstech.whisperJournal.util.safeAPIRequestCall
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class AudiosRepositoryImpl(val api: MyApi) : AudiosRepository {
    override suspend fun test(): ApiResponse<MessageResponse> {
        return safeAPIRequestCall { api.getUserData() }
    }

    override suspend fun getTestAudios(): ApiResponse<JournalResponse> {
        return safeAPIRequestCall {
            api.getTestAudio()
        }
    }

    override suspend fun addTestAudio(item: JournalRequestItem): ApiResponse<Unit> {
        val multipart =item.audio.toMultipartBodyPart()
        val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "My Recording")
        val duration = RequestBody.create("text/plain".toMediaTypeOrNull(), "120") // seconds
        return safeAPIRequestCall {

            api.addTestAudio(audio = multipart, name = name, time = duration)
        }
    }
}
