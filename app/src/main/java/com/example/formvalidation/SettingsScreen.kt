package com.example.formvalidation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SettingsItem(
                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account") },
                title = "Account",
                onClick = { /* Handle account click */ }
            )

            SettingsItem(
                icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
                title = "Notifications",
                onClick = { /* Handle notifications click */ }
            )

            SettingsItem(
                icon = { Icon(Icons.Default.Logout, contentDescription = "Logout") },
                title = "Logout",
                onClick = { /* Handle logout click */ }
            )

            SettingsItem(
                icon = { Icon(Icons.Default.Delete, contentDescription = "Delete Account") },
                title = "Delete account",
                onClick = { /* Handle delete account click */ }
            )

            SettingsItem(
                icon = { Icon(Icons.Default.Storage, contentDescription = "Storage Permission") },
                title = "Request Storage Permission",
                onClick = { navController.navigate(Screen.Permission.route) }
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: @Composable () -> Unit,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Divider()
}

