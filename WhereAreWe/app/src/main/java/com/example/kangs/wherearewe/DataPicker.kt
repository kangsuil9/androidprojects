package com.example.kangs.wherearewe

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class DatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var calender : Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calender = Calendar.getInstance()

        var tempDate : String = activity!!.findViewById<TextView>(R.id.tvLoginDateOfBirth).text.toString()
        var tempList : List<String> = tempDate.split("-".toRegex())

        var year = tempList[0].toInt()
        var month = tempList[1].toInt() - 1
        var day = tempList[2].toInt()

        return DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                this,
                year,
                month,
                day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var temp_month : String = (month+1).toString().padStart(2, '0')

        activity!!.findViewById<TextView>(R.id.tvLoginDateOfBirth).text = "$year-$temp_month-$dayOfMonth".toString()
    }
}