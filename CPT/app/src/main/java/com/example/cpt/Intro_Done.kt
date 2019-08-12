package com.example.cpt

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_intro__done.*

class Intro_Done : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__done)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var token = getSharedPreferences(R.string.LOGIN_INFO.toString(), Context.MODE_PRIVATE)
        var editor = token.edit()

        btni_done_continue.setOnClickListener {
            editor.putString(R.string.LOGINDONE.toString(), "login done.").apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}