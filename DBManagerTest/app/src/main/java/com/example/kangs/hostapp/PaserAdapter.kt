package com.example.kangs.hostapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PaserAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Host_Schedule()
            1 -> Host_MyFood()
            2 -> Host_Chat()
            3 -> Host_Summary()
            else -> Host_Profile()
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "SCHEDULE"
            1 -> "MY FOOD"
            2 -> "CHAT"
            3 -> "SUMMARY"
            else -> "PROFILE"
        }
    }
}
