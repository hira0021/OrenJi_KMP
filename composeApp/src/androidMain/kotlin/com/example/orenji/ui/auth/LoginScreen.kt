package com.example.orenji.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orenji.presentation.auth.AuthState
import com.example.orenji.presentation.auth.AuthViewModel
import com.example.orenji.ui.common.OrenJiPrimaryButton
import com.example.orenji.ui.common.OrenJiPasswordField
import com.example.orenji.ui.common.OrenJiTextField
import com.example.orenji.ui.theme.OrenJiTheme
import com.example.orenji.ui.theme.TextSecondary
import orenji.composeapp.generated.resources.Res
import orenji.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
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
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "OrenJi Logo",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1.5f),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary,
            fontSize = 30.sp,
        )

        Spacer(Modifier.height(10.dp))

        OrenJiTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
            isError = state.error != null,
            enabled = !state.isLoading,
        )

        Spacer(Modifier.height(16.dp))

        OrenJiPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(Modifier.height(14.dp))

        OrenJiPrimaryButton(
            text = "Login",
            onClick = onLoginClick,
            isLoading = state.isLoading,
        )
    }
}

@Preview(showBackground = true, name = "Login - Default")
@Composable
fun LoginContentPreview() {
    OrenJiTheme {
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
    OrenJiTheme {
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
    OrenJiTheme {
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
    OrenJiTheme {
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