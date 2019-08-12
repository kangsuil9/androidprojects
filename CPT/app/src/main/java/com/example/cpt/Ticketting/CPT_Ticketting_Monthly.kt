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
import kotlinx.android.synthetic.main.activity_cpt__ticketting__monthly.*
import java.time.MonthDay
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CPT_Ticketting_Monthly : AppCompatActivity() {

    var parentList = ArrayList<ParentItem_Model>()
    var childList = HashMap<String, ArrayList<ChildItem_Model>>()
    var monthlyPassAdapter: CPT_Ticketting_ExpandAdapter? = null
    val monthname = arrayListOf<String>("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan")
    var ticket = Ticket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__ticketting__monthly)
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
        val children_lowincome = ArrayList<ChildItem_Model>()
        val children_payment = ArrayList<ChildItem_Model>()

        ticket = Ticket()

        val instance = Calendar.getInstance()
        val month = instance.get(Calendar.MONTH)

        parentList.add(ParentItem_Model("Age Group", "Adult", R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Validity Starts", monthname[month],R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Ticket Count", "1",R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Low Income", "None",R.drawable.arrow_down))
        parentList.add(ParentItem_Model("Payment Method", "Select Payment Method",R.drawable.arrow_down))

        children_agegroup.add(ChildItem_Model("Adult", "", "10.75 $"))
        children_agegroup.add(ChildItem_Model("Youth", "(Age 6 - 17)", "7.75 $"))
        childList.set(parentList[0].getparentTitle(), children_agegroup)

        children_start.add(ChildItem_Model(monthname[month], "", ""))
        children_start.add(ChildItem_Model(monthname[month+1], "Only Buy", ""))
        childList.set(parentList[1].getparentTitle(), children_start)

        for(i in 1..10)
            children_count.add(ChildItem_Model("${i}", "",""))

        childList.set(parentList[2].getparentTitle(), children_count)

        children_lowincome.add(ChildItem_Model("None", "", ""))
        children_lowincome.add(ChildItem_Model("A Band", "", ""))
        children_lowincome.add(ChildItem_Model("B Band", "", ""))
        children_lowincome.add(ChildItem_Model("C Band", "", ""))
        childList.set(parentList[3].getparentTitle(), children_lowincome)

        children_payment.add(ChildItem_Model("Kay's Credit Card", "**** **** **** 2627 (10/21)", ""))
        childList.set(parentList[4].getparentTitle(), children_payment)

        monthlyPassAdapter = CPT_Ticketting_ExpandAdapter(this, parentList, childList)
        elvt_monthly_buysetting.setAdapter(monthlyPassAdapter!!)
    }

    private fun setListener() {
        setExpandableListViewHeight(elvt_monthly_buysetting, -1)
        elvt_monthly_buysetting.setOnGroupClickListener(object: ExpandableListView.OnGroupClickListener {
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
        elvt_monthly_buysetting.setOnGroupExpandListener(object: ExpandableListView.OnGroupExpandListener {
            var previousPos: Int = -1
            override fun onGroupExpand(groupPosition: Int) {
                if(groupPosition != previousPos && previousPos != -1) {
                    elvt_monthly_buysetting.collapseGroup(previousPos)
                }
                previousPos = groupPosition
            }

        })
        elvt_monthly_buysetting.setOnGroupCollapseListener(object: ExpandableListView.OnGroupCollapseListener {
            override fun onGroupCollapse(groupPosition: Int) {
                monthlyPassAdapter!!.parentList[groupPosition].arrow = R.drawable.arrow_down
            }
        })

        elvt_monthly_buysetting.setOnChildClickListener(object: ExpandableListView.OnChildClickListener{
            override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                monthlyPassAdapter!!.parentList[groupPosition].parentContent = monthlyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].childTitle
                monthlyPassAdapter!!.notifyDataSetChanged()
                if(groupPosition == 0 || groupPosition == 2 || groupPosition == 3)
                    update()
                if( groupPosition == 4 ) {
                    val card = Card(monthlyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].getchildTitle(),
                        monthlyPassAdapter!!.childList.get(parentList[groupPosition].getparentTitle())!![childPosition].getchildContent())
                    ticket.card = card
                }
                setExpandableListViewHeight(parent!!, groupPosition)
                parent.collapseGroup(groupPosition)
                return false
            }
        })
    }

    private fun update() {
        val agegroup: String = monthlyPassAdapter!!.parentList[0].parentContent
        val ticketcount: Int = monthlyPassAdapter!!.parentList[2].parentContent.toInt()
        val lowincomelevel: String = monthlyPassAdapter!!.parentList[3].parentContent.substring(0, 1)
        val totalprice: Double = when(agegroup) {
            "Adult" -> {
                when(lowincomelevel) {
                    "N" -> ticketcount*106.0
                    "A" -> ticketcount*5.30
                    "B" -> ticketcount*37.10
                    "C" -> ticketcount*53.0
                    else -> 0.0
                }
            }
            "Youth" -> {
                ticketcount*75.0
            }
            else -> 0.0
        }

        ticket.tickettype = "Monthly"
        ticket.agegroup = agegroup
        ticket.price = when(agegroup) {
            "Adult" -> {
                when(lowincomelevel) {
                    "N" -> 106.0
                    "A" -> 5.30
                    "B" -> 37.10
                    "C" -> 53.0
                    else -> 0.0
                }
            }
            "Youth" -> {
                75.0
            }
            else -> 0.0
        }
        tvt_monthly_totalprice.setText(totalprice.toString())
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
        tvt_monthly_cancel.setOnClickListener {
            onBackPressed()
        }

        btnt_monthly_pay.setOnClickListener {
            if(ticket.card!!.getcardname().equals("") || ticket.card!!.getcardnumber().equals("")) {
                val builder = AlertDialog.Builder(this@CPT_Ticketting_Monthly)
                builder.setTitle("Select Payment Method")
                    .setMessage("Please Select one of Payment Method to proceed Payment")
                    .setPositiveButton("Confirm", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    }).show()
                return@setOnClickListener
            }
            if(monthlyPassAdapter!!.parentList[1].parentContent.equals(monthname[Calendar.getInstance().get(Calendar.MONTH)])) {

                val cal = Calendar.getInstance()
                val daytime = resources.getString(R.string.DAY_TIME_MILLS).toLong()
                val daytimemillis = (cal.get(Calendar.HOUR_OF_DAY)*1000*60*60) + (cal.get(Calendar.MINUTE)*1000*60) + (cal.get(Calendar.SECOND)*1000)
                val dailytickettime: Long = daytime - daytimemillis.toLong()

                val days = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH)
                val monthlytickettime: Long = (days * daytime) + dailytickettime

                ticket.timeleft = monthlytickettime
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
                            monthlyPassAdapter!!.parentList[2].parentContent.toInt(),
                            System.currentTimeMillis() + monthlytickettime
                        )
                    )
                })
                if(result.get().equals("Good")) {
                    for(i in 0..monthlyPassAdapter!!.parentList[2].parentContent.toInt()-1) {
                        CPT_MyTicket.adapter!!.addView(ticket)
                    }
                }
            }
            else if(monthlyPassAdapter!!.parentList[1].parentContent.equals(monthname[Calendar.getInstance().get(Calendar.MONTH)+1])) {
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
                            monthlyPassAdapter!!.parentList[2].parentContent.toInt()
                        )
                    )
                })
                if(result.get().equals("Good")) {
                    Toast.makeText(this@CPT_Ticketting_Monthly, "Purchase has succeeded!", Toast.LENGTH_LONG).show()
                }
            }
            onBackPressed()        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
