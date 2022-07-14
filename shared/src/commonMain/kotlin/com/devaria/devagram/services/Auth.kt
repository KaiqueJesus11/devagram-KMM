package com.devaria.devagram.services

import com.devaria.devagram.model.Cadastrar
import com.devaria.devagram.model.Login.Login
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Auth {
    suspend fun login(body: Login): HttpResponse{
        return DevagramApiService().post("login", body)
    }

    suspend fun cadastro(body: Cadastrar): HttpResponse {
        val form = formData{
            parametersOf("nome", body.nome)
            parametersOf("email", body.email)
            parametersOf("senha", body.senha)
        }

        return DevagramApiService().post("cadastrar",  body, "", true, body.file)
    }
}