package com.example.formvalidation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.formvalidation.model.Register
import com.example.formvalidation.utility.isValidEmail
import com.example.formvalidation.utility.isValidPassword
import com.example.formvalidation.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel,
    onRegistrationSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
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
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center)
                    .background(Color.White, shape = RoundedCornerShape(24.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Create your new account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                                errorMessage = ""
                                val user = Register(name = name, email = email, password = password)
                                viewModel.registerUser(user)
                            }
                        }
                    },
                    enabled = state !is RegisterViewModel.RegisterUiState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("Sign Up", color = Color.White)
                }

                when (state) {
                    is RegisterViewModel.RegisterUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                    }
                    is RegisterViewModel.RegisterUiState.Success -> {
                        LaunchedEffect(Unit) { onRegistrationSuccess() }
                        Text(
                            (state as RegisterViewModel.RegisterUiState.Success).message,
                            color = Color.Green,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    is RegisterViewModel.RegisterUiState.Error -> {
                        Text(
                            (state as RegisterViewModel.RegisterUiState.Error).error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
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
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF6A1B9A),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
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
            }
        }
    }
}
