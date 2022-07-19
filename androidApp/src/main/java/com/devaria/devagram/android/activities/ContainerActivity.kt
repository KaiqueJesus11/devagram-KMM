package com.devaria.devagram.android.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.devaria.devagram.android.R
import com.devaria.devagram.android.fragments.FeedFragment
import com.devaria.devagram.android.fragments.NovaPublicacaoFragment
import com.devaria.devagram.android.fragments.PerfilFragment

class ContainerActivity : AppCompatActivity() {
    var rotaAtual = R.string.rota_home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        var botaoHome : ImageView = findViewById(R.id.home)
        var botaoPublicao : ImageView = findViewById(R.id.add_publicacao)
        var botaoPerfil : ImageView = findViewById(R.id.perfil)

        rotaAtual = R.string.rota_home
        botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home))
        botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
        botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
        setFragment(FeedFragment())

        botaoHome.setOnClickListener {
            rotaAtual = R.string.rota_home
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
            setFragment(FeedFragment())
        }

        botaoPublicao.setOnClickListener {
            rotaAtual = R.string.rota_nova_publicacao
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home_inativa))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicacao))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user_inativo))
            setFragment(NovaPublicacaoFragment())
        }

        botaoPerfil.setOnClickListener {
            rotaAtual = R.string.rota_perfil
            botaoHome.setImageDrawable(getResources().getDrawable(R.drawable.home_inativa))
            botaoPublicao.setImageDrawable(getResources().getDrawable(R.drawable.nova_publicao_inativa))
            botaoPerfil.setImageDrawable(getResources().getDrawable(R.drawable.user))
            setFragment(PerfilFragment())
        }
    }


    fun setFragment(fragment: Fragment){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_container, fragment)
        ft.commit()
    }

}