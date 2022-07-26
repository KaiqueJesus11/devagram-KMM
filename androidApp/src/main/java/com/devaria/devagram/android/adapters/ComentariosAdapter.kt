import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.devaria.devagram.android.R
import com.devaria.devagram.model.Feed.Comentario

class ComentariosAdapter(context: Context?, comentarios: ArrayList<Comentario>) :
    ArrayAdapter<Comentario>(context!!, 0, comentarios) {

    override fun isEnabled(position: Int): Boolean{
        return false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        val comentario: Comentario? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comentario, parent, false)
        }

        val textoComentario : TextView = convertView!!.findViewById(R.id.texto_comentario)

        val comentarioFormatado = "<b>${comentario!!.nome}</b> ${comentario!!.comentario}"
        textoComentario.text = HtmlCompat.fromHtml(comentarioFormatado, HtmlCompat.FROM_HTML_MODE_LEGACY)

        return convertView
    }
}