package com.example.orenji.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orenji.domain.model.User
import com.example.orenji.presentation.profile.ProfileState
import com.example.orenji.presentation.profile.ProfileViewModel
import com.example.orenji.ui.common.OrenJiButton
import com.example.orenji.ui.theme.OrenJiTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) onLogout()
    }

    ProfileContent(
        state = state,
        onLogout = { viewModel.logout() },
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = state.user?.fullName ?: "User",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = state.user?.email ?: "",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.height(40.dp))

        OrenJiButton(
            text = "Logout",
            onClick = onLogout,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileContentPreview() {
    OrenJiTheme {
        ProfileContent(
            state = ProfileState(
                user = User(
                    id = 1,
                    fullName = "John Cena",
                    email = "johncena@gmail.com",
                    avatarUrl = null,
                    bio = null,
                )
            ),
            onLogout = {},
        )
    }
}