package com.example.kangs.wherearewe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.customalertdialog.view.*

class Login : AppCompatActivity() {

    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        val bundle = intent.extras
        password = bundle?.getString("PASSWORD", "0")

        btnLoginLogin.setOnClickListener {
            if(login()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        
        etLoginPassword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER ) {
                if(login()){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                return@OnKeyListener true
            }
            false
        })
    }

    private fun login() : Boolean {
        if(!etLoginPassword.text.toString().equals(password)) {
            val view : View = layoutInflater.inflate(R.layout.customalertdialog, null)
            var builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setView(view)
            val mDialog = builder.show()

            view.btnWrongPasswordOk.setOnClickListener {
                mDialog.dismiss()
            }
            return false
        }
        return true
    }
}
