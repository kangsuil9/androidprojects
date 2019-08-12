package com.example.kangs.guestapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.activity_main_app.*

class MainAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val fragmentAdapter = PaserAdapter(supportFragmentManager)
        viewpaser_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpaser_main)
    }
}