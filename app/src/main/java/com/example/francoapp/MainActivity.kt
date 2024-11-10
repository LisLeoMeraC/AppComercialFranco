package com.example.francoapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.francoapp.datos.LoginRepository
import com.example.francoapp.datos.repositorio.LoginRepositoryImpl
import com.example.francoapp.datos.servicio.Helper
import com.example.francoapp.dominio.LoginUseCase
import com.example.francoapp.dominio.SesionManager
import com.example.francoapp.ui.navegacion.MainNavigation
import com.example.francoapp.ui.pantallas.LoginDesign
import com.example.francoapp.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Inyección de SesionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Usar hiltViewModel para obtener la instancia de LoginViewModel gestionada por Hilt
            val viewModel: LoginViewModel = hiltViewModel()
            val sessionManager = SesionManager(this)

            // Asegúrate de que se esté proporcionando el SavedStateRegistryOwner
            MainNavigation(viewModel = viewModel, sessionManager = sessionManager)
        }
    }
}