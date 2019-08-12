package com.example.cpt

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.cpt.MyTicket.CPT_MyTicket
import com.example.cpt.Search.CPT_Search
import com.example.cpt.Ticketting.CPT_Ticketting
import com.example.cpt.more.CPT_More
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    object G {
        var g_email = ""
        var g_agegroup = ""
        var g_firstname = ""
        var g_middlename = ""
        var g_lastname = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        init()

        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        vpmain_custompager.adapter = fragmentAdapter
        tabmain_tab.setupWithViewPager(vpmain_custompager)
    }

    override fun onBackPressed() {
    }

    fun init() {
        val token = getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
        if(token.getString(resources.getString(R.string.SIGNIN_EMAIL), null) != null) {
            G.g_email = token.getString(resources.getString(R.string.SIGNIN_EMAIL), "")!!
            G.g_agegroup = token.getString(resources.getString(R.string.SIGNIN_AGEGROUP), "")!!
            G.g_firstname = token.getString(resources.getString(R.string.SIGNIN_FIRSTNAME), "")!!
            G.g_middlename = token.getString(resources.getString(R.string.SIGNIN_MIDDLENAME), "")!!
            G.g_lastname = token.getString(resources.getString(R.string.SIGNIN_LASTNAME), "")!!
        }
    }
}

class PagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CPT_MyTicket()
            1 -> CPT_Ticketting()
            2 -> CPT_Search()
            else -> CPT_More()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "TICKET"
            1 -> "BUY"
            2 -> "SEARCH"
            else -> "MORE"
        }
    }
}

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}