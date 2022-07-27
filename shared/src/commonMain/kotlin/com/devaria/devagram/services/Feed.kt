package com.devaria.devagram.services

import com.devaria.devagram.dto.ComentarioDto
import io.ktor.client.statement.*

class Feed {
    suspend fun getFeed(token: String, id : String? = ""): HttpResponse {
        if(id != "" && id != null){
            return DevagramApiService().get("feed?id=$id", token)
        }
        return DevagramApiService().get("feed", token)
    }

    suspend fun toggleCurtir(publicacaoId: String, token: String): HttpResponse{
        return DevagramApiService().put("like?id=${publicacaoId}", null, token)
    }

    suspend fun addComentario(publicacaoId: String, comentario: ComentarioDto, token: String): HttpResponse{
        return DevagramApiService().put("comentario?id=${publicacaoId}", comentario, token)
    }

}