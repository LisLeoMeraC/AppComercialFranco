package com.example.francoapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.francoapp.datos.LoginRepository
import com.example.francoapp.datos.repositorio.LoginRepositoryImpl
import com.example.francoapp.datos.servicio.Helper
import com.example.francoapp.dominio.LoginUseCase
import com.example.francoapp.ui.navegacion.MainNavigation
import com.example.francoapp.ui.pantallas.LoginDesign
import com.example.francoapp.ui.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // val sharedPreferences = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

        val loginRepositoryImpl = LoginRepositoryImpl(Helper.api)
        val loginUseCase = LoginUseCase(loginRepositoryImpl)
        val viewModel = LoginViewModel(loginUseCase)
       // val viewModel = LoginViewModel(loginUseCase, sharedPreferences)
        setContent {
            MainNavigation(viewModel = viewModel)
        }
    }
}