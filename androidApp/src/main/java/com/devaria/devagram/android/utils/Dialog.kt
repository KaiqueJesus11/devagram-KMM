package com.devaria.devagram.android.utils

import android.app.AlertDialog
import android.content.Context

class Dialog(context: Context) {
    val context = context

    fun show(title: String, message: String){
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}