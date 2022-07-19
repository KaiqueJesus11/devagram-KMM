package com.devaria.devagram.services

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
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

    suspend private fun api (endpoint: String, metodo: HttpMethod, formData: List<PartData>, token: String = "", newHeaders: Headers? = null, pamerameters : Parameters? = null): HttpResponse {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = base_url + endpoint,
            formData
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

    suspend fun post(endpoint: String, body: List<PartData>, token: String = "", newHeaders: Headers? = null): HttpResponse {
            val response = api(endpoint, HttpMethod.Post ,body, token, newHeaders)
            return response
    }

    suspend fun post(endpoint: String, body: Any, token: String = "", newHeaders: Headers? = null): HttpResponse {
        val response = api(endpoint, HttpMethod.Post ,body, token, newHeaders)
        return response
    }


    suspend fun get(endpoint: String, token: String = "", pamerameters: Parameters? = null, newHeaders: Headers? = null): HttpResponse {
        val response = api(endpoint, HttpMethod.Get, null, token, newHeaders, pamerameters)
        return response
    }

    suspend fun put(endpoint: String, body: List<PartData>, token: String = "", newHeaders: Headers? = null, pamerameters: Parameters? = null): HttpResponse {
            val response = api(endpoint, HttpMethod.Put, body, token, newHeaders, pamerameters)
            return response
    }

    suspend fun put(endpoint: String, body: Any, token: String = "", newHeaders: Headers? = null, pamerameters: Parameters? = null): HttpResponse {
        val response = api(endpoint, HttpMethod.Put, body, token, newHeaders, pamerameters)
        return response
    }



}