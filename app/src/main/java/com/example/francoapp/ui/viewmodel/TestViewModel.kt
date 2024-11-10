package com.example.francoapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    // Utilizamos SavedStateHandle para recuperar o guardar el estado
    private val _message = MutableStateFlow(savedStateHandle.get<String>("message") ?: "Hola Mundo")
    val message: StateFlow<String> = _message

    // Guardamos el estado cada vez que se actualiza el valor de _message
    fun setMessage(newMessage: String) {
        _message.value = newMessage
        savedStateHandle.set("message", newMessage)  // Guardamos el nuevo valor en SavedStateHandle
    }
}