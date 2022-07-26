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
        val rotaAtual = shared!!.getString("rota_atual", "")
        val idPerfil = shared!!.getString("id_perfil", "")
        val nome_perfil = shared!!.getString("nome_perfil", "")
        val idUsuarioLogado = shared.getString("id_usuario_logado", "")


        when(rotaAtual) {
            context?.resources?.getString(R.string.rota_home) -> {
                if(idPerfil != "" && idPerfil != idUsuarioLogado){
                    view.findViewById<RelativeLayout>(R.id.header_perfil_expecifico).visibility = View.VISIBLE
                    view.findViewById<RelativeLayout>(R.id.header_home).visibility = View.GONE
                    view.findViewById<RelativeLayout>(R.id.header_perfil_interno).visibility = View.GONE
                    val nome = view.findViewById<TextView>(R.id.header_nome)
                    nome.text = nome_perfil
                }else{
                    view.findViewById<RelativeLayout>(R.id.header_home).visibility = View.VISIBLE
                    view.findViewById<RelativeLayout>(R.id.header_perfil_interno).visibility = View.GONE
                    view.findViewById<RelativeLayout>(R.id.header_perfil_expecifico).visibility = View.GONE
                }
            }
            context?.resources?.getString(R.string.rota_perfil) -> {
                view.findViewById<RelativeLayout>(R.id.header_perfil_interno).visibility = View.VISIBLE
                view.findViewById<RelativeLayout>(R.id.header_perfil_expecifico).visibility = View.GONE
                view.findViewById<RelativeLayout>(R.id.header_home).visibility = View.GONE
                val nome = view.findViewById<TextView>(R.id.header_perfil_interno_nome)
                nome.text = nome_perfil
            }
        }

        val botaoLogout = view.findViewById<ImageView>(R.id.logout)



        botaoLogout.setOnClickListener {
            shared.edit().remove("token").apply()
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HeaderFragment()
    }
}