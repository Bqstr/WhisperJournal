package kz.bqstech.whisperJournal

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.bqstech.whisperJournal.data.remote.JournalRequestItem
import kz.bqstech.whisperJournal.data.remote.toUiItem
import kz.bqstech.whisperJournal.repository.AudiosRepository
import kz.bqstech.whisperJournal.ui.audioList.JournalUiItem
import kz.bqstech.whisperJournal.util.handleResponse

class JournalListViewModel(
    private val audiosRepository: AudiosRepository

) : ViewModel() {
//    private val _state = MutableStateFlow(JournalListUiState())
//    val state  =_state.asStateFlow()

    val _state = MutableLiveData<JournalListUiState>(
        JournalListUiState(searchQuery = "", journalEntries = emptyMap())
    )
    val state: LiveData<JournalListUiState> = _state


    fun addTestJournalList(item: JournalRequestItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = audiosRepository.addTestAudio(item)
            response.handleResponse(onSuccess = {
                Log.d("urfhyrhfyrhfyrhfry", "succ")
            }, onFailure = {
                Log.d("urfhyrhfyrhfyrhfry", it.toString())

            })
        }
    }

    fun setState(newState: JournalListUiState?) {
        newState.let {
            _state.postValue(it)
        }

    }

    fun getTestJournalList(onSuccess:(JournalListUiState?) ->Unit ,onFail :(JournalListUiState?) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = audiosRepository.getTestAudios()
            response.handleResponse(
                onSuccess = {
                    Log.d("sdfasdfasfrfrfrfr", it.toString())
                    val newMap = it.audios.map { it.toUiItem() }.convertToMap()
                    Log.d("sdfasdfasfrfrfrfr", newMap.toString())

                    onSuccess(_state.value?.copy(journalEntries = newMap))
//                    setState(_state.value?.copy(journalEntries = newMap))
                }, onFailure = {
                    Log.d("sdfasdfasfrfrfrfr", it.toString())
                    onFail(_state.value?.copy(journalEntries = emptyMap()))


                })
        }
    }


    fun addItemToList(item: JournalUiItem) {

        Log.d("sdfasdfasdfasf", "1")

        val newMap = _state.value?.journalEntries?.toMutableMap()
        Log.d("sdfasdfasdfasf", "1")

        val newList = (newMap?.get(item.time) ?: emptyList()).toMutableList()
        Log.d("sdfasdfasdfasf", "2")

        newList.add(item)
        Log.d("sdfasdfasdfasf", newList.toString())
        newMap?.put(item.time, newList.toList())
        //_state.value =_state.value?.copy(journalEntries = newMap?: emptyMap())
        _state.value =
            JournalListUiState(journalEntries = newMap ?: emptyMap(), searchQuery = "")
    }

    fun playAudioFromUri(context: Context, audioUri: String) {

        val testUri = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        val player = ExoPlayer.Builder(context).build()
        // Build the media item.
        val mediaItem = MediaItem.fromUri(testUri)

        // Set the media item to be played.
        player.setMediaItem(mediaItem)

        player.addListener(object : Player.Listener {

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_IDLE -> {
                        Log.d("AudioPlayer", "Player is idle")
                    }

                    Player.STATE_BUFFERING -> {
                        Log.d("AudioPlayer", "Buffering / Downloading audio")
                    }

                    Player.STATE_READY -> {
                        Log.d("AudioPlayer", "Ready to play")
                    }

                    Player.STATE_ENDED -> {
                        Log.d("AudioPlayer", "Playback ended")
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.e("AudioPlayer", "Error: ${error.message}")
            }
        })

        // Prepare the player.
        player.prepare()

        // Start the playback.
        player.play()


    }


}

data class JournalListUiState(
    val journalEntries: Map<String, List<JournalUiItem>> = emptyMap(),
    val searchQuery: String = ""
)

fun List<JournalUiItem>.convertToMap(): Map<String, List<JournalUiItem>> {
    val newMap = mutableMapOf<String, List<JournalUiItem>>()
    this.forEach { item ->
        val list = (newMap.get(item.date) ?: emptyList()).toMutableList()
        list.add(item)
        Log.d("asdfasdfrfrfrfr",list.toString())
        newMap.put(item.date, list)
    }

    return newMap
}
