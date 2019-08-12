package com.example.cpt.Search


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.example.cpt.R
import kotlinx.android.synthetic.main.fragment_cpt__search__stop__realtime.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CPT_Search_Stop_Realtime : Fragment() {

    var realtimerouteAdapter: RealtimeRoute_Adapter? = null
    var realtimeroutelist: ArrayList<RealtimeRoute_Model> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpt__search__stop__realtime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        for(i in 0..5) {
            var temp = RealtimeRoute_Model("5", i*3)
            realtimeroutelist.add(temp)
        }

        realtimerouteAdapter = RealtimeRoute_Adapter(activity!!.baseContext, realtimeroutelist)
        lvs_stop_realtime_routelist.adapter = realtimerouteAdapter
    }
}

class RealtimeRoute_Model(var routename: String, var timeleft: Int) {
    fun getroutename(): String {
        return routename
    }
    fun gettimeleft(): Int {
        return timeleft
    }
}

class RealtimeRoute_Adapter (context: Context, var realtimeroutelist: ArrayList<RealtimeRoute_Model>): ArrayAdapter<RealtimeRoute_Model>(context, R.layout.customview_search_realtimeroutes, realtimeroutelist) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.customview_search_realtimeroutes, parent, false)
        val routename = view.findViewById<View>(R.id.tvs_stop_realtime_route) as TextView
        val timeleft = view.findViewById<View>(R.id.tvs_stop_realtime_timeleft) as TextView

        routename.setText(realtimeroutelist[position].getroutename())
        timeleft.setText(realtimeroutelist[position].gettimeleft().toString())
        return view
    }
}