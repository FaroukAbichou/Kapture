
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import core.navigation.KpNavigationBar
import core.navigation.wrappers.HomeScreenWrapper
import core.theme.KaptureTheme

@Composable
fun App() = KaptureTheme {

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        KpNavigationBar(
            modifier = Modifier,
            onHomeClick = {},
            onVideoClick = {},
            onImageClick = {},
            onAudioClick = {},
            onSettingsClick = {},
        )
        Navigator(HomeScreenWrapper()) { navigator ->
            FadeTransition(navigator)
        }
    }
}