package com.devaria.devagram

import android.text.TextUtils

class Validacoes {
    fun validarNome (nome: String): Boolean{
        return nome.length > 3
    }

    fun validarEmail (email: String): Boolean{
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    fun validarSenha (senha: String): Boolean{
        return senha.length > 4
    }

    fun validarConfirmarSenha (senha: String, confirmarSenha: String): Boolean{
        return senha.equals(confirmarSenha)
    }
}