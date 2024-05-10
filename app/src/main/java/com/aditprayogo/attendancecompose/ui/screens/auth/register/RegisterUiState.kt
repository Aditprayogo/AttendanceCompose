package com.aditprayogo.attendancecompose.ui.screens.auth.register

import android.util.Patterns

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String? = null,
) {

    val isFullNameValid: Boolean
        get() = if (fullName.isEmpty()) false else fullName.length !in 3..50

    val isEmailError: Boolean
        get() = if (email.isBlank()) false else !Patterns.EMAIL_ADDRESS.matcher(email).matches()

//    val isPasswordError: Boolean
//        get() = password.isBlank() || password.length < 8 ||
//                !password.any { it.isDigit() } ||
//                !password.any { it.isUpperCase() } ||
//                !password.any { it.isLowerCase() }

    val isPasswordError: Boolean
        get() = if (password.isBlank()) false else password.length < 8 || !password.any { it.isDigit() } ||
                !password.any { it.isUpperCase() } ||
                !password.any { it.isLowerCase() }

    val isRepeatPasswordError: Boolean
        get() = if (repeatPassword.isNullOrEmpty()) false else password != repeatPassword

    val registerEnabled: Boolean
        get() = email.isNotBlank()
                && fullName.isNotBlank()
                && password.isNotBlank()
                && repeatPassword?.isNotBlank() ?: false
                && !isRepeatPasswordError
}
