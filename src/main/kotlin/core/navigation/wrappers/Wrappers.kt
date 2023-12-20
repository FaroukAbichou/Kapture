package core.navigation.wrappers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import record.home.presentation.HomeScreen
import record.home.presentation.HomeViewModel
import record.home.presentation.event.HomeEvent
import record.settings.presentation.SettingsScreen
import record.settings.presentation.SettingsViewModel
import record.settings.presentation.event.SettingsEvent


internal class HomeScreenWrapper : Screen {

    private val viewModel : HomeViewModel = HomeViewModel()
    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        HomeScreen(
            state = state,
            onEvent = {
                when(it){
                    is HomeEvent.NavigateToSettings -> {
                        navigator.push(SettingsScreenWrapper(it.arg))
                    }

                    else -> viewModel.onEvent(it)
                }
            }
        )
    }
}

internal class SettingsScreenWrapper(val arg :String) : Screen {

    @Composable
    override fun Content() {
        val viewModel = SettingsViewModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        SettingsScreen(
            state = state,
            onEvent = {
                when(it){
                    is SettingsEvent.NavigateToHome -> {
                        navigator.pop()
                    }
                    else -> viewModel.onEvent(it)
                }
            }
        )
    }
}