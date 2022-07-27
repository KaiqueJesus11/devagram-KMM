package com.devaria.devagram.android.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.devaria.devagram.android.R
import com.devaria.devagram.android.activities.ContainerActivity
import com.devaria.devagram.android.activities.LoginActivity
import com.devaria.devagram.android.services.Rotas

// TODO: Rename parameter arguments, choose names that match

class HeaderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_header, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)

        definirHeaderAtivo(view)

        val botaoLogout = view.findViewById<ImageView>(R.id.logout)
        val botaoVoltar = view.findViewById<ImageView>(R.id.voltar)
        val botaoCancelar = view.findViewById<TextView>(R.id.cancelar)
        val botaoCancelarPublicacao = view.findViewById<TextView>(R.id.cancelar_publicacao)

        botaoLogout.setOnClickListener {
            shared!!.edit().remove("token").apply()
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
        }

        botaoVoltar.setOnClickListener{
            voltar()
        }

        botaoCancelarPublicacao.setOnClickListener{
            cancelar()
        }

    }

    fun voltar(){
        val shared = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)
        val idUsuarioLogado = shared!!.getString("id_usuario_logado", "")
        context?.let { Rotas(it).setRota(PerfilFragment.newInstance(idUsuarioLogado), resources.getString(R.string.rota_perfil)) }
    }

    fun cancelar(){
        val shared = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)
        val rotaAnterior = shared!!.getString("rota_anterior", "")
        if(rotaAnterior != context?.resources?.getString(R.string.rota_perfil)){
            context?.let { Rotas(it).setRota(PerfilFragment(), resources.getString(R.string.rota_home)) }
        }else{
            context?.let { Rotas(it).setRota(PerfilFragment(), resources.getString(R.string.rota_perfil)) }
        }
    }

    fun definirHeaderAtivo(view: View){
        val shared = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)
        val rotaAtual = shared!!.getString("rota_atual", "")
        val nome_perfil = shared!!.getString("nome_perfil", "")

        when(rotaAtual) {
            context?.resources?.getString(R.string.rota_home) -> {
                ocultarHeaders(view)
                view.findViewById<RelativeLayout>(R.id.header_home).visibility = View.VISIBLE
            }
            context?.resources?.getString(R.string.rota_perfil) -> {
                ocultarHeaders(view)
                view.findViewById<RelativeLayout>(R.id.header_perfil_interno).visibility = View.VISIBLE
                val nome = view.findViewById<TextView>(R.id.header_perfil_interno_nome)
                nome.text = nome_perfil
            }
            context?.resources?.getString(R.string.rota_perfil_expecifico) -> {
                ocultarHeaders(view)
                view.findViewById<RelativeLayout>(R.id.header_perfil_expecifico).visibility = View.VISIBLE
                val nome = view.findViewById<TextView>(R.id.header_nome)
                nome.text = nome_perfil
            }
            context?.resources?.getString(R.string.rota_editar_perfil) -> {
                ocultarHeaders(view)
                view.findViewById<RelativeLayout>(R.id.header_editar_perfil).visibility = View.VISIBLE
            }
            context?.resources?.getString(R.string.rota_nova_publicacao) -> {
                ocultarHeaders(view)
                view.findViewById<RelativeLayout>(R.id.header_nova_publicacao).visibility = View.VISIBLE
            }
        }
    }

    fun ocultarHeaders(view: View){
        view.findViewById<RelativeLayout>(R.id.header_perfil_interno).visibility = View.GONE
        view.findViewById<RelativeLayout>(R.id.header_perfil_expecifico).visibility = View.GONE
        view.findViewById<RelativeLayout>(R.id.header_home).visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HeaderFragment()
    }
}