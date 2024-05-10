package com.aditprayogo.attendancecompose.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aditprayogo.attendancecompose.R
import com.aditprayogo.attendancecompose.ui.composeable.template.AuthTemplate
import com.aditprayogo.attendancecompose.ui.composeable.widget.OutlinedTextFieldOutsideLabel
import com.aditprayogo.attendancecompose.ui.composeable.widget.PasswordOutlinedTextFieldOutsideLabel
import com.aditprayogo.attendancecompose.ui.composeable.widget.SpanClickableText
import com.aditprayogo.attendancecompose.ui.theme.AttendanceComposeTheme
import com.aditprayogo.attendancecompose.ui.theme.ButtonBgYellow
import com.aditprayogo.attendancecompose.ui.theme.ButtonTextDarkBlueGrayish
import com.aditprayogo.core.utils.ResultState

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToDashboardScreen: () -> Unit
) {
    val registerUiState by viewModel.registerUiState.collectAsStateWithLifecycle()
    val registerResourceState by viewModel.registerResourceState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(registerResourceState) {
        if (registerResourceState is ResultState.Error) {
            Toast.makeText(
                context,
                (registerResourceState as ResultState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }

        if (registerResourceState is ResultState.Success) {
            onNavigateToDashboardScreen()
        }
    }

    RegisterScreen(
        modifier = modifier,
        onNavigateToLoginScreen = onNavigateToLoginScreen,
        registerResource = registerResourceState,
        onButtonRegisterClicked = {
            viewModel.register()
        },
        email = registerUiState.email,
        isEmailError = registerUiState.isEmailError,
        onTextFieldEmailValueChange = viewModel::onEmailValueChange,
        fullName = registerUiState.fullName,
        onTextFieldFullNameValueChange = viewModel::onFullNameValueChange,
        password = registerUiState.password,
        isPasswordError = registerUiState.isPasswordError,
        onTextFieldPasswordValueChange = viewModel::onPasswordValueChange,
        repeatPassword = registerUiState.repeatPassword ?: "",
        onTextFieldRepeatPasswordValueChange = viewModel::onRepeatPasswordValueChange,
        isRepeatPasswordError = registerUiState.isRepeatPasswordError,
        buttonRegisterEnabled = registerUiState.registerEnabled,
        isFullNameValid = registerUiState.isFullNameValid
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerResource: ResultState<String>,
    onNavigateToLoginScreen: () -> Unit,
    onButtonRegisterClicked: () -> Unit,
    email: String,
    isEmailError: Boolean,
    onTextFieldEmailValueChange: (String) -> Unit,
    fullName: String,
    onTextFieldFullNameValueChange: (String) -> Unit,
    password: String,
    isPasswordError: Boolean,
    onTextFieldPasswordValueChange: (String) -> Unit,
    repeatPassword: String,
    isRepeatPasswordError: Boolean,
    onTextFieldRepeatPasswordValueChange: (String) -> Unit,
    buttonRegisterEnabled: Boolean,
    isFullNameValid: Boolean
) {

    AuthTemplate(
        modifier = modifier,
        screenTitle = stringResource(R.string.register_screen_title),
        screenDescription = stringResource(R.string.register_screen_description),
        content = {
            Spacer(Modifier.height(24.dp))
            OutlinedTextFieldOutsideLabel(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email),
                textFieldValue = email,
                onTextFieldValueChange = onTextFieldEmailValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isEmailError
            )
            Spacer(Modifier.height(22.dp))
            OutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.full_name),
                textFieldValue = fullName,
                onTextFieldValueChange = onTextFieldFullNameValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isFullNameValid
            )
            Spacer(Modifier.height(22.dp))
            PasswordOutlinedTextFieldOutsideLabel(
                label = stringResource(R.string.password),
                textFieldValue = password,
                onTextFieldValueChange = onTextFieldPasswordValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isPasswordError
            )
            Spacer(Modifier.height(22.dp))
            PasswordOutlinedTextFieldOutsideLabel(
                modifier = Modifier.imePadding(),
                label = stringResource(R.string.repeat_password),
                textFieldValue = repeatPassword,
                isError = isRepeatPasswordError,
                onTextFieldValueChange = onTextFieldRepeatPasswordValueChange
            )
            Spacer(Modifier.height(22.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (registerResource != ResultState.Loading) {
                        onButtonRegisterClicked()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBgYellow,
                    contentColor = ButtonTextDarkBlueGrayish
                ),
                enabled = buttonRegisterEnabled,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
            ) {
                if (registerResource is ResultState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp)
                    )
                } else {
                    Text(text = stringResource(R.string.register))
                }
            }
            Spacer(Modifier.height(12.dp))
            SpanClickableText(
                modifier = Modifier.fillMaxWidth(),
                onSpanTextClick = { onNavigateToLoginScreen() },
                regularText = "Already have an account? Please",
                clickableText = "Login",
                fontSize = 12.sp
            )
            Spacer(Modifier.height(21.dp))
        }
    )
}

@Preview
@Composable
fun RegisterScreenPreview() {
    AttendanceComposeTheme {
        RegisterScreen(
            onNavigateToLoginScreen = {},
            registerResource = ResultState.Init,
            onButtonRegisterClicked = {},
            email = "",
            onTextFieldEmailValueChange = {},
            fullName = "",
            onTextFieldFullNameValueChange = {},
            password = "",
            onTextFieldPasswordValueChange = {},
            repeatPassword = "",
            onTextFieldRepeatPasswordValueChange = {},
            isRepeatPasswordError = false,
            buttonRegisterEnabled = true,
            isPasswordError = false,
            isEmailError = false,
            isFullNameValid = false
        )
    }
}