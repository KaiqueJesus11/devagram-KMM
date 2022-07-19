package com.devaria.devagram.android.activities

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.devaria.devagram.Validacoes
import com.devaria.devagram.android.R
import com.devaria.devagram.model.Cadastrar.Cadastrar
import com.devaria.devagram.model.Login.Login
import com.devaria.devagram.model.Login.ResponseErro
import com.devaria.devagram.model.Login.ResponseSucesso
import com.devaria.devagram.services.Auth
import io.ktor.client.call.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class CadastroActivity : AppCompatActivity() {
    var nome : String = ""
    var email : String = ""
    var senha : String = ""
    var avatar: ByteArray? = null
    var confirmacaoSenha : String = ""
    lateinit var botaoCadastrar : Button
    lateinit var tirarFoto : ImageView
    val REQUEST_CODE = 200
    private val mainScope = MainScope()

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

        botaoCadastrar.setOnClickListener {
            mainScope.launch {
                kotlin.runCatching {
                    Auth().cadastrar(Cadastrar(nome, email, senha, avatar))
                }.onSuccess {
                    if(it.status.value >= 400){
                        val erroData : com.devaria.devagram.model.Cadastrar.ResponseErro = it.body()
                        Log.e("Erro", "Erro: ${erroData.erro}")
                        showDialog("Erro", "Erro: ${erroData.erro}")
                    }else{
                        val authData : com.devaria.devagram.model.Cadastrar.ResponseSucesso = it.body()
                        showDialog("Sucesso!", "${authData.msg}")
                        aoCadastrar()
                    }
                }.onFailure {
                    Log.e("Erro", "Erro: ${it.localizedMessage}")
                    showDialog("Erro", "Erro: ${it.localizedMessage}")
                }
            }
            Log.i("Botao cadastrar", "Clicado")
        }
    }

    fun aoCadastrar(){
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

    fun onLogin(authData: ResponseSucesso){
        getSharedPreferences("devagram", Context.MODE_PRIVATE).edit().putString("token", authData.token).apply()
        val intent: Intent = Intent(this, ContainerActivity::class.java)
        startActivity(intent)
    }

    fun showDialog(title: String, message: String){
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun capturarFoto (){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            var foto = editarImage(data.extras?.get("data") as Bitmap, 120, 120)
            val stream = ByteArrayOutputStream()
            foto!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            avatar = stream.toByteArray()
            tirarFoto.setImageBitmap(foto)
        }
    }

    fun editarImage(imagem: Bitmap, newHeigth: Int, newWidth: Int): Bitmap? {
        val width = imagem.width
        val heigth = imagem.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeigth = newHeigth.toFloat() / heigth

        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeigth)

        return Bitmap.createBitmap(imagem, 0, 0, width, heigth, matrix, false)

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