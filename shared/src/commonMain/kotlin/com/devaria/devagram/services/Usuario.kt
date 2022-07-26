package com.devaria.devagram.services

import io.ktor.client.statement.*

class Usuario {
    suspend fun getUsuario(token: String): HttpResponse {
        return DevagramApiService().get("usuario", token)
    }
}