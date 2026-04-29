package com.example.orenji

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.orenji.data.repository.AuthRepository
import com.example.orenji.ui.auth.LoginScreen
import com.example.orenji.ui.common.LoadingIndicator
import com.example.orenji.ui.main.MainScreen
import com.example.orenji.ui.theme.OrenJiTheme
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    OrenJiTheme {
        val authRepository = koinInject<AuthRepository>()
        var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

        LaunchedEffect(Unit) {
            isLoggedIn = authRepository.isLoggedIn()
        }

        when (isLoggedIn) {
            null -> LoadingIndicator()
            true -> MainScreen(
                onLogout = { isLoggedIn = false }
            )
            false -> LoginScreen(
                onLoginSuccess = { isLoggedIn = true }
            )
        }
    }
}