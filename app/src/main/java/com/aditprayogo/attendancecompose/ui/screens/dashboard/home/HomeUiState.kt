package com.aditprayogo.attendancecompose.ui.screens.dashboard.home

import com.aditprayogo.core.entity.Office
import com.aditprayogo.core.utils.ResultState

sealed interface HomeUiState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isError: Boolean

    data class CheckInUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val listOfOfficeResource: ResultState<List<Office>> = ResultState.Loading,
        val selectedOffice: Office? = null
    ) : HomeUiState

    data class CheckOutUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val selectedOffice: Office
    ) : HomeUiState

}