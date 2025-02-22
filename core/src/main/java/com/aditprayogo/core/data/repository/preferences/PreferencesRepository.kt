package com.aditprayogo.core.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun isAlreadyOnBoarded(): Flow<Boolean>

    fun setOnBoarded()
}