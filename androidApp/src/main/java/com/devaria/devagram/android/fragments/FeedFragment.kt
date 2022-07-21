package com.devaria.devagram.android.fragments

import PublicoesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.devaria.devagram.android.R
import com.devaria.devagram.model.Feed.Comentario
import com.devaria.devagram.model.Feed.Publicacao
import com.devaria.devagram.model.Usuario.Usuario

class FeedFragment : Fragment() {
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
        val likes = ArrayList<String>()
        val likes2 = ArrayList<String>()
        likes2.add("1123132")
        val comentarios = ArrayList<Comentario>()

        publicacoes.add(Publicacao(
            "1212",
            "12387",
            "Teste",
            "https://cdn.cosmicjs.com/794d7fa0-0863-11ed-b7be-d956591ad437-Rectangle-444.png",
            "20-07-2022",
            comentarios,
            likes,
            Usuario(
                "Kaique",
                "https://cdn.cosmicjs.com/794d7fa0-0863-11ed-b7be-d956591ad437-Rectangle-444.png"),
        )
        )

        publicacoes.add(Publicacao(
            "12212",
            "122387",
            "Teste 2",
            "https://cdn.cosmicjs.com/794d7fa0-0863-11ed-b7be-d956591ad437-Rectangle-444.png",
            "20-07-2022",
            comentarios,
            likes2,
            Usuario(
                "Daniel",
                "https://cdn.cosmicjs.com/794d7fa0-0863-11ed-b7be-d956591ad437-Rectangle-444.png"),
        )
        )

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