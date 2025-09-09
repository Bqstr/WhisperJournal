package kz.bqstech.whisperJournal.data.remote

import com.google.gson.annotations.SerializedName
import kz.bqstech.whisperJournal.ui.audioList.JournalUiItem
import kz.bqstech.whisperJournal.ui.audioList.TextStatus
import kz.bqstech.whisperJournal.util.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File


interface MyApi {

    @GET("public/ping")
    suspend fun getUserData(): Response<MessageResponse>

    @Multipart
    @POST("/audio/upload")
    suspend fun addTestAudio(
        @Part audio: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("duration") time: RequestBody,



    ): Response<Unit>

    @GET("/audio/list")
    suspend fun getTestAudio(

    ): Response<JournalResponse>


}

data class JournalRequestItem(
//    @SerializedName("id") val id: Int,
    val name: String,
    val time:String,
    val audio: File
    )

data class JournalResponse(
    @SerializedName("audios") val audios: List<JournalResponseItem>,
    @SerializedName("count") val count: Int,

    )

data class JournalResponseItem(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Trascription") val trascription: String,
    @SerializedName("UploadedAt") val uploadedAt: String,
    @SerializedName("TranscriptionStatus") val transcription_status: TextStatus,
    @SerializedName("Duration") val duration: String,
    @SerializedName("AudioURI") val audioUri: String,
   ){

}
fun JournalResponseItem.toUiItem(): JournalUiItem{
    val dateAndTime = Utils.formatDateTime(this.uploadedAt)

    return JournalUiItem(
        id =this.id,
        name=this.name  ,
        text =this.trascription,
        time =dateAndTime.second,
        duration =this.duration,
        textStatus = this.transcription_status,
        date =dateAndTime.first,
        audioUri = this.audioUri
    )
}

data class MessageResponse(@SerializedName("message") val message: String)

fun File.toMultipartBodyPart(partName: String = "audio"): MultipartBody.Part {
    val requestBody = this.asRequestBody("audio/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, this.name, requestBody)
}