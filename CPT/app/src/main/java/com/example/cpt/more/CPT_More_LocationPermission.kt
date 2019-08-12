package com.example.cpt.more

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import com.example.cpt.R
import kotlinx.android.synthetic.main.activity_cpt__more__location_permission.*

class CPT_More_LocationPermission : AppCompatActivity() {

    val locationEnabled: String = "Location Service Enabled"
    val locationDisabled: String = "Location Service Disabled"
    val setEnable: String = "Enable"
    val setDisable: String = "Disable"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__more__location_permission)
        supportActionBar!!.hide()

        getLocationSettings()
        setButtons()
    }

    override fun onResume() {
        super.onResume()
        getLocationSettings()
    }

    private fun getLocationSettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            tvmore_locationpermit_status.setText(locationEnabled)
            tvmore_locationpermit_setting.setText(setDisable)
        }
        else {
            tvmore_locationpermit_status.setText(locationDisabled)
            tvmore_locationpermit_setting.setText(setEnable)
        }
    }


    private fun setButtons() {
        tvmore_locationpermit_cancel.setOnClickListener {
            onBackPressed()
        }

        tvmore_locationpermit_setting.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
