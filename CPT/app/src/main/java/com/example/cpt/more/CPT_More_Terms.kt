package com.example.cpt.more

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.example.cpt.R
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_cpt__more__terms.*

class CPT_More_Terms : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__more__terms)
        supportActionBar!!.hide()

        tvmore_terms_cancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}