package com.aditprayogo.core.data.state

import com.aditprayogo.core.entity.Office

sealed class TodayAttendanceState {
    object NoRecord : TodayAttendanceState()

    data class CheckedIn(val office: Office) : TodayAttendanceState()

    object CheckedOut : TodayAttendanceState()
}