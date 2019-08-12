package com.example.cpt.Ticketting


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cpt.R
import kotlinx.android.synthetic.main.fragment_cpt__ticketting.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CPT_Ticketting : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpt__ticketting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        btnt_singleticket.setOnClickListener {
            menu("singleticket")
        }
        btnt_dailypass.setOnClickListener {
            menu("dailypass")
        }
        btnt_monthlypass.setOnClickListener {
            menu("monthlypass")
        }
        btnt_tickets.setOnClickListener {
            menu("tickets")
        }
        btnt_purchasehistory.setOnClickListener {
            menu("purchasehistory")
        }
    }

    private fun menu(item: String) {
        var intent: Intent
        when(item) {
            "singleticket" -> intent = Intent(activity, CPT_Ticketting_Single::class.java)
            "dailypass" -> intent = Intent(activity, CPT_Ticketting_Daily::class.java)
            "monthlypass" -> intent = Intent(activity, CPT_Ticketting_Monthly::class.java)
            "tickets" -> intent = Intent(activity, CPT_Ticketting_Tickets::class.java)
            "purchasehistory" -> intent = Intent(activity, CPT_Ticketting_history::class.java)
            else -> return
        }
        startActivity(intent)
        activity!!.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }
}
