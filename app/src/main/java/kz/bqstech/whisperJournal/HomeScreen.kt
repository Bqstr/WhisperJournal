package kz.bqstech.whisperJournal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.xr.compose.testing.toDp
import kz.bqstech.whisperJournal.util.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToJournal: () -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val context = LocalContext.current
    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        MainBottomBar(
            navHostController,
            navigateToHome = navigateToHome,
            navigateToJournal = navigateToJournal,
            navigateToSettings = navigateToSettings
        )
    }) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            var circleSize by remember { mutableStateOf(0.dp) }
            Box(
                Modifier
                    .fillMaxWidth()
                    .size(circleSize)
                    .padding(horizontal = 120.dp) // 100dp total padding
                    .onGloballyPositioned { layoutCoordinates ->
                        val width = layoutCoordinates.size.width.toDp()
                        circleSize = width
                    }
                    .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                    .align(Alignment.Center)
                    .clickable {
                        viewModel.startRecording(onRecorderStarting = {
                            showToast(
                                context,
                                context.getString(R.string.recording_started)
                            )//make it custom(with img) or smth
                        })
                    }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.background,
                    painter = painterResource(id = R.drawable.mic_image),
                    contentDescription = "record image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .padding(40.dp)
                )
            }
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopCenter)
//            )
//            {
//                Box(
//                    Modifier
//                        .weight(1f)
//                        .background(Color.Red)
//                        .clickable {
//                            viewModel.stopRecording()
//                        }
//                ) {
//                    Text(
//                        fontSize = 25.sp, text = "Stop recording", modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(vertical = 10.dp)
//                    )
//                }
//                Box(
//                    Modifier
//                        .weight(1f)
//                        .background(Color.Green)
//                        .clickable {
//                            viewModel.playAudioTest()
//                        }
//                ) {
//                    Text(
//                        fontSize = 25.sp, text = "Start Play", modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(vertical = 10.dp)
//                    )
//                }
//                Box(
//                    Modifier
//                        .weight(1f)
//                        .background(Color.Yellow)
//                        .clickable {
//                            viewModel.stopAudioTest()
//                        }
//                ) {
//                    Text(
//                        fontSize = 25.sp, text = "Stop Play", modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(vertical = 10.dp)
//                    )
//                }
//            }

        }
    }


}