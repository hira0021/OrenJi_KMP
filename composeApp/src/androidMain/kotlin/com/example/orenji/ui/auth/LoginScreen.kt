package com.example.orenji.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orenji.presentation.auth.AuthState
import com.example.orenji.presentation.auth.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onLoginSuccess()
    }

    LoginContent(
        email = email,
        password = password,
        state = state,
        onEmailChange = {
            email = it
            viewModel.clearError()
        },
        onPasswordChange = {
            password = it
            viewModel.clearError()
        },
        onLoginClick = { viewModel.login(email, password) },
    )
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    state: AuthState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "OrenJi",
            style = MaterialTheme.typography.displaySmall,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Sign in to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.error != null,
            enabled = !state.isLoading,
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = state.error != null,
            enabled = !state.isLoading,
        )

        Spacer(Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth().heightIn(min = 20.dp)) {
            if (state.error != null) {
                Text(
                    text = state.error ?: "error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !state.isLoading,
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                Text("Login")
            }
        }
    }
}

@Preview(showBackground = true, name = "Login - Default")
@Composable
fun LoginContentPreview() {
    MaterialTheme {
        LoginContent(
            email = "",
            password = "",
            state = AuthState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Filled")
@Composable
fun LoginContentFilledPreview() {
    MaterialTheme {
        LoginContent(
            email = "johncena@gmail.com",
            password = "qwe123",
            state = AuthState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Loading")
@Composable
fun LoginContentLoadingPreview() {
    MaterialTheme {
        LoginContent(
            email = "johncena@gmail.com",
            password = "qwe123",
            state = AuthState(isLoading = true),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Login - Error")
@Composable
fun LoginContentErrorPreview() {
    MaterialTheme {
        LoginContent(
            email = "johncena@gmail.com",
            password = "wrongpass",
            state = AuthState(error = "Invalid email or password"),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
        )
    }
}