package com.example.kangs.guestapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PaserAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Guest_Search()
            1 -> Guest_MyTrip()
            2 -> Guest_Chat()
            3 -> Guest_Like()
            else -> Guest_Profile()
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "SEARCH"
            1 -> "MY RRIP"
            2 -> "CHAT"
            3 -> "LIKE"
            else -> "PROFILE"
        }
    }
}
