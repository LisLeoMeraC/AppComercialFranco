package com.example.francoapp.datos.servicio

import com.example.francoapp.entidades.Login
import com.example.francoapp.entidades.RespuestaLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiciosAPI {
    @POST("/generate-token")
    suspend fun autenticar(@Body solicitud: Login): Response<RespuestaLogin>
}