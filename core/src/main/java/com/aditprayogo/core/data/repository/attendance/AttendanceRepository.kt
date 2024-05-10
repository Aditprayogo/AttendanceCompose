package com.aditprayogo.core.data.repository.attendance

import com.aditprayogo.core.data.paging.AttendanceRecordsPagingSource
import com.aditprayogo.core.data.state.AttendanceState
import com.aditprayogo.core.data.state.TodayAttendanceState
import com.aditprayogo.core.entity.Office
import com.aditprayogo.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    fun checkTodayAttendanceState(): Flow<ResultState<TodayAttendanceState>>

    fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<ResultState<String>>

    fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource
}