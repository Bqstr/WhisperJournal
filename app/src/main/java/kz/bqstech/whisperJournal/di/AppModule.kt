package kz.bqstech.whisperJournal.di

import android.system.Os.bind
import kz.bqstech.whisperJournal.HomeViewModel
import kz.bqstech.whisperJournal.JournalListViewModel
import kz.bqstech.whisperJournal.audio.AndroidAudioPlayer
import kz.bqstech.whisperJournal.audio.AudioPlayer
import kz.bqstech.whisperJournal.recorder.AndroidAudioRecorder
import kz.bqstech.whisperJournal.recorder.AudioRecorder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    //singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AndroidAudioRecorder){bind<AudioRecorder>()  }
    singleOf(::AndroidAudioPlayer){bind<AudioPlayer>()  }
    viewModel {
        HomeViewModel(
            context = get(),              // Context or Application
            audioPlayer = get(),
            audioRecorder = get()
        )
    }
    viewModelOf(::JournalListViewModel)


}