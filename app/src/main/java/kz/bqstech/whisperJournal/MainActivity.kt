package kz.bqstech.whisperJournal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kz.bqstech.whisperJournal.ui.theme.Fonts.baldFontWorkSans
import kz.bqstech.whisperJournal.ui.theme.WhisperJournalTheme
import kz.bqstech.whisperJournal.util.Space
import kz.bqstech.whisperJournal.util.noRippleClick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val fragmentManager =
        enableEdgeToEdge()
        setContent {
            WhisperJournalTheme {
                Log.d("dsgkjhjkjlfdgk", "${MaterialTheme.colorScheme.onBackground}     ${Color.Black}")

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.RECORD_AUDIO),
                    1001
                )//move this to part also where i start recording



                val navController = rememberNavController()
                Scaffold(
                    topBar ={
                        MainTopbar()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        MainNavGraph()
                    }
                }
            }
        }
    }
}

@Composable
fun MainBottomBar(
    navController: NavHostController,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToJournal: () -> Unit,
    currentScreen: String?
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Space(16.dp)
        val currentScreen = when (currentScreen) {
            HomeScreen::class.java.name -> "HomeScreen"
            JournalScreen::class.java.name -> "JournalScreen"
            SettingsScreen::class.java.name -> "SettingsScreen"

            else -> null
        }
        Log.d(
            "aadasdasdasdasdded",
            "${HomeScreen::class.java.name}     ${navController.currentDestination?.route}      ${currentScreen.toString()}"
        )

        BottomBarItem(
            painterResource(R.drawable.home_bottom),
            "Home",
            onCLick = navigateToHome,
            selected = currentScreen == "HomeScreen"
        )
        BottomBarItem(
            painterResource(R.drawable.journal_bottom),
            "Journal",
            onCLick = navigateToJournal,
            selected = currentScreen == "JournalScreen"
        )
        BottomBarItem(
            painterResource(R.drawable.settings_bottom),
            "Settings",
            onCLick = navigateToSettings,
            selected = currentScreen == "SettingsScreen"
        )
        Space(16.dp)

    }
}

@Composable
fun RowScope.BottomBarItem(painter: Painter, text: String, onCLick: () -> Unit, selected: Boolean) {
    val color = if (selected) {
        Color.Black
    } else {
        Color.Black.copy(0.2f)
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .weight(1f)
        .noRippleClick {
            onCLick.invoke()
        }) {
        Column() {
            Icon(
                tint = color,
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Space(4.dp)
            Text(
                color = color,
                text = text,
                lineHeight = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun MainTopbar() {
    Box(Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp)) {
        Text(color =Color.Black,text="Whisper Journal",modifier =Modifier.align(Alignment.Center),fontSize =18.sp, lineHeight = 23.sp, fontFamily = baldFontWorkSans)
    }
}
