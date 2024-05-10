package com.aditprayogo.attendancecompose.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditprayogo.core.data.repository.auth.AuthRepository
import com.aditprayogo.core.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginResource: MutableStateFlow<ResultState<String>> =
        MutableStateFlow(ResultState.Init)
    val loginResourceState = _loginResource.asStateFlow()

    private val _message: MutableSharedFlow<String> = MutableSharedFlow()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun onEmailChanged(newEmailValue: String) {
        _loginUiState.update { it.copy(email = newEmailValue) }
    }

    fun onPasswordChanged(newPasswordValue: String) {
        _loginUiState.update { it.copy(password = newPasswordValue) }
    }

    fun login() {
        loginUiState.value.apply {
            viewModelScope.launch {
                authRepository.login(email, password).collect {
                    if (it is ResultState.Error) {
                        _message.emit(it.message)
                    }
                    _loginResource.value = it
                }
            }
        }
    }

}