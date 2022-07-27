package com.devaria.devagram.services

import io.ktor.client.statement.*

class Usuario {
    suspend fun getUsuario(token: String): HttpResponse {
        return DevagramApiService().get("usuario", token)
    }

    suspend fun pesquisarUsuario(token: String, id: String): HttpResponse {
        return DevagramApiService().get("pesquisa?id=${id}", token)
    }

    suspend fun toggleSeguir(token: String, id: String): HttpResponse {
        return DevagramApiService().put("seguir?id=${id}", null, token)
    }
}