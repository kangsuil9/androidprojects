package com.example.kangs.dbmanagertest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class SignUp2 : AppCompatActivity() {

    var signupURL = "http://192.168.137.1/foodtrip_example/signup.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        initialize()

        txtSignUpDateOfBirth.setOnClickListener{
            val datepickerFragment = DatePicker()
            datepickerFragment.show(supportFragmentManager, "Date Picker")
        }

        forTest()


        btnSignUpSignUp.setOnClickListener {
            Signup()
        }
    }

    private fun Signup() {
        if(validation()) {
            insertandsignup()
        }
    }

    private fun forTest() {
        etSignUpFirstName.setText("Kang")
        etSignUpCellphone.setText("01064768318")
        etSignUpStreet.setText("131")
        etSignUpCity.setText("Sapkyo")
        etSignUpCountry.setText("Korea")

        val intent = Intent(this, com.example.kangs.guestapp.MainAppActivity::class.java)
        startActivity(intent)
    }


    private fun initialize() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        var temp_month = month+1
        txtSignUpDateOfBirth.setText("$year/$temp_month/$day".toString())
    }

    private fun validation() : Boolean {
        if(etSignUpFirstName.text.toString().isEmpty()) {
            alert { title = "First Name must be filled."
            yesButton { "YES" }
            }.show()
            return false
        }
        if(spnSignUpGender.selectedItem.toString().equals("Gender")) {
            alert { title = "Select your gender."
                yesButton { "YES" }
            }.show()
            return false
        }
        if(etSignUpCellphone.text.toString().isEmpty()) {
            alert { title = "Cell phone number must be filled."
                yesButton { "YES" }
            }.show()
            return false
        }
        if(etSignUpStreet.text.toString().isEmpty()) {
            alert { title = "Street number must be filled."
                yesButton { "YES" }
            }.show()
            return false
        }
        if(etSignUpCity.text.toString().isEmpty()) {
            alert { title = "City name must be filled."
                yesButton { "YES" }
            }.show()
            return false
        }
        if(etSignUpCountry.text.toString().isEmpty()) {
            alert { title = "Country name must be filled."
                yesButton { "YES" }
            }.show()
            return false
        }
        return true
    }

    private fun insertandsignup() {
        val getintent = intent.extras
        var map = HashMap<String, String>()

        map.put("COUNTRY_CODE", ccpSignUpCountryCode.selectedCountryNameCode)
        map.put("EMAIL", getintent.getString("EMAILADDRESS").toString())
        map.put("PASSWORD", getintent.getString("PASSWORD").toString())
        map.put("FIRSTNAME", etSignUpFirstName.text.toString())
        map.put("LASTNAME", etSignUpLastName.text.toString())

        if(spnSignUpGender.selectedItem.toString().equals("Male"))
            map.put("GENDER", "M")
        else
            map.put("GENDER", "F")
        map.put("DATEOFBIRTH", txtSignUpDateOfBirth.text.toString())

        map.put("ADDRESS_UNIT", etSignUpAptUnit.text.toString())
        map.put("ADDRESS_STREET", etSignUpStreet.text.toString())
        map.put("ADDRESS_CITY", etSignUpCity.text.toString())
        map.put("ADDRESS_PROVINCE", etSignUpProvince.text.toString())
        map.put("ADDRESS_COUNTRY", etSignUpCountry.text.toString())
        map.put("ADDRESS_POSTCODE", etSignUpPostCode.text.toString())
        map.put("CELLPHONE", etSignUpCellphone.text.toString())
        map.put("VERIFYCODE", "")
        map.put("ABOUTME", etSignUpAboutMe.text.toString())

            DBManager.DBService.testVolley(this, signupURL, map) {
                response ->
            if(response.contains(getintent.getString("EMAILADDRESS").toString())) {
                //val intent = Intent(this, com.example.kangs.mainapp.MainAppActivity::class.java)
                //intent.putExtra(("USERINFO_JSON"), response.toString())
                //startActivity(intent)
            }
            else {
                alert { title = "Error Occurs, please contact system manager."
                yesButton { "YES" }
                }.show()
            }
        }
    }
}
