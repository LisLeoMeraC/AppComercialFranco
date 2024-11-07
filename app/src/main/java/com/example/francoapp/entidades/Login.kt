package com.example.francoapp.entidades

data class Login(
    val username: String,
    val password: String


)


data class RespuestaLogin(
    val token: String
)