package com.devaria.devagram.services

import com.devaria.devagram.model.Cadastrar.Cadastrar
import com.devaria.devagram.model.Login.Login
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Auth {
    suspend fun login(body: Login): HttpResponse{
        return DevagramApiService().post("login", body)
    }

    suspend fun cadastrar(body: Cadastrar): HttpResponse {
        val form = formData{
            append("nome", body.nome)
            append("email", body.email)
            append("senha", body.senha)
            append("file", body.file!!, Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"avatar.png\"")
            })
        }

        return DevagramApiService().post("cadastro",  form, "")
    }
}