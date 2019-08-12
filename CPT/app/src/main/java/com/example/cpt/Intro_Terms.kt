package com.example.cpt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_intro__terms.*

class Intro_Terms : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__terms)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btni_terms_continue.setOnClickListener {
            next()
        }
    }

    private fun next() {
        val intent = Intent(this, Intro_Done::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
