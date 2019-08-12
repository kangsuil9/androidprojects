package com.example.cpt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.concurrent.thread

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.hide()

        setButtons()
    }

    private fun setButtons() {
        sw_signup_youth.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                sw_signup_adult.isChecked = false
                sw_signup_senior.isChecked = false
            }
        }
        sw_signup_adult.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                sw_signup_youth.isChecked = false
                sw_signup_senior.isChecked = false
            }
        }
        sw_signup_senior.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                sw_signup_youth.isChecked = false
                sw_signup_adult.isChecked = false
            }
        }

        tv_signup_cancel.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        btn_signup_signup.setOnClickListener {
            if(validate()) {
                insertuser()
            }
        }
    }

    private fun insertuser() {
        //Email Exist Check
        val dbHandler = DBHandler(this)
        thread(start = true) {
            val email = dbHandler.myInterface.connectionmysql(Email("CHECK_EMAILEXIST", et_signup_email.text.toString()))
            var agegroup:String
            if(sw_signup_youth.isChecked)
                agegroup = "Youth"
            else if(sw_signup_adult.isChecked)
                agegroup = "Adult"
            else
                agegroup = "Senior"

            if(!email.equals(et_signup_email.text.toString())) {
                val result = dbHandler.myInterface.connectionmysql(
                    InsertUser(
                        "SIGNUP",
                        et_signup_email.text.toString(),
                        et_signup_password.text.toString(),
                        agegroup,
                        et_signup_firstname.text.toString(),
                        et_signup_middlename.text.toString(),
                        et_signup_lastname.text.toString()
                    )
                )
                if(result.equals("Good")) {
                    GoToSignIn()
                }
                else {
                    txt_signup_warning.setText(result)
                }
            }
            else {
                txt_signup_warning.setText(resources.getString(R.string.warning_emailexist))
            }
        }
    }

    private fun validate():Boolean {
        //Email
        if(et_signup_email.text.isNullOrEmpty()) {
            txt_signup_warning.setText(resources.getString(R.string.warning_noemail))
            return false
        }

        //Password
        if(et_signup_password.text.isNullOrBlank() || et_signup_repassword.text.isNullOrBlank()) {
            txt_signup_warning.setText(resources.getString(R.string.warning_nopassword))
            return false
        }

        //Password Equality
        if(!et_signup_password.text.toString().equals(et_signup_repassword.text.toString())) {
            txt_signup_warning.setText(resources.getString(R.string.warning_notsamepassword))
            return false
        }

        //Password Policy
        if(!passwordPolicy()) {
            txt_signup_warning.setText(resources.getString(R.string.warning_passwordpolicy))
            return false
        }

        //AgeGroup
        if(!sw_signup_youth.isChecked && !sw_signup_adult.isChecked && !sw_signup_senior.isChecked) {
            txt_signup_warning.setText(resources.getString(R.string.warning_agegroup))
            return false
        }

        //Last Name
        if(et_signup_lastname.text.isNullOrBlank()) {
            txt_signup_warning.setText(resources.getString(R.string.warning_lastname))
            return false
        }

        return true
    }

    private fun passwordPolicy(): Boolean {
        val pw = et_signup_password.text.toString()
        var intIncluded = false
        var charIncluded = false
        if (pw.length < 8)
            return false
        for(i in 0..pw.length-1) {
            if(pw[i].toInt() >= 48 && pw[i].toInt() <= 57) {
                intIncluded = true
            }
            if(pw[i].toInt() >= 97 && pw[i].toInt() <= 122) {
                charIncluded = true
            }
        }
        if(intIncluded && charIncluded)
            return true
        return false
    }

    override fun onBackPressed() {

    }

    private fun GoToSignIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}