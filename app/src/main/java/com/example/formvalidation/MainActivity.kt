package com.example.formvalidation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.formvalidation.uii.CountryDetailScreen
import com.example.formvalidation.uii.CountryListScreen
import com.example.formvalidation.viewmodel.UserViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val viewModel: UserViewModel = viewModel()

                NavHost(navController = navController, startDestination = Screen.Splash.route) {
                    composable(Screen.Splash.route) {
                        SplashScreen(navController)
                    }
//                    composable(Screen.Register.route) {
//                        RegisterScreen(navController, viewModel)
//                    }
                    composable(Screen.Register.route) { backStackEntry ->
                        val viewmodel: UserViewModel = viewModel(backStackEntry)
                        RegisterScreen(
                            navController,
                            viewmodel,
                            onRegistrationSuccess = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Screen.Login.route) {
                        LoginScreen(navController, viewModel)
                    }
                    composable(Screen.ForgotPassword.route) {
                        ForgotPasswordScreen(navController)
                    }

                    composable(Screen.Home.route) {
                        HomeScreen(navController)
                    }
//                    composable(Screen.Permission.route) {
//                        PermissionScreen(navController)
//                    }
                    composable(Screen.HomeTab.route) {
                        HomeTabScreen(navController)
                    }
                    composable(Screen.Profile.route) {
                        FullProfileScreen(navController = navController)
                    }
                    composable("countryList") {
                        CountryListScreen(
                            onCountryClick = { country ->
                                navController.navigate("detail/${country.name}/${country.flags.png}")
                            }
                        )
                    }

                    composable(
                        route = "detail/{name}/{flagUrl}/{capital}/{region}/{population}",
                        arguments = listOf(
                            navArgument("name") { type = NavType.StringType },
                            navArgument("flagUrl") { type = NavType.StringType },
                            navArgument("capital") { type = NavType.StringType },
                            navArgument("region") { type = NavType.StringType },
                            navArgument("population") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val name = Uri.decode(backStackEntry.arguments?.getString("name") ?: "")
                        val flagUrl =
                            Uri.decode(backStackEntry.arguments?.getString("flagUrl") ?: "")
                        val capital =
                            Uri.decode(backStackEntry.arguments?.getString("capital") ?: "N/A")
                        val region =
                            Uri.decode(backStackEntry.arguments?.getString("region") ?: "N/A")
                        val population =
                            Uri.decode(backStackEntry.arguments?.getString("population") ?: "N/A")

                        CountryDetailScreen(
                            navController = navController,
                            name = name,
                            flagUrl = flagUrl,
                            capital = capital,
                            region = region,
                            population = population
                        )
                    }

                    composable("countryList") {
                        CountryListScreen(
                            onCountryClick = { country ->
                                navController.navigate("detail/${country.name}/${country.flags.png}")
                            }
                        )
                    }

                    composable(
                        route = "detail/{name}/{flagUrl}",
                        arguments = listOf(
                            navArgument("name") { type = NavType.StringType },
                            navArgument("flagUrl") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val flagUrl = backStackEntry.arguments?.getString("flagUrl") ?: ""
                        CountryDetailScreen(name, flagUrl)
                    }
                }
            }
        }
    }
}
