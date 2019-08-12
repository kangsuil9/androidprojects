package com.example.cpt

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cpt.MyTicket.CPT_MyTicket
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.ArrayList
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar!!.hide()


        if(checkLoginInfo()) {
            GoToMain()
        }
        setButtons()
    }

    private fun checkLoginInfo(): Boolean {
        val token = getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
        if(token.getString(resources.getString(R.string.SIGNIN_EMAIL), null) != null)
            return true
        return false
    }

    private fun setButtons() {
        btn_signin.setOnClickListener {
            if(edittextValidation()) {
                loginInfoValidation()
            }
        }
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
    }

    private fun edittextValidation():Boolean {
        if(et_signin_email.equals("")) {
            txt_signin_warning.setText(resources.getString(R.string.warning_noemail))
            return false
        }
        if(et_signin_password.equals("")) {
            txt_signin_warning.setText(resources.getString(R.string.warning_nopassword))
            return false
        }
        return true
    }

    private fun loginInfoValidation() {

        val dbHandler = DBHandler(this)
        val myService: ExecutorService = Executors.newFixedThreadPool(1)
        val result_email = myService.submit(Callable<String>{
            dbHandler.myInterface.connectionmysql(
                Email(
                    "CHECK_EMAILEXISTE",
                    et_signin_email.text.toString()
                )
            )
        })
        if(result_email.get().equals(et_signin_email.text.toString())) {
            val result = myService.submit(Callable<JsonArray>{
                dbHandler.myInterface.connectionmysql(
                    Login(
                        "LOGIN",
                        et_signin_email.text.toString(),
                        et_signin_password.text.toString()
                    )
                )
            })
            if(result.get().size() > 0) {
                GoToMain(result.get())
            }
            else {
                txt_signin_warning.setText(resources.getString(R.string.warning_wrongpassword))
            }
        }
        else {
            txt_signin_warning.setText(resources.getString(R.string.warning_nouser))
        }


        /*
        val dbhandler = DBHandler(this)
        thread(start = true) {
            val email = dbhandler.myInterface.connectionmysql(Email("CHECK_EMAILEXISTE", et_signin_email.text.toString()))
            if(email.equals(et_signin_email.text.toString())) {
                val loginInfo: ArrayList<LoginResult> = Gson().fromJson<ArrayList<LoginResult>>(
                    dbhandler.myInterface.connectionmysql(Login("LOGIN", et_signin_email.text.toString(), et_signin_password.text.toString())),
                    object: TypeToken<ArrayList<LoginResult>>() {}.type)

                if(loginInfo[0].email.equals(et_signin_email.text.toString())) {
                    GoToMain(loginInfo)
                }
                else {
                    txt_signin_warning.setText(resources.getString(R.string.warning_wrongpassword))
                }
            }
            else {
                txt_signin_warning.setText(resources.getString(R.string.warning_nouser))
            }
        }
        */
    }

    private fun GoToMain(loginInfo: JsonArray) {
        val token = getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
        val editor = token.edit()

        val jsonobject = loginInfo.get(0)
        editor.putString(resources.getString(R.string.SIGNIN_EMAIL), jsonobject.asJsonObject.get("USER_EMAIL").toString().replace("\"", "")).apply()
        editor.putString(resources.getString(R.string.SIGNIN_AGEGROUP), jsonobject.asJsonObject.get("USER_AGEGROUP").toString().replace("\"", "")).apply()
        editor.putString(resources.getString(R.string.SIGNIN_FIRSTNAME), jsonobject.asJsonObject.get("USER_FIRSTNAME").toString().replace("\"", "")).apply()
        editor.putString(resources.getString(R.string.SIGNIN_MIDDLENAME), jsonobject.asJsonObject.get("USER_MIDDLENAME").toString().replace("\"", "")).apply()
        editor.putString(resources.getString(R.string.SIGNIN_LASTNAME), jsonobject.asJsonObject.get("USER_LASTNAME").toString().replace("\"", "")).apply()

        if(jsonobject.asJsonObject.get("USER_EMAIL").toString().replace("\"", "").equals("admin")) {
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
        }
        else {
            val dbHandler = DBHandler(this)
            val myService: ExecutorService = Executors.newFixedThreadPool(1)
            val result = myService.submit(Callable<JsonArray>{
                dbHandler.myInterface.connectionmysql(
                    Load_All_ValidTickets(
                        "LOAD_ALL_VALIDTICKETS",
                        jsonobject.asJsonObject.get("USER_EMAIL").toString().replace("\"", ""),
                        jsonobject.asJsonObject.get("USER_LASTNAME").toString().replace("\"", ""),
                        System.currentTimeMillis()
                    )
                )
            })

            if(result.get().size() > 0) {
                val validticketlist: ArrayList<Ticket> = arrayListOf()
                for(jobject in result.get()) {
                    val ticket = Ticket()
                    ticket.tickettype = jobject.asJsonObject.get("TICKET_TYPE").toString().replace("\"", "")
                    ticket.timeleft = jobject.asJsonObject.get("EXPIRE_TIMEMILLI").toString().replace("\"", "").toLong() - System.currentTimeMillis()
                    validticketlist.add(ticket)
                }

                val token_validtickets = getSharedPreferences(R.string.TICKET_LIST.toString(), Context.MODE_PRIVATE)
                val editor_validtickets = token_validtickets.edit()

                val gson = Gson()
                val jsonList: String = gson.toJson(validticketlist)
                editor_validtickets.putString(R.string.TICKET_LIST.toString(), jsonList).apply()
                editor_validtickets.putLong(R.string.TICKET_CURRENTTIME.toString(), System.currentTimeMillis()).apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun GoToMain() {
        val token = getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
        if(token.getString(resources.getString(R.string.SIGNIN_EMAIL), null)!!.equals("admin")) {
            val intent = Intent(this, Admin::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
