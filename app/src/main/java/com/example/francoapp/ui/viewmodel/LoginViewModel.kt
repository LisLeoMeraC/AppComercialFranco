package com.example.francoapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.francoapp.dominio.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    var token: String? = null
        private set

    // Asegúrate de que este sea LiveData
    val mensajeError: MutableLiveData<String?> = MutableLiveData(null)

    fun autenticar(username: String, password: String, callback: (String?, String?) -> Unit) {
        viewModelScope.launch {
            // Simular llamada al use case
            val response = loginUseCase(username, password)
            if (response != null) {
                val token = response.token
                this@LoginViewModel.token = token
                // Guardar el token en SharedPreferences
              //  sharedPreferences.edit().putString("auth_token", token).apply()
                callback(token, null) // Devuelve el token y sin mensaje de error
                Log.d("Hola", "Éxito en el inicio de sesión")
            } else {
                callback(null, "Credenciales Incorrectas") // Devuelve null y el mensaje de error
                Log.d("Hola", "Hay error de autenticación")
            }

        }

    }
}