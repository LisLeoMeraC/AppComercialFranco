package com.example.francoapp.datos

import com.example.francoapp.entidades.Login
import com.example.francoapp.entidades.RespuestaLogin

interface LoginRepository {
    suspend fun autenticar(solicitud: Login): RespuestaLogin?
}