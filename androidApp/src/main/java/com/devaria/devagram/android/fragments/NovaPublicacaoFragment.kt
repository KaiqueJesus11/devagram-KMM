package com.devaria.devagram.android.fragments

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.devaria.devagram.android.R
import com.devaria.devagram.android.utils.Imagem
import java.io.ByteArrayOutputStream


class NovaPublicacaoFragment : Fragment() {
    val REQUEST_CODE = 200
    lateinit var foto : ImageView
    var fotoBinario: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nova_publicacao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }

    companion object {
        fun newInstance() = NovaPublicacaoFragment()
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        val permission = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.CAMERA
            )
        }

        if(permission != PackageManager.PERMISSION_GRANTED ){
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        }

        capturarFoto()
    }

    fun capturarFoto (){
        val intent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startImageResult.launch(intent)
    }

    private val startImageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            foto.setImageURI(result.data?.data)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val shared = context?.getSharedPreferences("devagram", Context.MODE_PRIVATE)

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            var imagem = Imagem().editarImage(data.extras?.get("data") as Bitmap, 120, 120)
            val stream = ByteArrayOutputStream()
            imagem!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            fotoBinario = stream.toByteArray()
            shared!!.edit().putString("editar_perfil_avatar", fotoBinario.toString())
            foto.setImageBitmap(imagem)
        }
    }
}