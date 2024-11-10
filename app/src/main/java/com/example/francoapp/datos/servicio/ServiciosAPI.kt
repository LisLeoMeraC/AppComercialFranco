package com.example.francoapp.datos.servicio


import com.example.francoapp.entidades.Cliente
import com.example.francoapp.entidades.Login
import com.example.francoapp.entidades.Page
import com.example.francoapp.entidades.RespuestaLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiciosAPI {
    @POST("/generate-token")
    suspend fun autenticar(@Body solicitud: Login): Response<RespuestaLogin>

    //Para listar los clientes
    @GET("/clientes")
    suspend fun listarClientes(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<Page<Cliente>>
}