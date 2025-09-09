package kz.bqstech.whisperJournal.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080") // change to your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: MyApi by lazy {
        retrofit.create(MyApi::class.java)
    }
}