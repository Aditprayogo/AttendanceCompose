package com.aditprayogo.attendancecompose.ui.screens.dashboard.profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val username: String = "",
    val jobTitle: String = "",
    val employeeNumber: String = "",
    val address: String = "",
    val email: String = ""
)
