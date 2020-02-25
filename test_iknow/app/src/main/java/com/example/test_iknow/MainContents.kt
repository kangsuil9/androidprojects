package com.example.test_iknow

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_contents.*

class MainContents : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_contents)
        supportActionBar!!.hide()

        Init()
    }

    private fun Init() {
        val token = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE)

        if(token.getString("COMMONSENSE", null) == "Y")
            txtText.text = txtText.text.toString().plus(token.getString("COMMONSENSE", null))


    }
}