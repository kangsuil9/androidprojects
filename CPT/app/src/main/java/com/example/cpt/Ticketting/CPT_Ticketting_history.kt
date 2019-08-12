package com.example.cpt.Ticketting

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions
import com.example.cpt.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_cpt__ticketting_history.*
import kotlinx.android.synthetic.main.customview_ticket_receipt.view.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class CPT_Ticketting_history : AppCompatActivity() {

    var purchasedticket_adapter: PurchasedTicket_Adapter? = null
    var ticketlist: ArrayList<Ticket> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__ticketting_history)
        supportActionBar!!.hide()

        init()
        setButtons()
        LoadData()
    }

    private fun LoadData() {
        val dbHandler = DBHandler(this)
        val myService: ExecutorService = Executors.newFixedThreadPool(1)
        val result = myService.submit(Callable<JsonArray>{
            dbHandler.myInterface.connectionmysql(Load_Available_Tickets(
                "LOAD_ALL_TICKETS",
                MainActivity.G.g_email
            ))
        })
        purchasedticket_adapter!!.purchasedTicketList = arrayListOf()
        if(result.get().size() > 0) {
            for(jsonobject in result.get()) {
                val ticket = Ticket()
                ticket.sequence = jsonobject.asJsonObject.get("TICKET_SEQUENCE").toString().toInt()
                ticket.tickettype = jsonobject.asJsonObject.get("TICKET_TYPE").toString().replace("\"", "")
                ticket.agegroup = jsonobject.asJsonObject.get("AGE_GROUP").toString().replace("\"", "")
                ticket.card!!.cardname = jsonobject.asJsonObject.get("CARD_NAME").toString().replace("\"", "")
                ticket.card!!.cardnumber = jsonobject.asJsonObject.get("CARD_NUMBER").toString().replace("\"", "")
                ticket.price = jsonobject.asJsonObject.get("TICKET_PRICE").toString().toDouble()
                ticket.purchaseddate = jsonobject.asJsonObject.get("PURCHASE_DATE").toString().replace("\"", "")
                ticket.validateddate = jsonobject.asJsonObject.get("VALIDATE_DATE").toString().replace("\"", "")
                ticket.ticketstate = jsonobject.asJsonObject.get("TICKET_STATE").toString().replace("\"", "")
                purchasedticket_adapter!!.purchasedTicketList!!.add(ticket)
            }
            purchasedticket_adapter!!.notifyDataSetChanged()
        }
    }

    private fun init() {
        purchasedticket_adapter = PurchasedTicket_Adapter(this, ticketlist)
        rvt_history_purchasedticketlist.layoutManager = LinearLayoutManager(this)
        rvt_history_purchasedticketlist.adapter = purchasedticket_adapter
    }

    private fun setButtons() {
        tvt_history_cancel.setOnClickListener {
            onBackPressed()
        }

        purchasedticket_adapter!!.setOnItemClickListener(object: PurchasedTicket_Adapter.OnItemClickListener{
            override fun OnItemClick(position: Int) {
                val inflater = LayoutInflater.from(this@CPT_Ticketting_history)
                val view: View = inflater.inflate(R.layout.customview_ticket_receipt, null)

                view.tvt_history_receipttickettype.setText(purchasedticket_adapter!!.purchasedTicketList!![position].gettickettype())
                view.tvt_history_receiptticketsequence.setText(purchasedticket_adapter!!.purchasedTicketList!![position].getsequence().toString())
                view.tvt_history_receiptagegroup.setText(purchasedticket_adapter!!.purchasedTicketList!![position].getagegroup())
                view.tvt_history_receiptcardname.setText(purchasedticket_adapter!!.purchasedTicketList!![position].card!!.getcardname())
                view.tvt_history_receiptpcardnumber.setText(purchasedticket_adapter!!.purchasedTicketList!![position].card!!.getcardnumber())
                view.tvt_history_receiptprice.setText(purchasedticket_adapter!!.purchasedTicketList!![position].getprice().toString())
                view.tvt_history_receiptdateofbought.setText(purchasedticket_adapter!!.purchasedTicketList!![position].getpurchaseddate())
                view.tvt_history_receiptdateofvalidation.setText(purchasedticket_adapter!!.purchasedTicketList!![position].getvalidateddate())
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@CPT_Ticketting_history)
                builder.setView(view)
                val customDialog = builder.show()

                view.btnt_history_receiptok.setOnClickListener {
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

class PurchasedTicket_Adapter: RecyclerView.Adapter<PurchasedTicket_Adapter.PurchasedTicketViewHolder> {

    var purchasedTicketList: ArrayList<Ticket>? = null
    var listener: OnItemClickListener? = null
    val context: Context

    constructor(context: Context, purchasedticketlist: ArrayList<Ticket>) {
        this.context = context
        purchasedTicketList = purchasedticketlist
    }

    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchasedTicketViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.customview_ticket_unittickets_history, parent, false)
        return PurchasedTicketViewHolder(view, listener!!)
    }

    override fun getItemCount(): Int {
        return purchasedTicketList!!.size
    }

    override fun onBindViewHolder(holder: PurchasedTicketViewHolder, position: Int) {
        val item: Ticket = purchasedTicketList!![position]

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

    class PurchasedTicketViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var ticketType: ImageView
        var ageGroup: TextView
        var purchaseddate: TextView
        var validateddate: TextView


        init {
            ticketType = itemView.findViewById(R.id.ivt_tickets_history_tickettype)
            ageGroup = itemView.findViewById(R.id.tvt_tickets_history_agegroup)
            purchaseddate = itemView.findViewById(R.id.tvt_tickets_history_purchaseddate)
            validateddate = itemView.findViewById(R.id.tvt_tickets_history_validateddate)

            itemView.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        listener.OnItemClick(position)
                }
            })
        }
    }
}