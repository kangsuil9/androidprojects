package com.example.cpt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_intro__notification_permission.*

class Intro_NotificationPermission : AppCompatActivity() {

    private val LOCATION_PERMISSION_CODE = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__notification_permission)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tvi_notification_skip.setOnClickListener {
            Next()
        }

        btni_notification_permission.setOnClickListener {
            GetPermission()
        }
    }

    private fun GetPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED) {
            Next()
        }
        else {
            RequestPermission()
        }
    }

    private fun RequestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_NOTIFICATION_POLICY), LOCATION_PERMISSION_CODE)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_NOTIFICATION_POLICY), LOCATION_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == LOCATION_PERMISSION_CODE) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Next()
            }
            else {
            }
        }
    }

    private fun Next() {
        val intent = Intent(this, Intro_Terms::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
