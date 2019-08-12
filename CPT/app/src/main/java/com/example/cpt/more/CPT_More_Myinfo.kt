package com.example.cpt.more

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cpt.*
import kotlinx.android.synthetic.main.activity_cpt__more__myinfo.*
import kotlin.concurrent.thread

class CPT_More_Myinfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__more__myinfo)
        supportActionBar!!.hide()

        GetInfo()
        setButtons()
    }

    private fun setButtons() {
        swmore_myinfo_youth.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swmore_myinfo_adult.isChecked = false
                swmore_myinfo_senior.isChecked = false
            }
        }
        swmore_myinfo_adult.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swmore_myinfo_youth.isChecked = false
                swmore_myinfo_senior.isChecked = false
            }
        }
        swmore_myinfo_senior.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                swmore_myinfo_youth.isChecked = false
                swmore_myinfo_adult.isChecked = false
            }
        }

        tvmore_myinfo_cancel.setOnClickListener {
            onBackPressed()
        }

        tvmore_myinfo_save.setOnClickListener {
            save()
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun save() {
        var agegroup = ""
        if(swmore_myinfo_youth.isChecked)
            agegroup = "Youth"
        else if(swmore_myinfo_adult.isChecked)
            agegroup = "Adult"
        else if(swmore_myinfo_senior.isChecked)
            agegroup = "Senior"

        val dbHandler = DBHandler(this)

        thread(start = true) {
            val result = dbHandler.myInterface.connectionmysql(UpdateUser(
                "UPDATE_USER_INFO",
                MainActivity.G.g_email,
                agegroup,
                etmore_myinfo_firstname.text.toString(),
                etmore_myinfo_middlename.text.toString(),
                etmore_myinfo_lastname.text.toString()
            ))
            if(result.equals("Good")) {
                val token = getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
                val editor = token.edit()

                editor.putString(resources.getString(R.string.SIGNIN_AGEGROUP), agegroup).apply()
                editor.putString(resources.getString(R.string.SIGNIN_FIRSTNAME), etmore_myinfo_firstname.text.toString()).apply()
                editor.putString(resources.getString(R.string.SIGNIN_MIDDLENAME), etmore_myinfo_middlename.text.toString()).apply()
                editor.putString(resources.getString(R.string.SIGNIN_LASTNAME), etmore_myinfo_lastname.text.toString()).apply()

                MainActivity.G.g_agegroup = agegroup
                MainActivity.G.g_firstname = etmore_myinfo_firstname.text.toString()
                MainActivity.G.g_middlename = etmore_myinfo_middlename.text.toString()
                MainActivity.G.g_lastname = etmore_myinfo_lastname.text.toString()
            }
        }

    }

    private fun GetInfo() {
        txtmore_myinfo_email.setText(MainActivity.G.g_email)
        when(MainActivity.G.g_agegroup) {
            "Youth" -> swmore_myinfo_youth.isChecked = true
            "Adult" -> swmore_myinfo_adult.isChecked = true
            "Senior" -> swmore_myinfo_senior.isChecked = true
            else -> null
        }
        etmore_myinfo_firstname.setText(MainActivity.G.g_firstname)
        etmore_myinfo_middlename.setText(MainActivity.G.g_middlename)
        etmore_myinfo_lastname.setText(MainActivity.G.g_lastname)
    }
}