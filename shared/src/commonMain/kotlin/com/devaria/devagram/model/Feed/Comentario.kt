package com.devaria.devagram.model.Feed

import kotlinx.serialization.Serializable


@Serializable
class Comentario(
    var usuarioId : String? = "",
    var nome: String,
    var comentario: String
) {}