package com.example.francoapp.dominio

import android.util.Log
import com.example.francoapp.datos.LoginRepository
import com.example.francoapp.entidades.Login
import com.example.francoapp.entidades.RespuestaLogin

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(username: String, password: String): RespuestaLogin? {
        val solicitud = Login(username, password)

        // Agrega el log aquí antes del retorno
        Log.d("AutenticacionCasoUso", "Intentando autenticar: $username")

        return repository.autenticar(solicitud)
    }
}