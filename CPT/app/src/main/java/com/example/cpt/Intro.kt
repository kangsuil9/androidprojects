package com.example.cpt

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.cpt.more.CPT_More
import java.lang.Thread.sleep

class Intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Thread {
            sleep(2000)
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }.start()
    }
}