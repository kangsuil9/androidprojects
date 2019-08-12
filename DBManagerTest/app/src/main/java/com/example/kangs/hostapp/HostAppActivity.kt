package com.example.kangs.hostapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.activity_host_app.*

class HostAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_app)

        val fragmentAdapter = PaserAdapter(supportFragmentManager)
        viewpaser_host.adapter = fragmentAdapter

        tabs_host.setupWithViewPager(viewpaser_host)
    }
}