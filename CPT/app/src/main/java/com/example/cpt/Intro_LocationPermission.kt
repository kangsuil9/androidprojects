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
import kotlinx.android.synthetic.main.activity_intro__location_permission.*

class Intro_LocationPermission : AppCompatActivity() {

    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro__location_permission)
        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setButtons()
    }

    private fun setButtons() {
        tvi_location_skip.setOnClickListener{
            Next()
        }

        btni_location_permission.setOnClickListener{
            getpermission()
        }
    }

    private fun getpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Next()
        }
        else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), RECORD_REQUEST_CODE);
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), RECORD_REQUEST_CODE);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if( requestCode == RECORD_REQUEST_CODE ) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Next()
            }
            else {
                Next()
            }
        }
    }

    private fun Next() {
        val intent = Intent(this, Intro_NotificationPermission::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
