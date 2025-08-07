package com.example.formvalidation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.HomeTab.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.HomeTab.route) {
                HomeTabScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                FullProfileScreen(navController = navController)
            }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}

