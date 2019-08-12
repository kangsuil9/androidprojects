package com.example.cpt.Search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.cpt.R
import com.example.cpt.R.layout.activity_cpt__search__route
import kotlinx.android.synthetic.main.activity_cpt__search__route.*

class CPT_Search_Route : AppCompatActivity() {

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    var adapter: ArrayAdapter<String>? = null
    lateinit var route: RouteStop_Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_cpt__search__route)
        supportActionBar!!.hide()

        init()
        setButtons()
    }


    private fun init() {

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.route4stops))
        lvs_route_stopsofroute.adapter = adapter
        bottomSheetBehavior = BottomSheetBehavior.from(lls_route_bottomsheet)

        tvs_route_weekdayfirsttime.setText("05:30 AM")
        tvs_route_weekdaylasttime.setText("11:30 PM")
        tvs_route_weekendfirsttime.setText("06:30 AM")
        tvs_route_weekendlasttime.setText("11:00 PM")

        val bundle = intent.extras
        val name = bundle?.getString("ROUTESTOPNUMBER", " ")
        val type = bundle?.getString("ROUTESTOPTYPE", " ")
        route = RouteStop_Model(name!!, type!!)

        tvs_route_title.setText(route.getname())

        if(isRouteStopExist(route)) {
            ivs_route_like.setImageResource(R.drawable.heart_fill)
        }
        else {
            ivs_route_like.setImageResource(R.drawable.heart_edge)
        }
    }


    private fun setButtons() {
        tvs_route_cancel.setOnClickListener {
            onBackPressed()
        }

        // allows listview to drag back up in BottomSheet
        lvs_route_stopsofroute.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event!!.action) {
                    MotionEvent.ACTION_DOWN -> v!!.parent.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_UP -> v!!.parent.requestDisallowInterceptTouchEvent(false)
                }
                v!!.onTouchEvent(event)
                return true
            }
        })

        ivs_route_like.setOnClickListener {
            if(isRouteStopExist(route)) {
                CPT_Search.likelist.removeAt(getPositionOfItem(route))
                ivs_route_like.setImageResource(R.drawable.heart_edge)
                CPT_Search.likeadapter!!.notifyDataSetChanged()
                CPT_Search.likeadapter!!.updateList()
            }
            else {
                CPT_Search.likelist.add(route)
                ivs_route_like.setImageResource(R.drawable.heart_fill)
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

    private fun listattop(): Boolean {
        if(lvs_route_stopsofroute.childCount == 0)
            return true
        return (lvs_route_stopsofroute.getChildAt(0).top == 0 && lvs_route_stopsofroute.firstVisiblePosition == 0)
    }
}
