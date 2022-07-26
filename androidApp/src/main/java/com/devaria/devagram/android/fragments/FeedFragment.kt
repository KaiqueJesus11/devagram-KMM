package com.devaria.devagram.android.fragments

import PublicoesAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.devaria.devagram.android.R
import com.devaria.devagram.android.utils.Dialog
import com.devaria.devagram.model.Cadastrar.ResponseErro
import com.devaria.devagram.model.Feed.Comentario
import com.devaria.devagram.model.Feed.Publicacao
import com.devaria.devagram.model.Usuario.Usuario
import com.devaria.devagram.services.Feed
import io.ktor.client.call.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val publicacoes: ArrayList<Publicacao> = ArrayList<Publicacao>()

        mainScope.launch {
            kotlin.runCatching {
                val token = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)?.getString("token", "")
                Feed().getFeed(token!!)
            }.onSuccess {
                if(it.status.value >= 400){
                    val erroData : ResponseErro = it.body()
                    Log.e("Erro", "Erro: ${erroData.erro}")
                    context?.let { context -> Dialog(context).show("Erro", "Erro: ${erroData.erro}") }
                }else{
                    val publicacoes : ArrayList<Publicacao> = it.body()
                    val adapter = PublicoesAdapter(view.context, publicacoes)
                    val listView : ListView = view.findViewById(R.id.list_view)
                    listView.adapter = adapter
                }
            }.onFailure {
                Log.e("Erro", "Erro: ${it.localizedMessage}")
                context?.let { context -> Dialog(context).show("Erro", "Erro: ${it.localizedMessage}") }
            }
        }

        val adapter = PublicoesAdapter(view.context, publicacoes)
        val listView : ListView = view.findViewById(R.id.list_view)
        listView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FeedFragment()
    }
}