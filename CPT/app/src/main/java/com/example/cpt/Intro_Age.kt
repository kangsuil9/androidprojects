package com.example.cpt

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_intro__age.*

class Intro_Age : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__age)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val token = getSharedPreferences(R.string.LOGIN_INFO.toString(), Context.MODE_PRIVATE)
        val editor = token.edit()
        var sAgetype = ""

        if(token.getString(R.string.LOGINDONE.toString(), " ") != " ") {
            GoToMain()
        }
        swi_age_youth.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swi_age_adult.isChecked = false
                swi_age_senior.isChecked = false
                btni_age_continue.isEnabled = true
                sAgetype = "YOUTH"
            }
            else {
                if(!swi_age_adult.isChecked && !swi_age_senior.isChecked)
                    btni_age_continue.isEnabled = false
            }
        }
        swi_age_adult.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swi_age_youth.isChecked = false
                swi_age_senior.isChecked = false
                btni_age_continue.isEnabled = true
                sAgetype = "ADULT"
            }
            else {
                if(!swi_age_youth.isChecked && !swi_age_senior.isChecked)
                    btni_age_continue.isEnabled = false
            }
        }
        swi_age_senior.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swi_age_adult.isChecked = false
                swi_age_youth.isChecked = false
                btni_age_continue.isEnabled = true
                sAgetype = "SENIOR"
            }
            else {
                if(!swi_age_adult.isChecked && !swi_age_youth.isChecked)
                    btni_age_continue.isEnabled = false
            }
        }

        btni_age_continue.setOnClickListener{
            editor.putString(R.string.AGETYPE.toString(), sAgetype).apply()
            val intent = Intent(this, Intro_PlaceYouLive::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun GoToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun finish() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
