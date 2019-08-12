package com.example.kangs.guestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kangs.dbmanagertest.R

class CustomAdapter_Profile(private val context: Context, private val ProfileModelArrayList: ArrayList<Profile_Model>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getCount(): Int {
        return ProfileModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return ProfileModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.customview_textandimage, null, true)

            holder.main_image = convertView!!.findViewById(R.id.guest_profile_main_icon) as ImageView
            holder.main_name = convertView!!.findViewById(R.id.guest_profile_main_title) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.main_image!!.setImageResource(ProfileModelArrayList[position].getMainImage())
        holder.main_name!!.setText(ProfileModelArrayList[position].getMainName())



        return convertView
    }

    private inner class ViewHolder {

        var main_image: ImageView? = null
        var main_name: TextView? = null
    }
}