package com.example.francoapp.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.francoapp.dominio.SesionManager
import com.example.francoapp.ui.pantallas.ClientesViewDesign
import com.example.francoapp.ui.pantallas.Dashboard
import com.example.francoapp.ui.pantallas.LoginDesign
import com.example.francoapp.ui.viewmodel.ClienteViewModel
import com.example.francoapp.ui.viewmodel.LoginViewModel
import com.example.francoapp.ui.viewmodel.TestViewModel

@Composable
fun MainNavigation(viewModel: LoginViewModel, sessionManager: SesionManager) {
    val navController = rememberNavController()

    val token = sessionManager.getToken()
    val startDestination = if (token != null) "dashboard" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        // Pantalla de login
        composable("login") {
            LoginDesign(viewModel = viewModel, navController = navController, sesionManager = sessionManager)
        }

        // Pantalla del dashboard
        composable("dashboard") {
            Dashboard(navController = navController)
        }

        // Pantalla de clientes
        composable("clientes") {
            val clienteViewModel: ClienteViewModel = hiltViewModel()  // HiltViewModel aquí
            ClientesViewDesign(clienteViewModel = clienteViewModel)
        }
    }
}