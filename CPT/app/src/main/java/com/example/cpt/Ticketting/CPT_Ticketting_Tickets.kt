package com.example.cpt.Ticketting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cpt.*
import com.example.cpt.MyTicket.CPT_MyTicket
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_cpt__ticketting__tickets.*
import kotlinx.android.synthetic.main.customview_ticket_okvalidate.view.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CPT_Ticketting_Tickets : AppCompatActivity() {

    var ticketlist: ArrayList<Ticket> = arrayListOf()
    var availableticket_adapter: AvailableTicket_Adapter? = null

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__ticketting__tickets)
        supportActionBar!!.hide()

        init()
        setButtons()
        LoadTickets()
    }

    fun LoadTickets() {
        val dbHandler = DBHandler(this)
        val myService: ExecutorService = Executors.newFixedThreadPool(1)
        val result = myService.submit(Callable<JsonArray>{
            dbHandler.myInterface.connectionmysql(Load_Available_Tickets(
                "LOAD_AVAILABLE_TICKETS",
                MainActivity.G.g_email
            ))
        })

        availableticket_adapter!!.availableTicketList = arrayListOf()
        if(result.get().size() > 0) {
            for(jsonobject in result.get()) {
                val ticket = Ticket()
                ticket.sequence = jsonobject.asJsonObject.get("TICKET_SEQUENCE").toString().toInt()
                ticket.tickettype = jsonobject.asJsonObject.get("TICKET_TYPE").toString().replace("\"", "")
                ticket.agegroup = jsonobject.asJsonObject.get("AGE_GROUP").toString().replace("\"", "")
                availableticket_adapter!!.availableTicketList!!.add(ticket)
                availableticket_adapter!!.notifyDataSetChanged()
            }
        }
        else {
            availableticket_adapter!!.notifyDataSetChanged()
        }
    }

    private fun init() {
        availableticket_adapter = AvailableTicket_Adapter(ticketlist)
        rvt_tickets_availableticketlist.layoutManager = LinearLayoutManager(this)
        rvt_tickets_availableticketlist.adapter = availableticket_adapter
    }

    private fun existTicket(ticketType: String): Boolean {
        var exist = false
        for(i in 0..CPT_MyTicket.adapter!!.count-1) {
            if(CPT_MyTicket.adapter!!.ticketList[i].gettickettype().equals(ticketType)) {
                exist = true
                break
            }
        }
        return exist
    }

    private fun setButtons() {
        tvt_tickets_cancel.setOnClickListener {
            onBackPressed()
        }

        availableticket_adapter!!.setOnItemClickListener(object: AvailableTicket_Adapter.OnItemClickListener{
            override fun OnValidateClick(position: Int) {
                val inflater = LayoutInflater.from(this@CPT_Ticketting_Tickets)
                val view: View = inflater.inflate(R.layout.customview_ticket_okvalidate, null)
                val type: String = availableticket_adapter!!.availableTicketList!![position].gettickettype()
                val group: String = availableticket_adapter!!.availableTicketList!![position].getagegroup()

                var ticketimage = 0
                if(type.equals("Single")) {
                    ticketimage = R.drawable.image_single
                }
                else if(type.equals("Daily")) {
                    ticketimage = R.drawable.image_daily
                }
                else if(type.equals("Monthly")) {
                    ticketimage = R.drawable.image_monthly
                }
                view.ivt_tickets_validatetickettype.setImageResource(ticketimage)
                view.tvt_tickets_validateagegroup.setText(group)

                val builder: AlertDialog.Builder = AlertDialog.Builder(this@CPT_Ticketting_Tickets)
                builder.setView(view)
                val customDialog = builder.show()

                view.btnt_tickets_validateconfirm.setOnClickListener {

                    if(existTicket(type)) {
                        Toast.makeText(baseContext, "Ticket is already valid", Toast.LENGTH_SHORT).show()
                        customDialog.dismiss()
                    }
                    else {
                        val ticket = availableticket_adapter!!.availableTicketList!![position]

                        val singletickettime = resources.getString(R.string.SINGLE_TICKET_TIME_MILLS).toLong()
                        val daytime = resources.getString(R.string.DAY_TIME_MILLS).toLong()

                        val cal = Calendar.getInstance()
                        val daytimemillis = (cal.get(Calendar.HOUR_OF_DAY)*1000*60*60) + (cal.get(Calendar.MINUTE)*1000*60) + (cal.get(Calendar.SECOND)*1000)
                        val dailytickettime: Long = daytime - daytimemillis.toLong()

                        val days = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH)
                        val monthlytickettime: Long = (days * daytime) + dailytickettime

                        var expiremilli: Long = 0L
                        if(type.equals("Single")) {
                            expiremilli = System.currentTimeMillis() + singletickettime
                        }
                        else if(type.equals("Daily")) {
                            expiremilli = System.currentTimeMillis() + dailytickettime
                        }
                        else if(type.equals("Monthly")) {
                            expiremilli = System.currentTimeMillis() + monthlytickettime
                        }

                        val dbHandler = DBHandler(this@CPT_Ticketting_Tickets)
                        val myService: ExecutorService = Executors.newFixedThreadPool(1)
                        val result = myService.submit(Callable<String>{
                            dbHandler.myInterface.connectionmysql(Validate_Ticket(
                                "VALIDATE_TICKET",
                                MainActivity.G.g_email,
                                ticket.getsequence(),
                                expiremilli
                            ))
                        })
                        if(result.get().equals("Good")) {
                            if(type == "Single")
                                ticket.timeleft = singletickettime
                            else if(type == "Daily")
                                ticket.timeleft = dailytickettime
                            else if(type == "Monthly")
                                ticket.timeleft = monthlytickettime

                            CPT_MyTicket.adapter!!.addView(ticket)
                            customDialog.dismiss()

                            LoadTickets()
                        }
                    }
                }
                view.btnt_tickets_validatecancel.setOnClickListener {
                    customDialog.dismiss()
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}

class AvailableTicket_Adapter: RecyclerView.Adapter<AvailableTicket_Adapter.AvailableTicketViewHolder> {

    var availableTicketList: ArrayList<Ticket>? = null
    var listener: OnItemClickListener? = null

    constructor(availableticketlist: ArrayList<Ticket>) {
        availableTicketList = availableticketlist
    }

    interface OnItemClickListener {
        fun OnValidateClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTicketViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.customview_ticket_unittickets, parent, false)
        return AvailableTicketViewHolder(view, listener!!)
    }

    override fun getItemCount(): Int {
        return availableTicketList!!.size
    }

    override fun onBindViewHolder(holder: AvailableTicketViewHolder, position: Int) {
        val item: Ticket = availableTicketList!![position]

        holder.ticketType.setImageResource(when(item.gettickettype()) {
            "Single" -> R.drawable.image_single
            "Daily" -> R.drawable.image_daily
            "Monthly" -> R.drawable.image_monthly
            else -> R.drawable.image_single
        })
        holder.ageGroup.setText(item.getagegroup())
    }

    class AvailableTicketViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var ticketType: ImageView
        var ageGroup: TextView
        var validate: Button

        init {
            ticketType = itemView.findViewById(R.id.ivt_tickets_tickettype)
            ageGroup = itemView.findViewById(R.id.tvt_tickets_agegroup)


            validate = itemView.findViewById(R.id.btnt_tickets_validate)
            validate.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        listener.OnValidateClick(position)
                }
            })
        }
    }
}