package com.example.kangs.guestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kangs.dbmanagertest.R

class CustomAdapter_Chat(private val context: Context, private val ChatModelArrayList: ArrayList<Chat_FoodModel>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return ChatModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return ChatModelArrayList[position]
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
            convertView = inflater.inflate(R.layout.customvieiw_chat, null, true)

            holder.main_image = convertView!!.findViewById(R.id.guest_chat_main_image) as ImageView
            holder.main_name = convertView!!.findViewById(R.id.guest_chat_main_name) as TextView
            holder.main_content = convertView!!.findViewById(R.id.guest_chat_main_content) as TextView
            holder.main_lastdate = convertView!!.findViewById(R.id.guest_chat_main_lastdate) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.main_image!!.setImageResource(ChatModelArrayList[position].getMainImage())
        holder.main_name!!.setText(ChatModelArrayList[position].getMainName())
        holder.main_content!!.setText(ChatModelArrayList[position].getMainContent())
        holder.main_lastdate!!.setText(ChatModelArrayList[position].getMainLastdate())

        return convertView
    }

    private inner class ViewHolder {

        var main_image: ImageView? = null
        var main_name: TextView? = null
        var main_content: TextView? = null
        var main_lastdate: TextView? = null
    }
}