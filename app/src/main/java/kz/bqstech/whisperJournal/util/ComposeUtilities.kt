package kz.bqstech.whisperJournal.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.time.Duration


@Composable
fun ColumnScope.Space(weight :Float =1f) = Spacer(Modifier.weight(weight))

@Composable
fun RowScope.Space(weight :Float =1f) = Spacer(Modifier.weight(weight))

@Composable
fun ColumnScope.Space(height: Dp) = Spacer(Modifier.height(height))

@Composable
fun RowScope.Space(width: Dp) = Spacer(Modifier.width(width))

@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingDialog(onDismiss: () -> Unit = {}) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Modifier.noRippleClick(onClick:() ->Unit): Modifier {
    return clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick.invoke()
    }
}


fun showToast(context: Context,text:String,duration :Int =Toast.LENGTH_SHORT ){
    Toast.makeText(context, text , duration).show()
}
