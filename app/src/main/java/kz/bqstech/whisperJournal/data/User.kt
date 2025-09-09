package kz.bqstech.whisperJournal.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class User(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String
)