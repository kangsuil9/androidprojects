package com.example.kangs.guestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kangs.dbmanagertest.R

class CustomAdapter_Mytrip(private val context: Context, private val imageModelArrayList: ArrayList<Mytrip_FoodModel>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return imageModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.customview_mytriplist, null, true)

            holder.main_city = convertView!!.findViewById(R.id.guest_mytrip_title_cityname) as TextView
            holder.sub_country = convertView!!.findViewById(R.id.guest_mytrip_sub_countryname) as TextView
            holder.sub_city = convertView!!.findViewById(R.id.guest_mytrip_sub_cityname) as TextView
            holder.sub_type = convertView!!.findViewById(R.id.guest_mytrip_sub_type) as TextView
            holder.sub_price = convertView!!.findViewById(R.id.guest_mytrip_sub_price) as TextView
            holder.sub_date = convertView!!.findViewById(R.id.guest_mytrip_sub_date) as TextView
            holder.icon = convertView.findViewById(R.id.guest_mytrip_future_past_icon) as ImageView
            holder.food = convertView.findViewById(R.id.guest_mytrip_sub_food) as ImageView
            holder.profile = convertView.findViewById(R.id.guest_mytrip_sub_profile_picture) as ImageView
            holder.flag = convertView.findViewById(R.id.guest_mytrip_sub_flag) as ImageView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.main_city!!.setText(imageModelArrayList[position].getMainCity())
        holder.sub_country!!.setText(imageModelArrayList[position].getSubCountry())
        holder.sub_city!!.setText(imageModelArrayList[position].getSubCity())
        holder.sub_type!!.setText(imageModelArrayList[position].getSubType())
        holder.sub_price!!.setText(imageModelArrayList[position].getSubPrice())
        holder.sub_date!!.setText(imageModelArrayList[position].getSubDate())
        holder.icon!!.setImageResource(imageModelArrayList[position].getMainIcon())
        holder.food!!.setImageResource(imageModelArrayList[position].getSubFood())
        holder.profile!!.setImageResource(imageModelArrayList[position].getSubProfile())
        holder.flag!!.setImageResource(imageModelArrayList[position].getSubFlag())

        return convertView
    }

    private inner class ViewHolder {

        var main_city: TextView? = null
        var sub_country: TextView? = null
        var sub_city: TextView? = null
        var sub_type: TextView? = null
        var sub_price: TextView? = null
        var sub_date: TextView? = null
        internal var icon: ImageView? = null
        internal var food: ImageView? = null
        internal var profile: ImageView? = null
        internal var flag: ImageView? = null
    }
}