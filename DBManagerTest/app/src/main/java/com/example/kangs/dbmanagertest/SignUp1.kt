package com.example.kangs.dbmanagertest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up1.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class SignUp1 : AppCompatActivity() {

    var testUrl = "http://192.168.137.1/foodtrip_example/checkemailexist.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        etSignUpEmailAddress.setText("zzkangsuil9@gmail.com")
        etSignUpPassword.setText("7Dnfkwlf")
        etSignUpRetryPassword.setText("7Dnfkwlf")

        btnSignUpNext.setOnClickListener {
            if(validation())
                checkEmail()
        }
    }

    private fun validation() :Boolean {
        if(etSignUpEmailAddress.text.toString().isEmpty() || etSignUpPassword.text.toString().isEmpty() || etSignUpRetryPassword.text.toString().isEmpty()) {
            alert { title = "All fields must be filled"
                yesButton { "YES" }
            }.show()
            return false
        }
        if( !etSignUpPassword.text.toString().equals(etSignUpRetryPassword.text.toString())) {
            alert { title = "Must the same password"
                yesButton { "YES" }
            }.show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etSignUpEmailAddress.text.toString()).matches()) {
            alert { title = "Check your Email Address."
                yesButton { "YES" }
            }.show()
            return false
        }
        return true
    }

    private fun checkEmail() {
        var map = HashMap<String, String>()
        map.put("EMAILADDRESS", etSignUpEmailAddress.text.toString())

        DBManager.DBService.testVolley(this, testUrl, map) {
            response ->
            if(response.contains(etSignUpEmailAddress.text.toString())) {
                alert {
                    title = "Email already exist."
                    yesButton { "YES" }
                }.show()
            }
            else {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                val intent = Intent(this, SignUp2::class.java)
                intent.putExtra("EMAILADDRESS", etSignUpEmailAddress.text.toString())
                intent.putExtra("PASSWORD", etSignUpPassword.text.toString())
                startActivity(intent)
            }
        }
    }
}
