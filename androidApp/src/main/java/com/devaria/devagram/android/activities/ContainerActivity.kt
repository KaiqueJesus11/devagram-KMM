package com.devaria.devagram.android.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.devaria.devagram.android.R
import com.devaria.devagram.android.fragments.FeedFragment
import com.devaria.devagram.android.fragments.HeaderFragment
import com.devaria.devagram.android.fragments.NovaPublicacaoFragment
import com.devaria.devagram.android.fragments.PerfilFragment
import com.devaria.devagram.android.utils.Dialog
import com.devaria.devagram.model.Cadastrar.ResponseErro
import com.devaria.devagram.services.Usuario
import io.ktor.client.call.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ContainerActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        val shared = getSharedPreferences("devagram", Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        var botaoHome : ImageView = findViewById(R.id.home)
        var botaoPublicao : ImageView = findViewById(R.id.add_publicacao)
        var botaoPerfil : ImageView = findViewById(R.id.perfil)

        botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home))
        botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
        botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
        setFragment(FeedFragment(), resources.getString(R.string.rota_home))

        botaoHome.setOnClickListener {
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
            setFragment(FeedFragment(), resources.getString(R.string.rota_home))
        }

        botaoPublicao.setOnClickListener {
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home_inativa))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicacao))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
            setFragment(NovaPublicacaoFragment(), resources.getString(R.string.rota_nova_publicacao))
        }

        botaoPerfil.setOnClickListener {
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home_inativa))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user))
            setFragment(PerfilFragment(), resources.getString(R.string.rota_perfil))
            val idUsuarioLogado = shared.getString("id_usuario_logado", "")
            val nomeUsuarioLogado = shared.getString("nome_usuario_logado", "")
            shared.edit().putString("id_perfil", idUsuarioLogado).apply()
            shared.edit().putString("nome_perfil", nomeUsuarioLogado).apply()
        }

        saveUsuario()
    }


    fun setFragment(fragment: Fragment, rota: String){
        val shared = getSharedPreferences("devagram", Context.MODE_PRIVATE)

        val rotaAnterior = shared.getString("rota_atual", "")
        shared.edit().putString("rota_anterior", rotaAnterior).apply()
        shared.edit().putString("rota_atual", rota).apply()

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_container, fragment)
        ft.replace(R.id.header_container, HeaderFragment.newInstance())
        ft.commit()
    }

    fun saveUsuario(){
        val shared = getSharedPreferences("devagram", Context.MODE_PRIVATE)
        mainScope.launch {
            kotlin.runCatching {
                val token = shared?.getString("token", "")
                Usuario().getUsuario(token!!)
            }.onSuccess {
                if(it.status.value >= 400){
                    val erroData : ResponseErro = it.body()
                    Dialog(this@ContainerActivity).show("Erro", "Erro: ${erroData.erro}")
                }else{
                    val usuario : com.devaria.devagram.model.Usuario.Usuario = it.body()
                    shared.edit().putString("id_usuario_logado", usuario!!._id).apply()
                    shared.edit().putString("nome_usuario_logado", usuario!!.nome).apply()
                }
            }.onFailure {
                Dialog(this@ContainerActivity).show("Erro", "Erro: ${it.localizedMessage}")
            }
        }
    }

}