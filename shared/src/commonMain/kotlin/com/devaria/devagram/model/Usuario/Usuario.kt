package com.devaria.devagram.model.Usuario

import kotlinx.serialization.Serializable

@Serializable
class Usuario(
    var _id: String? = "",
    var nome: String,
    var avatar: String? = ""
) {}