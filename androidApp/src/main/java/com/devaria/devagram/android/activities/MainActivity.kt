package com.devaria.devagram.android.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devaria.devagram.android.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val token = getSharedPreferences("devagram", Context.MODE_PRIVATE).getString("token", "")
        if(token != ""){
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
