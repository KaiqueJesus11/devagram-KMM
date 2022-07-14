package com.devaria.devagram.model.Login

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSucesso(
    var nome: String,
    var email: String,
    var token: String
) {}