package com.example.cpt

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_admin.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Admin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        supportActionBar!!.hide()

        setButtons()
    }

    private fun setButtons() {
        btnadmin_logout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val token = this.baseContext.getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
            builder.setTitle("Log Out")
                .setMessage("Are you sure that you want to Log out?")
                .setPositiveButton("Confirm", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        token.edit().clear().apply()
                        val intent = Intent(this@Admin, SignIn::class.java)
                        startActivity(intent)
                        dialog!!.dismiss()
                    }
                })
                .setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                }).show()
        }

        btnadmin_checkticket.setOnClickListener {
            if(et_admin_signinemail.text.isNullOrEmpty()) {
                val builder = AlertDialog.Builder(this)
                val token = this.baseContext.getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
                builder
                    .setMessage("Put Email Address")
                    .setPositiveButton("Confirm", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    }).show()
                return@setOnClickListener
            }
            if(et_admin_signinlastname.text.isNullOrEmpty()) {
                val builder = AlertDialog.Builder(this)
                val token = this.baseContext.getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
                builder
                    .setMessage("Put Last Name")
                    .setPositiveButton("Confirm", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    }).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AdminResult::class.java)
            intent.putExtra("EMAIL", et_admin_signinemail.text.toString())
            intent.putExtra("LASTNAME", et_admin_signinlastname.text.toString())
            startActivity(intent)
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
    }
}
