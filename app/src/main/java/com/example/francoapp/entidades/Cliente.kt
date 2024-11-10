package com.example.francoapp.entidades

data class Cliente(
    val id: Long,
    val nombres: String,
    val apellidos: String,
    val cedula: String,
    val telefono: String,
    val hectareas: Double,
    val certificado: Boolean,
    val recinto: Recinto

)

data class Recinto(
    val id: Long,
    val nombreRecinto: String
)

data class Page<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val first: Boolean,
    val size: Int,
    val number: Int,
    val numberOfElements: Int
)