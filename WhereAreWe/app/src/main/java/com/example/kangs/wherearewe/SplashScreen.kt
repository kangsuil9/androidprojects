package com.example.kangs.wherearewe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.lang.Thread.sleep

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar!!.hide()

        Thread( {
            sleep(2000)
            val intent = Intent(this, PasswordSetting::class.java)
            startActivity(intent)
        }).start()
    }
}
