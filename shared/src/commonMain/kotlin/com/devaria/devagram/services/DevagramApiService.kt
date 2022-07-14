package com.devaria.devagram.services

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class DevagramApiService {
    private val client = HttpClient(){
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    private val base_url = "https://devagram-node-nextjs-kohl.vercel.app/api/"

    suspend private fun api (endpoint: String, metodo: HttpMethod, body: Any? = null, token: String = "", newHeaders: Headers? = null, pamerameters : Parameters? = null): HttpResponse {
        val response = client.request(base_url + endpoint){
            method = metodo
            contentType(ContentType.Application.Json)
            setBody(body)
            pamerameters
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                apply { newHeaders }
            }
        }

        return response
    }

    suspend private fun api (endpoint: String, metodo: HttpMethod, formData: Any? = null, token: String = "", newHeaders: Headers? = null, isFormData: Boolean = true, imagem: ByteArray? = null, pamerameters : Parameters? = null): HttpResponse {
        if(isFormData == false){
            return api(endpoint, metodo, formData, token, newHeaders, pamerameters)
        }

        val response: HttpResponse = client.submitFormWithBinaryData(
            url = base_url + endpoint,
            formData = formData {
                apply { formData }
                append("image", imagem!!, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=\"avatar.png\"")
                })
            }
        ) {
            method = metodo
            pamerameters
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                apply { newHeaders }
            }
        }


        return response
    }

    suspend fun post(endpoint: String, body: Any, token: String = "", isFormData: Boolean? = false, imagem: ByteArray? = null, newHeaders: Headers? = null): HttpResponse {
        if(isFormData == true){
            val response = api(endpoint, HttpMethod.Post ,body, token, newHeaders, true, imagem)
            return response
        }

        val response = api(endpoint, HttpMethod.Post ,body, token, newHeaders)
        return response
    }

    suspend fun get(endpoint: String, token: String = "", pamerameters: Parameters? = null, newHeaders: Headers? = null): HttpResponse {
        val response = api(endpoint, HttpMethod.Get, null, token, newHeaders, pamerameters)
        return response
    }

    suspend fun put(endpoint: String, body: Any, token: String = "", isFormData: Boolean? = false, imagem: ByteArray? = null, newHeaders: Headers? = null, pamerameters: Parameters? = null): HttpResponse {
        if(isFormData == true){
            val response = api(endpoint, HttpMethod.Put, body, token, newHeaders, true, imagem, pamerameters)
            return response
        }

        val response = api(endpoint, HttpMethod.Put, body, token, newHeaders, pamerameters)
        return response
    }



}