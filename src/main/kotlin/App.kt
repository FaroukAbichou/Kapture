
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.FadeTransition
import core.navigation.KpNavigationBar
import core.navigation.wrappers.HomeScreenWrapper
import core.navigation.wrappers.HomeTab
import core.navigation.wrappers.SettingsTab
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

        TabNavigator(HomeTab) { navigator ->
            Column {

                BottomNavigation {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(SettingsTab)
                }
                CurrentTab()
            }
        }
        Navigator(HomeScreenWrapper()) { navigator ->
            FadeTransition(navigator)
        }
    }
}

@Composable
private fun ColumnScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        }
    )
}