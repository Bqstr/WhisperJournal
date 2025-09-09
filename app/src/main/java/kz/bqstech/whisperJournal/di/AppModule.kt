package kz.bqstech.whisperJournal.di

import kz.bqstech.whisperJournal.HomeViewModel
import kz.bqstech.whisperJournal.JournalListViewModel
import kz.bqstech.whisperJournal.audio.AndroidAudioPlayer
import kz.bqstech.whisperJournal.audio.AudioPlayer
import kz.bqstech.whisperJournal.data.remote.MyApi
import kz.bqstech.whisperJournal.recorder.AndroidAudioRecorder
import kz.bqstech.whisperJournal.recorder.AudioRecorder
import kz.bqstech.whisperJournal.repository.AudiosRepository
import kz.bqstech.whisperJournal.repository.AudiosRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.EmptyCoroutineContext.get

val appModule = module {
    //singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AndroidAudioRecorder) { bind<AudioRecorder>() }
    singleOf(::AndroidAudioPlayer) { bind<AudioPlayer>() }
    single<MyApi> {
        get<Retrofit>().create(MyApi::class.java)
    }
    single {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // change to your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
                .build()
    }
    single<AudiosRepository> { AudiosRepositoryImpl(get()) }


    viewModel {
        HomeViewModel(
            context = get(),              // Context or Application
            audioPlayer = get(),
            audioRecorder = get(),
            audiosRepository = get()
        )
    }
    viewModel {
        JournalListViewModel(
            audiosRepository = get()
        )
    }

        //viewModelOf(::JournalListViewModel)


}
