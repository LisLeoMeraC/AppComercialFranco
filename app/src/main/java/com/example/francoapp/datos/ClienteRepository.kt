package com.example.francoapp.datos

import com.example.francoapp.entidades.Cliente
import com.example.francoapp.entidades.Page

interface ClienteRepository {
    suspend fun obtenerClientes(page: Int, size: Int): Page<Cliente>?
}