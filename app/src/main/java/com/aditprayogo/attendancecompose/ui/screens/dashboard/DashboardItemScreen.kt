package com.aditprayogo.attendancecompose.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aditprayogo.attendancecompose.R
import com.aditprayogo.attendancecompose.ui.screens.dashboard.history.HistoryDestination
import com.aditprayogo.attendancecompose.ui.screens.dashboard.home.HomeDestination
import com.aditprayogo.attendancecompose.ui.screens.dashboard.profile.ProfileDestination

val dashboardItems = listOf(
    DashboardItemScreen.HomeScreen,
    DashboardItemScreen.HistoryScreen,
    DashboardItemScreen.ProfileScreen
)

sealed class DashboardItemScreen(
    val routeName: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    object HomeScreen : DashboardItemScreen(
        routeName = HomeDestination.routeName,
        title = R.string.home,
        icon = R.drawable.ic_home
    )

    object ProfileScreen : DashboardItemScreen(
        routeName = ProfileDestination.routeName,
        title = R.string.profile,
        icon = R.drawable.ic_profile_nav
    )

    object HistoryScreen : DashboardItemScreen(
        routeName = HistoryDestination.routeName,
        title = R.string.history,
        icon = R.drawable.ic_history
    )
}