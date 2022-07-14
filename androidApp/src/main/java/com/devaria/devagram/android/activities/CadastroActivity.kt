package com.devaria.devagram.android.activities

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.devaria.devagram.Validacoes
import com.devaria.devagram.android.R

class CadastroActivity : AppCompatActivity() {
    var nome : String = ""
    var email : String = ""
    var senha : String = ""
    lateinit var avatar: ByteArray
    var confirmacaoSenha : String = ""
    lateinit var botaoCadastrar : Button
    lateinit var tirarFoto : ImageView
    val REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        botaoCadastrar = findViewById(R.id.cadastrar)
        val linkFazerLogin : TextView = findViewById(R.id.fazer_login)
        val inputNome : EditText = findViewById(R.id.nome)
        val inputEmail : EditText = findViewById(R.id.email)
        val inputSenha : EditText = findViewById(R.id.senha)
        val inputConfirmacaoSenha : EditText = findViewById(R.id.confirmar_senha)
        tirarFoto = findViewById(R.id.tirar_foto)

        requestPermissions()

        inputNome.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nome = p0.toString()
                validarForm()
            }
        })

        inputEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = p0.toString()
                validarForm()
            }
        })

        inputSenha.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                senha = p0.toString()
                validarForm()
            }
        })

        inputConfirmacaoSenha.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                confirmacaoSenha = p0.toString()
                validarForm()
            }
        })

        linkFazerLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        tirarFoto.setOnClickListener {
            capturarFoto()
        }
    }

    fun capturarFoto (){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            var foto = data.extras?.get("data") as Bitmap
            avatar = data.extras?.get("data") as ByteArray
            tirarFoto.setImageBitmap(foto)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if(permission != PackageManager.PERMISSION_GRANTED ){
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        }
    }

    fun validarForm(){
        val formValido = Validacoes().validarNome(nome) && Validacoes().validarEmail(email)
                && Validacoes().validarSenha(senha) && Validacoes().validarConfirmarSenha(senha, confirmacaoSenha)
        botaoCadastrar.setEnabled(formValido)
    }
}