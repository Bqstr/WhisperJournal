package kz.bqstech.whisperJournal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.xr.compose.testing.toDp
import kotlinx.serialization.json.Json.Default.configuration
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.google.android.material.shape.TriangleEdgeTreatment
import kz.bqstech.whisperJournal.util.Space
import kz.bqstech.whisperJournal.util.noRippleClick

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToJournal: () -> Unit
) {
    val context = LocalContext.current

    val isPaused =remember{mutableStateOf(false)}
    val isRecording = remember { mutableStateOf(false) }

    val receiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                Log.d("intentAction", intent?.action.toString())
                if (intent?.action == RECORDING_STATE_CHANGED) {
                    Log.d("assigning", "assign")
                    isRecording.value = intent.getBooleanExtra(RECORDING_EXTRA, false)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val musicDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val mediaFiles = musicDir?.listFiles()?.filter { it.extension == "3gp" } ?: emptyList()
        Log.d("asdfasdffrfrfrf",mediaFiles.toString())
    }






    DisposableEffect(Unit) {
        val filter = IntentFilter(RECORDING_STATE_CHANGED)
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    val pxToMove = with(LocalDensity.current) {
        174.dp.toPx().roundToInt() //TODO:calculate this too
    }
    val offset by animateIntOffsetAsState(
        targetValue = if (isRecording.value) {
            IntOffset(0, pxToMove)
        } else {
            IntOffset.Zero
        },
        label = "offset",
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
    )

    val rotation by animateFloatAsState(
        targetValue = if (isRecording.value) {
            180f
        } else {
            0f
        },
        label = "rotation",
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),

    )





    var isHolding by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val viewModel: HomeViewModel = koinViewModel()
        Box(
            Modifier
                .fillMaxSize()
        )
        {


            Box(
                Modifier
                    .offset{offset}
                    .size((screenWidthDp/5).dp)
                    .noRippleClick{
                        isPaused.value= !isPaused.value
                    }
                    .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                    .align(Alignment.Center)
                    .rotate(rotation)
            ){


                if(isPaused.value) {
                    Box(Modifier.fillMaxSize()) {
                        Row(Modifier.align(Alignment.Center)) {
                            Space()
                            Box(
                                Modifier.fillMaxHeight(0.45f).fillMaxWidth(0.1f).background(
                                    MaterialTheme.colorScheme.background,
                                    RoundedCornerShape(10.dp)
                                )
                            ) {}
                            Space(0.4f)
                            Box(
                                Modifier.fillMaxHeight(0.45f).fillMaxWidth(0.1f).background(
                                    MaterialTheme.colorScheme.background,
                                    RoundedCornerShape(10.dp)
                                )
                            ) {}
                            Space()


                        }
                    }
                }
                else{
                    val backgroundColor = MaterialTheme.colorScheme.background
                    Box(Modifier.drawWithCache {
                        val roundedPolygon = RoundedPolygon(
                            numVertices = 3,
                            radius = size.minDimension / 2,
                            centerX = size.width / 2,
                            centerY = size.height / 2,
                            rounding = CornerRounding(
                                size.minDimension / 10f,
                                smoothing = 0.1f
                            )
                        )
                        val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                        onDrawBehind {
                            drawPath(roundedPolygonPath, color = backgroundColor)
                        }
                    })
                }
            }
            Box(
                Modifier
                    .size((screenWidthDp/2.5).dp)
                    .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                    .align(Alignment.Center)
                    .noRippleClick() {
                        val intent = Intent(context, AudioRecordingService::class.java)
                        if(isRecording.value){
                            context.stopService(intent)
                        }else{
                            ContextCompat.startForegroundService(context, intent)
                        }
                        isRecording.value =!isRecording.value
                    }
            )
            {


                    Box(Modifier.align(Alignment.Center)) {
                        AnimatedContent(
                            contentAlignment = Alignment.Center,
                            targetState = isRecording.value,
                            transitionSpec = {
                                fadeIn(tween(300)) with fadeOut(tween(300))
                            },
                            label = "iconTransition",
                        )
                        { recording ->

                            Log.d("isRecording", isRecording.value.toString())
                            if (recording) {
                                Box(
                                    Modifier.fillMaxSize(0.5f).align(Alignment.Center).background(
                                        MaterialTheme.colorScheme.background,
                                        RoundedCornerShape(10.dp)
                                    )
                                ) {}
                            } else {

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
                        }
                    }

            }
        }



}

