package com.aditprayogo.attendancecompose.ui.screens.dashboard.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aditprayogo.attendancecompose.ui.navigation.Destination

object HistoryDestination : Destination {
    override val routeName: String = "history"
}

fun NavGraphBuilder.historyScreen() {
    composable(route = HistoryDestination.routeName) {
        HistoryScreen()
    }
}