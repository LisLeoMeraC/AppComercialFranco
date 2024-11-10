package com.example.francoapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.francoapp.dominio.ClienteUseCase
import com.example.francoapp.entidades.Cliente
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class ClienteViewModel @Inject constructor(private val clienteUseCase: ClienteUseCase, private val savedStateHandle: SavedStateHandle):ViewModel() {
    var currentPage: Int = savedStateHandle.get<Int>("currentPage") ?: 0
    var lastPageReached: Boolean = savedStateHandle.get<Boolean>("lastPageReached") ?: false

    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun saveState() {
        savedStateHandle.set("currentPage", currentPage)
        savedStateHandle.set("lastPageReached", lastPageReached)
    }

    init {
        cargarClientes()
    }

    fun cargarClientes() {
        if (lastPageReached || _isLoading.value) return

        _isLoading.value = true

        viewModelScope.launch {
            try {
                
                val pagina = clienteUseCase.obtenerClientes(currentPage, 10)

                pagina?.let {
                    val nuevosClientes = it.content.filter { nuevoCliente ->
                        !_clientes.value.any { clienteExistente -> clienteExistente.id == nuevoCliente.id }
                    }

                    _clientes.update { oldList -> oldList + nuevosClientes }
                    lastPageReached = it.last
                    if (!lastPageReached) currentPage++

                    saveState()
                } ?: run {
                    _errorMessage.update { "No se encontraron datos." }
                }
            } catch (e: Exception) {
                Log.e("ClienteViewModel", "Error al cargar clientes", e)
                _errorMessage.update { "Error al cargar clientes: ${e.message}" }
            } finally {
                _isLoading.value = false
            }
        }
    }
}