package com.example.francoapp.dominio

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SesionManager @Inject constructor( @ApplicationContext private val context:Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val KEY_TOKEN = "key_token"
    }

    fun saveToken(token: String) {
        editor.putString(KEY_TOKEN, token).apply()
    }

    // Obtener el token
    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    // Eliminar el token (cuando el usuario cierre sesión)
    fun clearToken() {
        editor.remove(KEY_TOKEN).apply()
    }
}