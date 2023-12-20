import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import core.navigation.wrappers.HomeScreenWrapper
import core.theme.KaptureTheme

@Composable
fun App() = KaptureTheme {
    Navigator(HomeScreenWrapper()) { navigator ->
        FadeTransition(navigator)
    }
}