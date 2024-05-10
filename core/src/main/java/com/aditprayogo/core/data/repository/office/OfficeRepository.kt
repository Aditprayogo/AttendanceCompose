package com.aditprayogo.core.data.repository.office

import com.aditprayogo.core.entity.Office
import com.aditprayogo.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    fun getOffices(): Flow<ResultState<List<Office>>>

}