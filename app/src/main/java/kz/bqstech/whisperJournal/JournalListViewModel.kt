package kz.bqstech.whisperJournal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class JournalListViewModel: ViewModel() {
    private val _state = MutableStateFlow(JournalListUiState())
    val state  =_state.asStateFlow()


}

data class JournalListUiState(
    val journalEntries :Map<String,List<JournalItem>> =emptyMap(),
    val searchQuery:String =""
)