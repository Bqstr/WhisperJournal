package kz.bqstech.whisperJournal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class JournalListViewModel: ViewModel() {
//    private val _state = MutableStateFlow(JournalListUiState())
//    val state  =_state.asStateFlow()

     val _state = MutableLiveData<JournalListUiState>(
        JournalListUiState(searchQuery = "", journalEntries = emptyMap())
    )
    val state : LiveData<JournalListUiState> =_state

    fun addItemToList(item: JournalItem){

        Log.d("sdfasdfasdfasf","1")

        val newMap =_state.value?.journalEntries?.toMutableMap()
        Log.d("sdfasdfasdfasf","1")

        val newList =(newMap?.get(item.time)?: emptyList()).toMutableList()
        Log.d("sdfasdfasdfasf","2")

        newList.add(item)
        Log.d("sdfasdfasdfasf",newList.toString())
        newMap?.put(item.time,newList.toList())
        //_state.value =_state.value?.copy(journalEntries = newMap?: emptyMap())
        _state.value =JournalListUiState(journalEntries = newMap ?: emptyMap(), searchQuery = "123")
    }


}

data class JournalListUiState(
    val journalEntries :Map<String,List<JournalItem>> =emptyMap(),
    val searchQuery:String =""
)