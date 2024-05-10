package com.aditprayogo.attendancecompose.ui.screens.dashboard.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aditprayogo.attendancecompose.ui.navigation.Destination

object HomeDestination : Destination {
    override val routeName: String = "home"
}

fun NavGraphBuilder.homeScreen() {
    composable(route = HomeDestination.routeName) {
        HomeScreen()
    }
}