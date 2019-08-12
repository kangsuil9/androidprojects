package com.example.timetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val time = Calendar.getInstance()

        calendarTime.setText(time.time.toString())

        var s = SimpleDateFormat()
        s!!.timeZone = TimeZone.getTimeZone("Canada/Eastern")
        var d = Date(System.currentTimeMillis())
        systemTime.setText(s!!.format(object: Date() {}))

    }
}
