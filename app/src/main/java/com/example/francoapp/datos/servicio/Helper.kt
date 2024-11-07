package com.example.francoapp.datos.servicio

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Helper {
    val api: ServiciosAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.10.32:8080") // Reemplaza con tu URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiciosAPI::class.java)
    }
}