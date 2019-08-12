package com.example.cpt

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_admin_result.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdminResult : AppCompatActivity() {

    var validticket_adapter: ValidTicket_Adapter? = null
    var ticketlist: ArrayList<Ticket> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_result)
        supportActionBar!!.hide()

        init()

        val bundle = intent.extras
        val email: String = bundle?.get("EMAIL").toString()
        val lastname: String = bundle?.get("LASTNAME").toString()

        setButtons()

        val dbHandler = DBHandler(this)
        val myService: ExecutorService = Executors.newFixedThreadPool(1)
        val result = myService.submit(Callable<JsonArray>{
            dbHandler.myInterface.connectionmysql(
                Load_All_ValidTickets(
                    "LOAD_ALL_VALIDTICKETS",
                    email,
                    lastname,
                    System.currentTimeMillis()
                )
            )
        })

        if(result.get().size() > 0) {
            for(jsonobject in result.get()) {
                val expiretimemilli = jsonobject.asJsonObject.get("EXPIRE_TIMEMILLI").toString().replace("\"", "").toLong()
                if(System.currentTimeMillis() < expiretimemilli) {
                    val ticket = Ticket()
                    ticket.tickettype = jsonobject.asJsonObject.get("TICKET_TYPE").toString().replace("\"", "")
                    ticket.agegroup = jsonobject.asJsonObject.get("AGE_GROUP").toString().replace("\"", "")
                    ticket.purchaseddate = jsonobject.asJsonObject.get("PURCHASE_DATE").toString().replace("\"", "")
                    ticket.validateddate = jsonobject.asJsonObject.get("VALIDATE_DATE").toString().replace("\"", "")

                    ticketlist.add(ticket)
                }
            }
            validticket_adapter!!.validTicketList = ticketlist
            validticket_adapter!!.notifyDataSetChanged()
        }
        else {
        }
    }

    private fun init() {
        validticket_adapter = ValidTicket_Adapter(ticketlist)
        rvadminresult_validtickets.layoutManager = LinearLayoutManager(this)
        rvadminresult_validtickets.adapter = validticket_adapter
    }

    private fun setButtons() {
        tvt_adminresult_cancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}


class ValidTicket_Adapter: RecyclerView.Adapter<ValidTicket_Adapter.ValidTicketViewHolder> {

    var validTicketList: ArrayList<Ticket>? = null

    constructor(validticketlist: ArrayList<Ticket>) {
        validTicketList = validticketlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidTicketViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.customview_admin_validtickets, parent, false)
        return ValidTicketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return validTicketList!!.size
    }

    override fun onBindViewHolder(holder: ValidTicketViewHolder, position: Int) {
        val item: Ticket = validTicketList!![position]

        holder.ticketType.setImageResource(when(item.gettickettype()) {
            "Single" -> R.drawable.image_single
            "Daily" -> R.drawable.image_daily
            "Monthly" -> R.drawable.image_monthly
            else -> R.drawable.normaltype
        })

        holder.ageGroup.setText(item.getagegroup())
        holder.purchaseddate.setText("Purchased Date : " + item.getpurchaseddate())
        holder.validateddate.setText("Validated Date : " + item.getvalidateddate())
    }

    class ValidTicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ticketType: ImageView
        var ageGroup: TextView
        var purchaseddate: TextView
        var validateddate: TextView

        init {
            ticketType = itemView.findViewById(R.id.iv_admin_tickettype)
            ageGroup = itemView.findViewById(R.id.tv_admin_agegroup)
            purchaseddate = itemView.findViewById(R.id.tv_admin_purchaseddate)
            validateddate = itemView.findViewById(R.id.tv_admin_validateddate)
        }
    }
}