package com.example.orenji.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    // Auth
    object Login : Screen("login")

    // Main tabs
    object Map : Screen("map")
    object Feed : Screen("feed")
    object Family : Screen("link")
    object Profile : Screen("profile")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Map, "Map", Icons.Filled.Map),
    BottomNavItem(Screen.Feed, "Feed", Icons.Filled.Home),
    BottomNavItem(Screen.Family, "Link", Icons.Filled.FamilyRestroom),
    BottomNavItem(Screen.Profile, "Profile", Icons.Filled.Person),
)