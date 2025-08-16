package com.example.formvalidation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Register : Screen("register")
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object HomeTab : Screen("home_tab")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Permission : Screen("permission")
}