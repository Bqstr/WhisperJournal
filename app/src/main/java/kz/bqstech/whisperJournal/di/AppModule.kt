package kz.bqstech.whisperJournal.di

import android.system.Os.bind
import kz.bqstech.whisperJournal.HomeViewModel
import kz.bqstech.whisperJournal.audio.AndroidAudioPlayer
import kz.bqstech.whisperJournal.audio.AudioPlayer
import kz.bqstech.whisperJournal.recorder.AndroidAudioRecorder
import kz.bqstech.whisperJournal.recorder.AudioRecorder
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    //singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    viewModelOf(::HomeViewModel)
    singleOf(::AndroidAudioRecorder){bind<AudioRecorder>()  }
    singleOf(::AndroidAudioPlayer){bind<AudioPlayer>()  }


}