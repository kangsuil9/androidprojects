package com.example.kangs.guestapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.fragment_guest__search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Guest_Search : Fragment() {

    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private val myImageList = intArrayOf(R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp,
            R.drawable.ic_check_box_outline_blank_black_24dp)
    private val myImageNameList = arrayOf("Benz", "Bike", "Car", "Carrera", "Ferrari", "Harly", "Lamborghini", "Silver")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guest__search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageModelArrayList = populateList()
        var customAdapter_Food :CustomAdapter_Food = CustomAdapter_Food(view!!.context, imageModelArrayList!!)
        lvGuestSearchFood.adapter = customAdapter_Food

        btnGuestSearchDate.setOnClickListener {
            selectDate()
        }
        btnGuestSearchGuest.setOnClickListener {
            selectGuest()
        }
        btnGuestSearchType.setOnClickListener {
            selectType()
        }
    }

    private fun selectType() {

    }

    private fun selectGuest() {

    }

    private fun selectDate() {

    }

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..7) {
            val imageModel = ImageModel()
            imageModel.setNames(myImageNameList[i])
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }
        return list
    }
}
