package com.example.francoapp.dominio

import com.example.francoapp.datos.ClienteRepository
import com.example.francoapp.datos.repositorio.ClienteRepositoryImpl
import com.example.francoapp.entidades.Cliente
import com.example.francoapp.entidades.Page
import javax.inject.Inject

class ClienteUseCase @Inject constructor(private val clienteRepository: ClienteRepository) {
    suspend fun obtenerClientes(page:Int,size:Int):Page<Cliente>?{
        return clienteRepository.obtenerClientes(page,size)
    }
}