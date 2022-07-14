package com.devaria.devagram.model

data class Cadastrar(
    var nome: String,
    var email: String,
    var senha:String,
    var file: ByteArray
) {}