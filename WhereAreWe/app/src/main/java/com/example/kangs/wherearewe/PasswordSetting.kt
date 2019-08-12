package com.example.kangs.wherearewe

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_password_setting.*

class PasswordSetting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_setting)
        supportActionBar!!.hide()

        val password = exsitPassword()
        when(password) {
            "0" -> {
            }
            "SKIP" -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(this, Login::class.java)
                intent.putExtra("PASSWORD", password)
                startActivity(intent)
            }
        }


        btnIntroSetPassword.setOnClickListener {
            SetPassword()
        }

        btnIntroSkipPassword.setOnClickListener {
            skipPassword() }
    }



    private fun exsitPassword() : String {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val password = pref?.getString("PASSWORD", "0").toString()
        return password
    }

    private fun skipPassword() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("PASSWORD", "SKIP").
                apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun SetPassword() {
        if(!validation())
            return

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("PASSWORD", etIntroPassword.text.toString()).
                apply()

        val intent = Intent(this, Login::class.java)
        intent.putExtra("PASSWORD", etIntroPassword.text.toString())
        startActivity(intent)
    }

    private fun validation() : Boolean {

        if(!etIntroPassword.text.toString().equals(etIntroRetryPassword.text.toString())) {
            tvError.setText("match password")
            return false
        }
        if(etIntroPassword.length() < 6) {
            tvError.setText("Too Short, more than 6 characters")
            return false
        }
        return true
    }
}