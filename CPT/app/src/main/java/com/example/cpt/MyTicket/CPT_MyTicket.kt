package com.example.cpt.MyTicket


import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cpt.R
import com.example.cpt.Ticket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.customview_mt_tickets.*
import kotlinx.android.synthetic.main.fragment_cpt__my_ticket.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CPT_MyTicket : Fragment() {
    var ticketList: ArrayList<Ticket> = arrayListOf()

    companion object {
        var adapter: TicketsPagerAdapter? = null
        var mycontext: Context? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_cpt__my_ticket, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setButtons()
    }


    private fun init() {
        mycontext = activity!!.baseContext
        ticketList = ArrayList()

        adapter = TicketsPagerAdapter(activity!!.baseContext, vpmt_tickets, ticketList)
        vpmt_tickets!!.adapter = adapter
    }

    private fun setButtons() {
    }

    override fun onResume() {
        super.onResume()

        val token = activity!!.baseContext.getSharedPreferences(R.string.TICKET_LIST.toString(), Context.MODE_PRIVATE)

        if(token.getString(R.string.TICKET_LIST.toString(), " ") != " " && token.getLong(R.string.TICKET_CURRENTTIME.toString(), 0) != 0L) {

            val jsonticketlist = token.getString(R.string.TICKET_LIST.toString(), " ")

            val gson = Gson()
            val type = object: TypeToken<ArrayList<Ticket>>() {}.type
            ticketList = ArrayList()
            val tempTicket: ArrayList<Ticket> = gson.fromJson<ArrayList<Ticket>>(jsonticketlist, type)

            val timegap = System.currentTimeMillis() - token.getLong(R.string.TICKET_CURRENTTIME.toString(), 0)
            for(i in 0..tempTicket.size-1) {
                if( (tempTicket[i].gettimeleft() - timegap) > 0) {
                    tempTicket[i].timeleft = tempTicket[i].gettimeleft() - timegap
                    ticketList.add(tempTicket[i])
                }
            }

            adapter = TicketsPagerAdapter(activity!!.baseContext,vpmt_tickets, ticketList)
            vpmt_tickets!!.adapter = adapter
        }
        else {
            init()
        }
    }

    override fun onStop() {
        super.onStop()
        val token = activity!!.baseContext.getSharedPreferences(R.string.TICKET_LIST.toString(), Context.MODE_PRIVATE)
        val editor = token.edit()

        val gson = Gson()
        val jsonList: String = gson.toJson(adapter!!.ticketList)
        editor.putString(R.string.TICKET_LIST.toString(), jsonList).apply()
        editor.putLong(R.string.TICKET_CURRENTTIME.toString(), System.currentTimeMillis()).apply()
    }
}

class TicketsPagerAdapter : PagerAdapter {

    var DAY_TIME_MILLS: Long = 0L

    var context : Context
    var ticketList : ArrayList<Ticket>
    var viewpager: ViewPager
    lateinit var inflater: LayoutInflater

    var countdowntimer: CountDownTimer? = null

    constructor(context: Context, viewpager: ViewPager,ticketList: ArrayList<Ticket>) : super() {
        this.context = context
        this.ticketList = ticketList
        this.DAY_TIME_MILLS = context.resources.getString(R.string.DAY_TIME_MILLS).toLong()
        this.viewpager = viewpager
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun getCount(): Int {
        return ticketList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val ivt_mt_tickets: ImageView
        val tv_mt_timeleft: TextView
        val tv_mt_timeleft_time: TextView
        val btn_mt_extension: Button

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.customview_mt_tickets, container, false)

        ivt_mt_tickets = view.findViewById(R.id.ivmt_tickets)
        if(ticketList[position].gettickettype().equals("Single"))
            ivt_mt_tickets.setBackgroundResource(R.drawable.s_animation)
        if(ticketList[position].gettickettype().equals("Daily"))
            ivt_mt_tickets.setBackgroundResource(R.drawable.d_animation)
        if(ticketList[position].gettickettype().equals("Monthly"))
            ivt_mt_tickets.setBackgroundResource(R.drawable.m_animation)
        val ticketAnimation: AnimationDrawable = ivt_mt_tickets.background as AnimationDrawable
        ticketAnimation.start()

        tv_mt_timeleft = view.findViewById(R.id.tvmt_timeleft)
        tv_mt_timeleft_time = view.findViewById(R.id.tvmt_timeleft_time)
        btn_mt_extension = view.findViewById(R.id.btnmt_extension)

        if( ticketList[position].gettickettype().equals("Single") ) {
            countdowntimer = object: CountDownTimer(ticketList[position].gettimeleft(), 1000) {
                override fun onFinish() {
                    container.removeView(view)
                    destroyItem(container, position, view)
                    ticketList.removeAt(position)
                    notifyDataSetChanged()
                }
                override fun onTick(millisUntilFinished: Long) {
                    ticketList[position].timeleft = millisUntilFinished
                    val hours = ticketList[position].gettimeleft() / 1000 / 60 / 60
                    val minutes = (ticketList[position].gettimeleft() / 1000 / 60) % 60
                    val seconds = ticketList[position].gettimeleft() / 1000 % 60
                    val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds)
                    tv_mt_timeleft.setText(timeLeftFormatted)
                }
            }.start()
        }
        else if( ticketList[position].gettickettype().equals("Daily") ) {
            btn_mt_extension.layoutParams.height = 0
            countdowntimer = object: CountDownTimer(ticketList[position].gettimeleft(), 1000) {
                override fun onFinish() {
                    destroyItem(container, position, view)
                    ticketList.removeAt(position)
                    notifyDataSetChanged()

                    //removeView(position)
                    //ticketList.removeAt(position)
                    //notifyDataSetChanged()
                }
                override fun onTick(millisUntilFinished: Long) {
                    ticketList[position].timeleft = millisUntilFinished
                    val hours = ticketList[position].gettimeleft() / 1000 / 60 / 60
                    val minutes = (ticketList[position].gettimeleft() / 1000 / 60) % 60
                    val seconds = ticketList[position].gettimeleft() / 1000 % 60
                    val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds)
                    tv_mt_timeleft.setText(timeLeftFormatted)
                }
            }.start()
        }
        else if( ticketList[position].gettickettype().equals("Monthly") ) {
            btn_mt_extension.layoutParams.height = 0
            val cal = Calendar.getInstance()
            val month = cal.get(Calendar.MONTH) + 1
            tv_mt_timeleft_time.setText("Expire Date : " + cal.get(Calendar.YEAR).toString() + "/" + month + "/" + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + " 24:00")
            countdowntimer = object: CountDownTimer(ticketList[position].gettimeleft(), 1000) {
                override fun onFinish() {
                    container.removeView(view)
                    destroyItem(container, position, view)
                    ticketList.removeAt(position)
                    notifyDataSetChanged()
                }
                override fun onTick(millisUntilFinished: Long) {
                    ticketList[position].timeleft = millisUntilFinished
                    val days = ticketList[position].gettimeleft() / DAY_TIME_MILLS
                    val hours = (ticketList[position].gettimeleft() % DAY_TIME_MILLS) / 1000 / 60 / 60
                    val minutes = ((ticketList[position].gettimeleft() % DAY_TIME_MILLS) / 1000 / 60) % 60
                    val seconds = (ticketList[position].gettimeleft() % DAY_TIME_MILLS) / 1000 % 60
                    val timeLeftFormatted = String.format(Locale.getDefault(), "%d Day %02d:%02d:%02d",days, hours, minutes, seconds)
                    tv_mt_timeleft.setText(timeLeftFormatted)
                }
            }.start()
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    fun addView(ticket: Ticket) {
        ticketList.add(ticket)
        notifyDataSetChanged()

        val token = context.getSharedPreferences(R.string.TICKET_LIST.toString(), Context.MODE_PRIVATE)
        val editor = token.edit()

        val gson = Gson()
        val jsonList: String = gson.toJson(ticketList)
        editor.putString(R.string.TICKET_LIST.toString(), jsonList).apply()
        editor.putLong(R.string.TICKET_CURRENTTIME.toString(), System.currentTimeMillis()).apply()
    }

    fun removeView(position: Int) {
        ticketList.remove(ticketList[position])
        notifyDataSetChanged()
    }
}