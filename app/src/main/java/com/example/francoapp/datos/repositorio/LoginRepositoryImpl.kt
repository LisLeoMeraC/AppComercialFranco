package com.example.francoapp.datos.repositorio

import android.util.Log
import com.example.francoapp.datos.LoginRepository
import com.example.francoapp.datos.servicio.ServiciosAPI
import com.example.francoapp.entidades.Login
import com.example.francoapp.entidades.RespuestaLogin

class LoginRepositoryImpl (private val api:ServiciosAPI):LoginRepository{
    override suspend fun autenticar(solicitud: Login): RespuestaLogin? {
        return try {
            // Hacer la llamada a la API y almacenar la respuesta
            val respuesta = api.autenticar(solicitud)

            // Comprobar si la respuesta es exitosa
            if (respuesta.isSuccessful) {
                // Retornar el cuerpo de la respuesta
                respuesta.body() // Aquí se obtiene el objeto RespuestaLogin
            } else {
                // Manejar el error de respuesta
                Log.e("LoginRepositoryImpl", "Error de respuesta: ${respuesta.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            // Manejar cualquier excepción que ocurra durante la llamada
            Log.e("LoginRepositoryImpl", "Excepción: ${e.message}")
            null
        }
    }
}