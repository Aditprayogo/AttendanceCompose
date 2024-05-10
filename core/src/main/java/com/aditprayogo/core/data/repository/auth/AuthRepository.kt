package com.aditprayogo.core.data.repository.auth

import com.aditprayogo.core.data.state.AuthState
import com.aditprayogo.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun currentAuthState(): Flow<AuthState>

    fun register(
        email: String,
        password: String,
        fullName: String
    ): Flow<ResultState<String>>

    fun login(
        email: String,
        password: String
    ): Flow<ResultState<String>>

    fun signOut()

}