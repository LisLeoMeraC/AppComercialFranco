package com.example.francoapp.datos.repositorio

import com.example.francoapp.datos.ClienteRepository
import com.example.francoapp.datos.servicio.ServiciosAPI
import com.example.francoapp.entidades.Cliente
import com.example.francoapp.entidades.Page
import javax.inject.Inject

class ClienteRepositoryImpl @Inject constructor(private val api:ServiciosAPI):ClienteRepository {
    override suspend fun obtenerClientes(page: Int, size: Int): Page<Cliente>? {
        val response = api.listarClientes(page, size)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Error al obtener clientes: Respuesta vacía.")
        } else {
            throw Exception("Error al obtener clientes: ${response.message()}")
        }
    }
}