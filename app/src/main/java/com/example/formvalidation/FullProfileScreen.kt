package com.example.formvalidation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProfileScreen(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }
    val emailState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val linkedInState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    var profileImageResId by remember { mutableStateOf<Int?>(R.drawable.profile) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text("Your Profile", color = Color.White)
                },
                actions = {
                    IconButton(onClick = { /* Settings click */ }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8E24AA)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (profileImageResId != null) {
                    Image(
                        painter = painterResource(id = profileImageResId!!),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Default Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        tint = Color.Gray
                    )
                }

                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .border(1.dp, Color.Gray, shape = CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Change Picture",
                        tint = Color(0xFF8E24AA)
                    )
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Update Picture") },
                    onClick = {
                        showMenu = false
                        profileImageResId = R.drawable.profile
                    }
                )
                DropdownMenuItem(
                    text = { Text("Change Picture") },
                    onClick = {
                        showMenu = false
                        // TODO: Add change logic
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete Picture") },
                    onClick = {
                        showMenu = false
                        profileImageResId = null
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Devika",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8E24AA)
            )

            Text(
                text = "Android Developer",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileField("Your Email", emailState, Icons.Default.Email)
            ProfileField("Phone Number", phoneState, Icons.Default.Call)
            ProfileField("LinkedIn", linkedInState, Icons.Default.Public)
            ProfileField("Password", passwordState, Icons.Default.Lock)
        }
    }
}
@Composable
fun ProfileField(label: String, textState: MutableState<String>, icon: ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            leadingIcon = { Icon(icon, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}