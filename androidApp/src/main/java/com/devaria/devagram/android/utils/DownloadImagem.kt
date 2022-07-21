package com.devaria.devagram.android.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class DownloadImagem(imageView: ImageView) : AsyncTask<String?, Void?, Bitmap?>() {
    var imageView = imageView
    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urlDisplay = urls[0]
        var imagem: Bitmap? = null
        try {
            val `in`: InputStream = URL(urlDisplay).openStream()
            imagem = BitmapFactory.decodeStream(`in`)
        }catch (e: Exception){
            Log.i("Erro", e.message.toString())
        }
        return imagem
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }

    init {
        this.imageView = imageView
    }
}