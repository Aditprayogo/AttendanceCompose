package com.aditprayogo.attendancecompose.ui.screens.auth

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.aditprayogo.attendancecompose.ui.navigation.Destination
import com.aditprayogo.attendancecompose.ui.navigation.popUpToInclusive
import com.aditprayogo.attendancecompose.ui.screens.auth.login.LoginDestination
import com.aditprayogo.attendancecompose.ui.screens.auth.login.loginScreen
import com.aditprayogo.attendancecompose.ui.screens.auth.login.navigateToLogin
import com.aditprayogo.attendancecompose.ui.screens.auth.register.RegisterDestination
import com.aditprayogo.attendancecompose.ui.screens.auth.register.navigateToRegister
import com.aditprayogo.attendancecompose.ui.screens.auth.register.registerScreen
import com.aditprayogo.attendancecompose.ui.screens.dashboard.navigateToDashboard

object GlobalAuthDestination : Destination {
    override val routeName: String = "global_auth"
}

private object GlobalAuthNavigationAnimation {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        val slideUp = slideInVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialOffsetY = { 300 }
        )

        val fadeOut = fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            initialAlpha = 0.8f
        )

        slideUp + fadeOut
    }
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        val slideDown = slideOutVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetOffsetY = { 300 }
        )

        val fadeOut = fadeOut(
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseInOut
            ),
            targetAlpha = 0.8f
        )

        slideDown + fadeOut
    }
}

fun NavController.navigateToGlobalAuth(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(GlobalAuthDestination.routeName, navOptions(builder))
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        startDestination = LoginDestination.routeName,
        route = GlobalAuthDestination.routeName
    ) {
        registerScreen(
            onNavigateToLoginScreen = {
                navController.navigateToLogin {
                    popUpToInclusive(RegisterDestination)
                }
            },
            onNavigateToDashboardScreen = {
                navController.navigateToDashboard {
                    popUpToInclusive(navController.graph.id)
                }
            },
            enterTransition = GlobalAuthNavigationAnimation.enterTransition,
            exitTransition = GlobalAuthNavigationAnimation.exitTransition
        )
        loginScreen(
            onNavigateToRegisterScreen = {
                navController.navigateToRegister {
                    popUpToInclusive(LoginDestination)
                }
            },
            enterTransition = GlobalAuthNavigationAnimation.enterTransition,
            exitTransition = GlobalAuthNavigationAnimation.exitTransition
        )
    }
}