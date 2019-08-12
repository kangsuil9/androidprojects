package com.example.kangs.guestapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.fragment_guest__my_trip.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Guest_MyTrip : Fragment() {
    private var aMytrip_FoodModel : ArrayList<Mytrip_FoodModel>? = null

    private val aMainCityList = arrayOf("Suwon", "Seoul", "Paris", "Calgary", "Edmonton", "Vancouver", "Yesan", "Sapkyo", "Seosan", "Busan")
    private val aSubCountryList = arrayOf("Korea", "Korea", "France", "Canada", "Canada", "Canada", "Korea", "Korea", "Korea", "Korea")
    private val aSubCityList = arrayOf("Suwon", "Seoul", "Paris", "Calgary", "Edmonton", "Vancouver", "Yesan", "Sapkyo", "Seosan", "Busan")
    private val aSubTypeList = arrayOf("Homemade", "GroupParty", "Homemade", "Homemade", "Homemade", "Homemade", "Homemade", "Homemade", "Homemade", "Homemade")
    private val aSubPriceList = arrayOf("\$5", "\$10", "\$5", "\$5", "\$5", "\$19", "\$5", "\$5", "\$5", "\$5")
    private val aSubDateList = arrayOf("2019-01-20", "2019-01-22", "2019-01-20", "2019-01-20", "2019-01-20", "2019-01-20", "2019-01-20", "2019-01-20", "2019-01-20", "2019-01-20")

    private val aMainIcon = intArrayOf(R.drawable.dot, R.drawable.circle, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot)
    private val aSubFood = intArrayOf(R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot)
    private val aSubProfile = intArrayOf(R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle)
    private val aSubFlag = intArrayOf(R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle, R.drawable.circle)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest__my_trip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        aMytrip_FoodModel = populateList()
        var customeAdapter_Mytrip :CustomAdapter_Mytrip = CustomAdapter_Mytrip(view!!.context, aMytrip_FoodModel!!)
        lvGuestMytrip.adapter = customeAdapter_Mytrip
    }

    private fun populateList(): ArrayList<Mytrip_FoodModel> {
        val list = ArrayList<Mytrip_FoodModel>()

        for (i in 0..9) {
            val Mytrip_FoodModel = Mytrip_FoodModel()
            Mytrip_FoodModel.setMainCity(aMainCityList[i])
            Mytrip_FoodModel.setSubCountry(aSubCountryList[i])
            Mytrip_FoodModel.setSubCity(aSubCityList[i])
            Mytrip_FoodModel.setSubType(aSubTypeList[i])
            Mytrip_FoodModel.setSubPrice(aSubPriceList[i])
            Mytrip_FoodModel.setSubDate(aSubDateList[i])
            Mytrip_FoodModel.setMainIcon(aMainIcon[i])
            Mytrip_FoodModel.setSubFood(aSubFood[i])
            Mytrip_FoodModel.setSubProfile(aSubProfile[i])
            Mytrip_FoodModel.setSubFlag(aSubFlag[i])
            list.add(Mytrip_FoodModel)
        }
        return list
    }
}