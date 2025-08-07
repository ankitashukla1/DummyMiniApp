package com.example.formvalidation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.formvalidation.model.User
import com.example.formvalidation.utility.isValidEmail
import com.example.formvalidation.utility.isValidPassword
import com.example.formvalidation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, viewModel: UserViewModel,
                   onRegistrationSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val state by viewModel.registerState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "SIGN UP!",
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                        )
                        Text(
                            text = "To get started!",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        },
        content = { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .align(Alignment.Center)
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Name") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            when {
                                name.isBlank() || email.isBlank() || password.isBlank() -> {
                                    errorMessage = "Please fill all fields"
                                }
                                !isValidEmail(email) -> {
                                    errorMessage = "Oops! Please enter a valid email"
                                }
                                !isValidPassword(password) -> {
                                    errorMessage = "Oops! Your password doesn't meet the security standards"
                                }
                                else -> {
                                    isLoading = true
                                    errorMessage = ""
                                    val user = User(name = name, email = email, password = password)
                                    viewModel.registerUser(user)
                                }
                            }
                        },
                        enabled = state !is UserViewModel.RegisterUiState.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                    ) {
                        Text("Sign Up", color = Color.White)
                    }

                    when (state) {
                        is UserViewModel.RegisterUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                        is UserViewModel.RegisterUiState.Success -> {
                            LaunchedEffect(Unit) {
                                onRegistrationSuccess()
                            }
                            Text(
                                (state as UserViewModel.RegisterUiState.Success).message,
                                color = Color.Green,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                        is UserViewModel.RegisterUiState.Error -> Text(
                            (state as UserViewModel.RegisterUiState.Error).error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        else -> {}
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val annotatedText = buildAnnotatedString {
                        append("Already have an account? ")
                        pushStringAnnotation(tag = "Sign in here", annotation = "login")
                        withStyle(style = SpanStyle(color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)) {
                            append("Sign in here")
                        }
                        pop()
                    }
                    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

                    Text(
                        text = annotatedText,
                        modifier = Modifier
                            .padding(16.dp)
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    textLayoutResult?.let { layoutResult ->
                                        val position = layoutResult.getOffsetForPosition(offset)
                                        annotatedText.getStringAnnotations(
                                            tag = "Sign in here",
                                            start = position,
                                            end = position
                                        ).firstOrNull()?.let {
                                            navController.navigate(Screen.Login.route)
                                        }
                                    }
                                }
                            },
                        onTextLayout = { textLayoutResult = it },
                        style = MaterialTheme.typography.bodyMedium
                    )

//                    ClickableText(
//                        text = annotatedText,
//                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
//                        onClick = { offset ->
//                            annotatedText.getStringAnnotations("LOGIN", offset, offset)
//                                .firstOrNull()?.let {
//                                    navController.navigate(Screen.Login.route)
//                                }
//                        }
//                    )
                }
            }
        }
    )
}