package com.example.cpt.Ticketting

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.example.cpt.*
import com.example.cpt.MyTicket.CPT_MyTicket
import kotlinx.android.synthetic.main.activity_cpt__ticketting__daily.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CPT_Ticketting_Daily : AppCompatActivity() {

    var parentList = ArrayList<ParentItem_Model>()
    var childList = HashMap<String, ArrayList<ChildItem_Model>>()
    var dailyPassAdapter: CPT_Ticketting_ExpandAdapter? = null
    var ticket = Ticket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__ticketting__daily)
        supportActionBar!!.hide()

        setButtons()
        init()
        setListener()
        update()
    }

    private fun init() {
        val children_agegroup = ArrayList<ChildItem_Model>()
        val children_start = ArrayList<ChildItem_Model>()
        val children_count = ArrayList<ChildItem_Model>()
        val children_payment = ArrayList<ChildItem_Model>()
        ticket = Ticket()

        parentList.add(ParentItem_Model("Age Group", "Adult", R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Validity Starts", "Now",R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Ticket Count", "1",R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Payment Method", "Select Payment Method",R.drawable.arrow_down))

        children_agegroup.add(ChildItem_Model("Adult", "", "10.75 $"))
        children_agegroup.add(ChildItem_Model("Youth", "(Age 6 - 17)", "7.75 $"))
        childList.set(parentList[0].getparentTitle(), children_agegroup)

        children_start.add(ChildItem_Model("Now", "", ""))
        children_start.add(ChildItem_Model("Later", "Buy Only", ""))
        childList.set(parentList[1].getparentTitle(), children_start)

        for(i in 1..10)
            children_count.add(ChildItem_Model("$i", "",""))
        childList.set(parentList[2].getparentTitle(), children_count)

        children_payment.add(ChildItem_Model("Kay's Credit Card", "**** **** **** 2627 (10/21)", ""))
        childList.set(parentList[3].getparentTitle(), children_payment)

        dailyPassAdapter = CPT_Ticketting_ExpandAdapter(this, parentList, childList)
        elvt_daily_buysetting.setAdapter(dailyPassAdapter!!)
    }

    private fun setListener() {
        setExpandableListViewHeight(elvt_daily_buysetting, -1)
        elvt_daily_buysetting.setOnGroupClickListener(object: ExpandableListView.OnGroupClickListener {
            var previousPos: Int = -1
            override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
                if(groupPosition != previousPos && previousPos != -1) {
                    parentList[previousPos].arrow = R.drawable.arrow_down
                    parentList[groupPosition].arrow = R.drawable.arrow_up
                }
                else if(groupPosition == previousPos || previousPos == -1) {
                    if(!parent!!.isGroupExpanded(groupPosition))
                        parentList[groupPosition].arrow = R.drawable.arrow_up
                    else
                        parentList[groupPosition].arrow = R.drawable.arrow_down
                }
                previousPos = groupPosition

                setExpandableListViewHeight(parent!!, groupPosition)
                return false
            }
        })
        elvt_daily_buysetting.setOnGroupExpandListener(object: ExpandableListView.OnGroupExpandListener {
            var previousPos: Int = -1
            override fun onGroupExpand(groupPosition: Int) {
                if(groupPosition != previousPos && previousPos != -1) {
                    elvt_daily_buysetting.collapseGroup(previousPos)
                }
                previousPos = groupPosition
            }

        })
        elvt_daily_buysetting.setOnGroupCollapseListener(object: ExpandableListView.OnGroupCollapseListener {
            override fun onGroupCollapse(groupPosition: Int) {
                dailyPassAdapter!!.parentList[groupPosition].arrow = R.drawable.arrow_down
            }
        })

        elvt_daily_buysetting.setOnChildClickListener(object: ExpandableListView.OnChildClickListener{
            override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                dailyPassAdapter!!.parentList[groupPosition].parentContent = dailyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].childTitle
                dailyPassAdapter!!.notifyDataSetChanged()
                if(groupPosition == 0 || groupPosition == 2)
                    update()
                if( groupPosition == 3 ) {
                    val card = Card(dailyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].getchildTitle(),
                        dailyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].getchildContent())
                    ticket.card = card
                }
                setExpandableListViewHeight(parent!!, groupPosition)
                parent.collapseGroup(groupPosition)
                return false
            }
        })
    }

    private fun update() {
        val agegroup: String = dailyPassAdapter!!.parentList[0].parentContent
        val ticketcount: Int = dailyPassAdapter!!.parentList[2].parentContent.toInt()
        val totalprice: Double = when(agegroup) {
            "Adult" -> ticketcount*10.75
            "Youth" -> ticketcount*7.75
            else -> 0.0
        }

        ticket.tickettype = "Daily"
        ticket.agegroup = agegroup
        ticket.price = when(agegroup) {
            "Adult" -> 10.75
            "Youth" -> 7.75
            else -> 0.0
        }
        tvt_daily_totalprice.setText(totalprice.toString())
    }

    private fun setExpandableListViewHeight(listView: ExpandableListView, groupPosition: Int) {
        val listAdapter: ExpandableListAdapter = listView.expandableListAdapter
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec((listView.width), View.MeasureSpec.UNSPECIFIED)
        var totalHeight: Int = 0
        var view: View? = null
        for(i in 0..listAdapter.groupCount-1) {
            view = listAdapter.getGroupView(i, false, view, listView)
            if(i == 0)
                view.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
            if( ((!listView.isGroupExpanded(groupPosition)) &&  (i == groupPosition)) || ((!listView.isGroupExpanded(i)) && (i == groupPosition)) ) {
                var listItem: View? = null
                for(j in 0.. listAdapter.getChildrenCount(i)-1) {
                    listItem = listAdapter.getChildView(i, j, false, listItem, listView)
                    listItem.layoutParams = ViewGroup.LayoutParams(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    totalHeight += listItem.measuredHeight
                }
            }
        }

        val params : ViewGroup.LayoutParams = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.groupCount-1))
        listView.layoutParams = params
        listView.requestLayout()
    }

    private fun setButtons() {
        tvt_daily_cancel.setOnClickListener {
            onBackPressed()
        }

        btnt_daily_pay.setOnClickListener {
            if(ticket.card!!.getcardname().equals("") || ticket.card!!.getcardnumber().equals("")) {
                val builder = AlertDialog.Builder(this@CPT_Ticketting_Daily)
                builder.setTitle("Select Payment Method")
                    .setMessage("Please Select one of Payment Method to proceed Payment")
                    .setPositiveButton("Confirm", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    }).show()
                return@setOnClickListener
            }
            if(dailyPassAdapter!!.parentList[1].parentContent.equals("Now")) {

                val cal = Calendar.getInstance()
                val daytime = resources.getString(R.string.DAY_TIME_MILLS).toLong()

                val daytimemillis = (cal.get(Calendar.HOUR_OF_DAY)*1000*60*60) + (cal.get(Calendar.MINUTE)*1000*60) + (cal.get(Calendar.SECOND)*1000)
                val dailytickettime: Long = daytime - daytimemillis.toLong()

                ticket.timeleft = dailytickettime
                val dbHandler = DBHandler(this)
                val myService: ExecutorService = Executors.newFixedThreadPool(1)
                val result = myService.submit(Callable<String>{
                    dbHandler.myInterface.connectionmysql(
                        InsertTicket_Now(
                            "PURCHASE_TICKET_NOW",
                            MainActivity.G.g_email,
                            ticket.gettickettype(),
                            ticket.getagegroup(),
                            ticket.getprice(),
                            ticket.card!!.getcardname(),
                            ticket.card!!.getcardnumber(),
                            dailyPassAdapter!!.parentList[2].parentContent.toInt(),
                            System.currentTimeMillis() + dailytickettime
                        )
                    )
                })
                if(result.get().equals("Good")) {
                    for(i in 0..dailyPassAdapter!!.parentList[2].parentContent.toInt()-1) {
                        CPT_MyTicket.adapter!!.addView(ticket)
                    }
                }
            }
            else if(dailyPassAdapter!!.parentList[1].parentContent.equals("Later")) {
                ticket.timeleft = resources.getString(R.string.SINGLE_TICKET_TIME_MILLS).toLong()
                val dbHandler = DBHandler(this)
                val myService: ExecutorService = Executors.newFixedThreadPool(1)
                val result = myService.submit(Callable<String>{
                    dbHandler.myInterface.connectionmysql(
                        InsertTicket_Later(
                            "PURCHASE_TICKET_LATER",
                            MainActivity.G.g_email,
                            ticket.gettickettype(),
                            ticket.getagegroup(),
                            ticket.getprice(),
                            ticket.card!!.getcardname(),
                            ticket.card!!.getcardnumber(),
                            dailyPassAdapter!!.parentList[2].parentContent.toInt()
                        )
                    )
                })
                if(result.get().equals("Good")) {
                    Toast.makeText(this@CPT_Ticketting_Daily, "Purchase has succeeded!", Toast.LENGTH_LONG).show()
                }
            }
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
