package com.example.cpt

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_intro__place_you_live.*

class Intro_PlaceYouLive : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__place_you_live)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var token = getSharedPreferences(R.string.LOGIN_INFO.toString(), Context.MODE_PRIVATE)
        var editor = token.edit()

        eti_place_postcode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isEmpty())
                    btni_place_continue.isEnabled = false
                else
                    btni_place_continue.isEnabled = true
            }
        })

        btni_place_continue.setOnClickListener{
            editor.putString(R.string.WHEREYOULIVE.toString(), eti_place_postcode.text.toString()).apply()
            val intent = Intent(this, Intro_LocationPermission::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
