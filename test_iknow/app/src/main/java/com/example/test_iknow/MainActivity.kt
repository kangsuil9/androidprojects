package com.example.test_iknow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    object G {
        var isLogin = ""

    }

    private var isCommonSense = "N"
    private var isBibleVerse = "N"
    private var isGoodSaying = "N"
    private var isNewWord = "N"
    private var isCooltem = "N"
    private var isPerson = "N"
    private var isJews = "N"
    private var isEtc = "N"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        Init()
        SetButtons()
    }

    private fun Init() {
        val token = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE)
        if(token.getString("ISLOGIN", null) == "SET") {
            GoToMain()
        }
    }

    private fun SetButtons() {

        val token = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE)
        if(token.getString("ISLOGIN", null) != null) {
            if(token.getString("COMMONSENSE", null) == "Y") {
                btnCommonSense.setBackgroundColor(Color.GREEN)
                isCommonSense = "Y"
            }
            else
                btnCommonSense.setBackgroundColor(Color.RED)

            if(token.getString("BIBLEVERSE", null) == "Y") {
                btnBibleVerse.setBackgroundColor(Color.GREEN)
                isBibleVerse = "Y"
            }
            else
                btnBibleVerse.setBackgroundColor(Color.RED)

            if(token.getString("GOODSAYING", null) == "Y") {
                btnGoodSaying.setBackgroundColor(Color.GREEN)
                isGoodSaying = "Y"
            }
            else
                btnGoodSaying.setBackgroundColor(Color.RED)

            if(token.getString("NEWWORD", null) == "Y") {
                btnNewWord.setBackgroundColor(Color.GREEN)
                isNewWord = "Y"
            }
            else
                btnNewWord.setBackgroundColor(Color.RED)

            if(token.getString("COOLTEM", null) == "Y") {
                btnCooltem.setBackgroundColor(Color.GREEN)
                isCooltem = "Y"
            }
            else
                btnCooltem.setBackgroundColor(Color.RED)

            if(token.getString("PERSON", null) == "Y") {
                btnPerson.setBackgroundColor(Color.GREEN)
                isPerson = "Y"
            }
            else
                btnPerson.setBackgroundColor(Color.RED)

            if(token.getString("JEWS", null) == "Y") {
                btnJews.setBackgroundColor(Color.GREEN)
                isJews = "Y"
            }
            else
                btnJews.setBackgroundColor(Color.RED)

            if(token.getString("ETC", null) == "Y") {
                btnEtc.setBackgroundColor(Color.GREEN)
                isEtc = "Y"
            }
            else
                btnEtc.setBackgroundColor(Color.RED)
        }
        else {
            btnCommonSense.setBackgroundColor(Color.RED)
            btnBibleVerse.setBackgroundColor(Color.RED)
            btnGoodSaying.setBackgroundColor(Color.RED)
            btnNewWord.setBackgroundColor(Color.RED)
            btnCooltem.setBackgroundColor(Color.RED)
            btnPerson.setBackgroundColor(Color.RED)
            btnJews.setBackgroundColor(Color.RED)
            btnEtc.setBackgroundColor(Color.RED)
        }

        btnCommonSense.setOnClickListener {
            if(isCommonSense.equals("Y")) {
                btnCommonSense.setBackgroundColor(Color.RED)
                isCommonSense = "N"
            }
            else {
                btnCommonSense.setBackgroundColor(Color.GREEN)
                isCommonSense = "Y"
            }
        }
        btnBibleVerse.setOnClickListener {
            if(isBibleVerse.equals("Y")) {
                btnBibleVerse.setBackgroundColor(Color.RED)
                isBibleVerse = "N"
            }
            else {
                btnBibleVerse.setBackgroundColor(Color.GREEN)
                isBibleVerse = "Y"
            }
        }
        btnGoodSaying.setOnClickListener {
            if(isGoodSaying.equals("Y")) {
                btnGoodSaying.setBackgroundColor(Color.RED)
                isGoodSaying = "N"
            }
            else {
                btnGoodSaying.setBackgroundColor(Color.GREEN)
                isGoodSaying = "Y"
            }
        }
        btnNewWord.setOnClickListener {
            if(isNewWord.equals("Y")) {
                btnNewWord.setBackgroundColor(Color.RED)
                isNewWord = "N"
            }
            else {
                btnNewWord.setBackgroundColor(Color.GREEN)
                isNewWord = "Y"
            }
        }
        btnCooltem.setOnClickListener {
            if(isCooltem.equals("Y")) {
                btnCooltem.setBackgroundColor(Color.RED)
                isCooltem = "N"
            }
            else {
                btnCooltem.setBackgroundColor(Color.GREEN)
                isCooltem = "Y"
            }
        }
        btnPerson.setOnClickListener {
            if(isPerson.equals("Y")) {
                btnPerson.setBackgroundColor(Color.RED)
                isPerson = "N"
            }
            else {
                btnPerson.setBackgroundColor(Color.GREEN)
                isPerson = "Y"
            }
        }
        btnJews.setOnClickListener {
            if(isJews.equals("Y")) {
                btnJews.setBackgroundColor(Color.RED)
                isJews = "N"
            }
            else {
                btnJews.setBackgroundColor(Color.GREEN)
                isJews = "Y"
            }
        }
        btnEtc.setOnClickListener {
            if(isEtc.equals("Y")) {
                btnEtc.setBackgroundColor(Color.RED)
                isEtc = "N"
            }
            else {
                btnEtc.setBackgroundColor(Color.GREEN)
                isEtc = "Y"
            }
        }

        btnConfirm.setOnClickListener {
            SaveContents()
        }
    }

    private fun GoToMain() {
        var intent = Intent(this, MainContents::class.java)
        startActivity(intent)
    }

    private fun SaveContents() {

        if(isCommonSense.equals("N") &&
                isBibleVerse.equals("N") &&
                isGoodSaying.equals("N") &&
                isNewWord.equals("N") &&
                isCooltem.equals("N") &&
                isPerson.equals("N") &&
                isJews.equals("N") &&
                isEtc.equals("N")) {
            Toast.makeText(this, "최소 한가지 컨텐츠를 선택해야 합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val token = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE)
        val editor = token.edit()

        // TEST!!!!!!!!!!!!!!!!!!!!!!            editor.putString("ISLOGIN", "SET").apply()

        if(isCommonSense.equals("Y"))
            editor.putString("COMMONSENSE", "Y").apply()

        if(isBibleVerse.equals("Y"))
            editor.putString("BIBLEVERSE", "Y").apply()

        if(isGoodSaying.equals("Y"))
            editor.putString("GOODSAYING", "Y").apply()

        if(isNewWord.equals("Y"))
            editor.putString("NEWWORD", "Y").apply()

        if(isCooltem.equals("Y"))
            editor.putString("COOLTEM", "Y").apply()

        if(isPerson.equals("Y"))
            editor.putString("PERSON", "Y").apply()

        if(isJews.equals("Y"))
            editor.putString("JEWS", "Y").apply()

        if(isEtc.equals("Y"))
            editor.putString("ETC", "Y").apply()

        Toast.makeText(this, "테스트성공", Toast.LENGTH_SHORT).show()
    }
}
