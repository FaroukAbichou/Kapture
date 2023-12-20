package org.zephyr.app.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import core.navigation.wrappers.HomeScreenWrapper

@Composable
fun MainNavGraph() {
    Navigator(HomeScreenWrapper()) { navigator ->
        FadeTransition(navigator)
    }
}