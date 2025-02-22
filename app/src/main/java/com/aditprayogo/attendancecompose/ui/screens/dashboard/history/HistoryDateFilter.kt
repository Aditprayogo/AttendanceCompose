package com.aditprayogo.attendancecompose.ui.screens.dashboard.history

import com.aditprayogo.attendancecompose.R
import com.aditprayogo.core.utils.CalendarUtils

sealed class HistoryDateFilter {
    abstract val nameStrResource: Int
    abstract val startDate: String
    abstract val endDate: String


    data class Day(
        override val nameStrResource: Int = R.string.day,
        override val startDate: String = CalendarUtils.currentDate,
        override val endDate: String = CalendarUtils.currentDate
    ) : HistoryDateFilter()

    data class Week(
        override val nameStrResource: Int = R.string.week,
        override val startDate: String = CalendarUtils.currentDate,
        override val endDate: String = CalendarUtils.beginningWeekDate
    ) : HistoryDateFilter()

    data class Month(
        override val nameStrResource: Int = R.string.month,
        override val startDate: String = CalendarUtils.currentDate,
        override val endDate: String = CalendarUtils.beginningMonthDate
    ) : HistoryDateFilter()

    data class Year(
        override val nameStrResource: Int = R.string.year,
        override val startDate: String = CalendarUtils.currentDate,
        override val endDate: String = CalendarUtils.beginningYearDate
    ) : HistoryDateFilter()
}