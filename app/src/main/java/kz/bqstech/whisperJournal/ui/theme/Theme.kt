package kz.bqstech.whisperJournal.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4A90E2),          // Same soft blue
    onPrimary = Color.Black,

    secondary = Color(0xFFCE93D8),        // Softer purple for dark mode
    onSecondary = Color.Black,

    background = Color.Black,       // Deep black background
    onBackground = Color.White,

    surface = Color(48, 48, 48),
    onSurface = Color(171, 171, 171),

    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A90E2),          // Soft blue
    onPrimary = Color.White,

    secondary = Color(0xFF9C27B0),        // Accent purple
    onSecondary = Color.White,

    background = Color.White,       // Near-white background
    onBackground = Color.Black,

    surface = Color(242, 242, 242),
    onSurface = Color(117, 117, 117),

    error = Color(0xFFB00020),
    onError = Color.White


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun WhisperJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    Log.d("dsgkjhjkjlfdgk", "${colorScheme.onBackground}     ${Color.Black}")

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}