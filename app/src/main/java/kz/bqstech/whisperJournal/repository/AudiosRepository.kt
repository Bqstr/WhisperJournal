package kz.bqstech.whisperJournal.repository

import kz.bqstech.whisperJournal.data.remote.JournalRequestItem
import kz.bqstech.whisperJournal.data.remote.JournalResponse
import kz.bqstech.whisperJournal.data.remote.JournalResponseItem
import kz.bqstech.whisperJournal.data.remote.MessageResponse
import kz.bqstech.whisperJournal.util.ApiResponse

interface AudiosRepository {
    suspend fun test(): ApiResponse<MessageResponse>

    suspend fun getTestAudios():ApiResponse<JournalResponse>
    suspend fun addTestAudio(
        item: JournalRequestItem
    ): ApiResponse<Unit>


}
