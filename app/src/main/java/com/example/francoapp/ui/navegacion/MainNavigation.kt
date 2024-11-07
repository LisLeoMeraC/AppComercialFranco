package com.example.francoapp.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.francoapp.ui.pantallas.Dashboard
import com.example.francoapp.ui.pantallas.LoginDesign
import com.example.francoapp.ui.viewmodel.LoginViewModel

@Composable
fun MainNavigation(viewModel: LoginViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginDesign(viewModel = viewModel, navController = navController)
        }
        composable("dashboard") {
            Dashboard()
        }
    }
}