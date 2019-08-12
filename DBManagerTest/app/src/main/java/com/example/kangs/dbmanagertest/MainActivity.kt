package com.example.kangs.dbmanagertest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, com.example.kangs.guestapp.MainAppActivity::class.java)
        startActivity(intent)

        btnStartSignUp.setOnClickListener() {
            val intent = Intent(this, SignUp1::class.java)
            startActivity(intent)
        }
    }
}