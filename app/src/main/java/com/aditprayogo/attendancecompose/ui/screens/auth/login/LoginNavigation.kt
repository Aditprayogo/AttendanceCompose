package com.aditprayogo.attendancecompose.ui.screens.auth.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.aditprayogo.attendancecompose.ui.navigation.Destination

object LoginDestination : Destination {
    override val routeName: String = "login"
}

fun NavController.navigateToLogin(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(LoginDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.loginScreen(
    onNavigateToRegisterScreen: () -> Unit,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
) {
    composable(
        route = LoginDestination.routeName,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        LoginScreen(
            onNavigateToRegisterScreen = onNavigateToRegisterScreen
        )
    }
}