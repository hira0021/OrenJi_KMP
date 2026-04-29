package com.example.orenji.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.orenji.navigation.Screen
import com.example.orenji.navigation.bottomNavItems
import com.example.orenji.ui.family.FamilyScreen
import com.example.orenji.ui.feed.FeedScreen
import com.example.orenji.ui.map.FamilyMapScreen
import com.example.orenji.ui.profile.ProfileScreen

@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MainContent(
        onLogout = onLogout,
        navController = navController,
        currentDestination = currentDestination
    )
}

@Composable
fun MainContent(
    onLogout: () -> Unit,
    navController: NavHostController = rememberNavController(),
    currentDestination: NavDestination? = null
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == item.screen.route
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                            )
                        },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Map.route) { FamilyMapScreen() }
            composable(Screen.Feed.route) { FeedScreen() }
            composable(Screen.Family.route) { FamilyScreen() }
            composable(Screen.Profile.route) { ProfileScreen(
                onLogout = onLogout
            ) }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MainContent(onLogout = {})
}