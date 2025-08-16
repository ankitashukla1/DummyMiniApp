 package com.example.formvalidation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.HomeTab,
        Screen.Profile,
        Screen.Settings
    )

    NavigationBar {
        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        is Screen.HomeTab -> Icon(Icons.Default.Home, contentDescription = null)
                        is Screen.Profile -> Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null
                        )

                        is Screen.Settings -> Icon(
                            Icons.Default.Settings,
                            contentDescription = null
                        )

                        else -> Icon(Icons.Default.Home, contentDescription = null)
                    }

                },
                label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
