package com.devaria.devagram.android.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.devaria.devagram.android.R
import com.devaria.devagram.model.Login.Login
import com.devaria.devagram.model.Login.ResponseErro
import com.devaria.devagram.model.Login.ResponseSucesso
import com.devaria.devagram.services.Auth
import io.ktor.client.call.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    var email : String = ""
    var senha : String = ""

    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputEmail : EditText  = findViewById(R.id.email)
        val inputSenha : EditText  = findViewById(R.id.senha)
        val botaoLogin : Button = findViewById(R.id.login)
        val linkFazerCadastro : TextView = findViewById(R.id.fazer_cadastro)

        inputEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = p0.toString()
            }
        })

        inputSenha.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                senha = p0.toString()
            }
        })

        botaoLogin.setOnClickListener {
            mainScope.launch {
                kotlin.runCatching {
                   Auth().login(Login(email, senha))
                }.onSuccess {
                   if(it.status.value >= 400){
                       val erroData : ResponseErro = it.body()
                       Log.e("Erro", "Erro: ${erroData.erro}")
                       showDialog("Erro", "Erro: ${erroData.erro}")
                   }else{
                       val authData : ResponseSucesso = it.body()
                       onLogin(authData)
                   }
                }.onFailure {
                    Log.e("Erro", "Erro: ${it.localizedMessage}")
                    showDialog("Erro", "Erro: ${it.localizedMessage}")
                }
            }
        }

        linkFazerCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    fun showDialog(title: String, message: String){
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun onLogin(authData: ResponseSucesso){
        getSharedPreferences("devagram", Context.MODE_PRIVATE).edit().putString("token", authData.token).apply()
        val intent: Intent = Intent(this, ContainerActivity::class.java)
        startActivity(intent)
    }

}