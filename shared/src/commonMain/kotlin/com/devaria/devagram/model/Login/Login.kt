package com.devaria.devagram.model.Login

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    var login: String,
    var senha: String
){}