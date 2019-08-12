package com.example.kangs.guestapp


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.fragment_guest__profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Guest_Profile : Fragment() {

    private var aProfile_Model : ArrayList<Profile_Model>? = null

    private val aMainImage = intArrayOf(R.drawable.dot, R.drawable.circle, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot)
    private val aMainNameList = arrayOf("Notifications",
            "Invite friends",
            "Refer a host",
            "Credits & coupons",
            "Switch to hosting",
            "Host an experience",
            "Settings",
            "Get help",
            "List your space",
            "Give us feedback")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest__profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        guest_profile_main_profilepicture.setImageResource(R.drawable.dot)
        guest_profile_main_name.setText("Lilly")
        guest_profile_main_subname.setText("View and edit profile")

        aProfile_Model = populateList()
        var customeAdapter_Profile :CustomAdapter_Profile = CustomAdapter_Profile(view!!.context, aProfile_Model!!)
        lvGuestProfile.adapter = customeAdapter_Profile

        lvGuestProfile.setOnItemClickListener {_, _, position, id ->
            val pm : Profile_Model = lvGuestProfile.getItemAtPosition(position) as Profile_Model
            val title = pm.main_name
            if(title.equals("Switch to hosting")) {
                val intent = Intent(activity, com.example.kangs.hostapp.HostAppActivity::class.java)
                startActivity(intent)
                //val intent = Intent(activity, com.example.kangs.hostapp.HostAppActivity::class.java)
                //startActivity(intent)
            }
        }
    }

    private fun populateList(): ArrayList<Profile_Model> {
        val list = ArrayList<Profile_Model>()

        for (i in 0..9) {
            val Profile_Model = Profile_Model()
            Profile_Model.setMainImage(aMainImage[i])
            Profile_Model.setMainName(aMainNameList[i])
            list.add(Profile_Model)
        }
        return list
    }

}
