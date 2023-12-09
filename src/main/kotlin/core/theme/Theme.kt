package core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Gray1,
    onPrimary = Color.Black,
    onPrimaryContainer = Color.White,
    secondary = Gray2,
    onSecondary = Gray3,
    onSecondaryContainer = Gray4,
    background = Gray1,
)

private val LightColorScheme = lightColorScheme(
    primary = Gray1,
    onPrimary = Color.Black,
    onPrimaryContainer = Color.White,
    secondary = Gray2,
    onSecondary = Gray3,
    onSecondaryContainer = Gray4,
    background = Gray1,
)

@Composable
fun KaptureTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
