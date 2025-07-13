package kz.bqstech.whisperJournal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.bqstech.whisperJournal.ui.theme.Fonts.baldFontWorkSans
import kz.bqstech.whisperJournal.ui.theme.Fonts.mediumWorkSans
import kz.bqstech.whisperJournal.ui.theme.Fonts.regularWorkSans
import kz.bqstech.whisperJournal.ui.theme.GrayText
import kz.bqstech.whisperJournal.util.Space
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarJournalScreen() {
    val viewModel : JournalListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val journalItems =state.journalEntries

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        var text by remember { mutableStateOf("") }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        {
            OutlinedTextField(
                shape =RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = 16.dp
                            )
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                value = text,
                onValueChange = {
                    text = it
                },
                label = {
                },
                placeholder = {
                    Text(
                        text = "Search  your thoughts...",
                        fontSize = 16.sp,
                        color = GrayText,
                        modifier = Modifier.align(
                            Alignment.CenterVertically
                        )
                    )
                },
            )
        }
        Space(12.dp)

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ){
            journalItems.entries.forEach { (date, items) ->
                stickyHeader {
                    DateInJournalList(date)
                }
                items(items) { element ->
                    ItemInJournalList(element)
                }
            }

        }
    }


}

@Composable
fun ItemInJournalList(item: JournalItem) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Box(
            Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                painter = painterResource(R.drawable.mic_image),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
        Space(16.dp)
        Column(Modifier.weight(1f)) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = item.name ?: item.text ?: "",
                fontSize = 16.sp,
                fontFamily = mediumWorkSans,
                lineHeight = 24.sp
            )
            Row() {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = item.time,
                    fontSize = 14.sp,
                    fontFamily = regularWorkSans,
                    lineHeight = 21.sp
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = " · ",
                    fontSize = 14.sp,
                    fontFamily = regularWorkSans,
                    lineHeight = 21.sp
                )

                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = item.duration,
                    fontSize = 14.sp,
                    fontFamily = regularWorkSans,
                    lineHeight = 21.sp
                )
            }

        }
        Box(
            Modifier
                .size(28.dp)
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp)
        ) {
            Box(
                Modifier
                    .size(12.dp)
                    .background(
                        when (item.textStatus) {
                            TextStatus.READY -> {
                                Color.Green
                            }

                            TextStatus.ERROR -> {
                                Color.Red

                            }

                            TextStatus.IN_PROCESS -> {
                                Color.Yellow

                            }

                            else -> {
                                Color.Red

                            }
                        }, CircleShape
                    )
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun DateInJournalList(date: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp, end = 16.dp)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = date,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = baldFontWorkSans
        )
    }
}

data class JournalItem(
    val id: Int,
    val name: String?,
    val text: String?,
    val time: String,
    val duration: String,
    val textStatus: TextStatus
)

enum class TextStatus(
    val status: String
) {
    READY("ready"),
    IN_PROCESS("in_process"),
    ERROR("error")

}