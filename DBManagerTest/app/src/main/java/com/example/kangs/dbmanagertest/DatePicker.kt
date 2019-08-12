package com.example.kangs.dbmanagertest

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

        var year = calender.get(Calendar.YEAR)
        var month = calender.get(Calendar.MONTH)
        var day = calender.get(Calendar.DAY_OF_MONTH)

        var tempDate : String = activity!!.findViewById<TextView>(R.id.txtSignUpDateOfBirth).text.toString()
        var tempList : List<String> = tempDate.split("/".toRegex())

        year = tempList[0].toInt()
        month = tempList[1].toInt() - 1
        day = tempList[2].toInt()

        return DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                this,
                year,
                month,
                day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var temp_month = month+1

        activity!!.findViewById<TextView>(R.id.txtSignUpDateOfBirth).text = "$year/$temp_month/$dayOfMonth".toString()
    }
}