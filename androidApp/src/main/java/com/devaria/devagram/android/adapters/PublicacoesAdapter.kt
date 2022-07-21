import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.devaria.devagram.android.R
import com.devaria.devagram.android.utils.DownloadImagem
import com.devaria.devagram.model.Feed.Publicacao


class PublicoesAdapter(context: Context?, publicacoes: ArrayList<Publicacao>) :
    ArrayAdapter<Publicacao>(context!!, 0, publicacoes) {

    override fun isEnabled(position: Int): Boolean{
        return false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var curtido: Boolean = false
        var comentado: Boolean = false

        var convertView: View? = convertView
        val publicacao: Publicacao? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_publicacao, parent, false)
        }


        val avatar : ImageView = convertView!!.findViewById(R.id.avatar)
        val nome : TextView = convertView!!.findViewById(R.id.nome)
        val imagem : ImageView = convertView!!.findViewById(R.id.imagem)
        val avatarInput : ImageView = convertView!!.findViewById(R.id.avatar_input)
        val curtir : ImageView = convertView!!.findViewById(R.id.curtir)
        val comentario : ImageView = convertView!!.findViewById(R.id.comentario)
        val qtdCurtidas : TextView = convertView!!.findViewById(R.id.qtd_curtidas)
        val nomeDescricao : TextView = convertView!!.findViewById(R.id.nome_descricao)
        val descricao : TextView = convertView!!.findViewById(R.id.texto_descricao)
        val containerComentario : RelativeLayout = convertView!!.findViewById(R.id.input_container)

        DownloadImagem(avatar).execute(publicacao!!.usuario.avatar)
        DownloadImagem(avatarInput).execute(publicacao!!.usuario.avatar)
        DownloadImagem(imagem).execute(publicacao!!.foto)
        nome.setText(publicacao!!.usuario.nome)
        qtdCurtidas.setText(publicacao!!.likes.count().toString() + " pessoas")
        nomeDescricao.setText(publicacao!!.usuario.nome)
        descricao.setText(publicacao!!.descricao)

        curtir.setOnClickListener {
            if(!curtido){
                curtido = true
                curtir.setImageDrawable(context.resources.getDrawable(R.drawable.curtir))
            }else{
                curtido = false
                curtir.setImageDrawable(context.resources.getDrawable(R.drawable.curtir_inativo))
            }
        }

        comentario.setOnClickListener {
            if(!comentado){
                containerComentario.visibility = View.VISIBLE
                comentado = true
                comentario.setImageDrawable(context.resources.getDrawable(R.drawable.comentario))
            }else{
                containerComentario.visibility = View.GONE
                comentado = false
                comentario.setImageDrawable(context.resources.getDrawable(R.drawable.comentario_inativo))
            }
        }

        return convertView
    }
}