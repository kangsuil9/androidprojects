package com.example.cpt.Search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.cpt.PagerAdapter
import com.example.cpt.R
import kotlinx.android.synthetic.main.activity_cpt__search__stop.*

class CPT_Search_Stop : AppCompatActivity() {

    lateinit var route: RouteStop_Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__search__stop)
        supportActionBar!!.hide()

        init()
        setButtons()
    }

    private fun init() {
        val fragmentAdapter = Stops_PagerAdapter(supportFragmentManager)
        vps_stop_viewpager.adapter = fragmentAdapter

        tabs_stop_tab.setupWithViewPager(vps_stop_viewpager)

        val bundle = intent.extras
        val name = bundle?.getString("ROUTESTOPNUMBER", " ")
        val type = bundle?.getString("ROUTESTOPTYPE", " ")
        route = RouteStop_Model(name!!, type!!)

        tvs_stop_title.setText(route.getname())

        if(isRouteStopExist(route)) {
            ivs_stop_like.setImageResource(R.drawable.heart_fill)
        }
        else {
            ivs_stop_like.setImageResource(R.drawable.heart_edge)
        }
    }

    private fun setButtons() {
        tvs_stop_cancel.setOnClickListener {
            onBackPressed()
        }

        ivs_stop_like.setOnClickListener {
            if(isRouteStopExist(route)) {
                CPT_Search.likelist.removeAt(getPositionOfItem(route))
                ivs_stop_like.setImageResource(R.drawable.heart_edge)
                CPT_Search.likeadapter!!.notifyDataSetChanged()
                CPT_Search.likeadapter!!.updateList()
            }
            else {
                CPT_Search.likelist.add(route)
                ivs_stop_like.setImageResource(R.drawable.heart_fill)
                CPT_Search.likeadapter!!.notifyDataSetChanged()
                CPT_Search.likeadapter!!.updateList()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun isRouteStopExist(item: RouteStop_Model): Boolean {
        for(i in 0..CPT_Search.likelist.size-1) {
            if(CPT_Search.likelist[i].getname().equals(item.getname()))
                return true
        }
        return false
    }

    private fun getPositionOfItem(item: RouteStop_Model): Int {
        var position: Int = 0
        for(i in 0..CPT_Search.likelist.size-1) {
            if(CPT_Search.likelist[i].getname().equals(item.getname()))
                position = i
        }
        return position
    }
}


class Stops_PagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CPT_Search_Stop_Realtime()
            else -> CPT_Search_Stop_Timetable()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "REAL TIME"
            else -> "TIME TABLE"
        }
    }
}