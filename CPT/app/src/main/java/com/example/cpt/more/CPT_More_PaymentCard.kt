package com.example.cpt.more

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cpt.R
import kotlinx.android.synthetic.main.activity_cpt__more__payment_card.*
import kotlinx.android.synthetic.main.customview_more_paycard_changecardname.view.*

class CPT_More_PaymentCard : AppCompatActivity() {

    var cardList : ArrayList<PaymentCard_Model>? = null
    var cardAdapter : PaymentCard_Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpt__more__payment_card)
        supportActionBar!!.hide()

        // Google Gson을 이용해서 ArrayList -> SharedPreferece 저장하기.
        //https://www.youtube.com/watch?v=jcliHGR3CHo
        checkCard()

        setButtons()
        cardList = getCards()
        buildCardListView()
    }

    private fun checkCard() {
        // Google Gson을 이용해서 ArrayList -> SharedPreferece 저장하기.
        //https://www.youtube.com/watch?v=jcliHGR3CHo
    }

    private fun buildCardListView() {
        cardAdapter = PaymentCard_Adapter(cardList!!)
        rvmore_paycard_cardlist.layoutManager = LinearLayoutManager(this)
        rvmore_paycard_cardlist.adapter = cardAdapter!!

        cardAdapter!!.setOnItemClickListener(object: PaymentCard_Adapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(applicationContext, "Item Clicked", Toast.LENGTH_SHORT).show()
            }
            override fun onModifyClick(position: Int) {

                val layoutInflater = LayoutInflater.from(this@CPT_More_PaymentCard)
                val view : View = layoutInflater.inflate(R.layout.customview_more_paycard_changecardname, null)
                view.tvmore_paycard_oricardname.setText(cardAdapter!!.CardList!![position].getcardName())
                view.tvmore_paycard_oricardnumber.setText(cardAdapter!!.CardList!![position].getcardNumber())

                var builder : AlertDialog.Builder = AlertDialog.Builder(this@CPT_More_PaymentCard)
                builder.setView(view)

                val mDialog = builder.show()

                view.btnmore_paycard_changeconfirm.setOnClickListener {
                    if(!view.etmore_paycard_newcardname.text.toString().isEmpty()) {
                        cardList!!.get(position).cardName = view.etmore_paycard_newcardname.text.toString()
                        cardAdapter!!.notifyItemChanged(position)
                        mDialog!!.dismiss()
                    }
                    else
                        mDialog.dismiss()
                }
                view.btnmore_paycard_changecancel.setOnClickListener {
                    mDialog!!.dismiss()
                }
            }
            override fun onDeleteClick(position: Int) {
                val builder = AlertDialog.Builder(this@CPT_More_PaymentCard)
                builder.setTitle("Delete Card")
                    .setMessage("Are you sure that you want to delete ")
                    .setPositiveButton("Confirm", object: DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            cardList!!.removeAt(position)
                            cardAdapter!!.notifyItemRemoved(position)
                            dialog!!.dismiss()
                        }
                    })
                    .setNegativeButton("Cancel", object:DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    }).show()
            }
        })
    }


    private fun setButtons() {
        tvmore_paycard_cancel.setOnClickListener {
            onBackPressed()
        }
        btnmore_paycard_addcard.setOnClickListener {
            addcard()
        }
    }

    private fun getCards() : ArrayList<PaymentCard_Model>{
        var list = ArrayList<PaymentCard_Model>()
        var card1 = PaymentCard_Model(
            "KAY's Credit Card",
            "**** **** **** 2627 (10/21)",
            R.drawable.modify,
            R.drawable.bin
        )
        var card2 = PaymentCard_Model(
            "KAY's Debit Card",
            "**** **** **** 2003 (10/21)",
            R.drawable.modify,
            R.drawable.bin
        )

        list.add(card1)
        list.add(card2)
        return list
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun addcard() {
        // Connect with Payment company later...
    }
}


class PaymentCard_Model (var cardName: String,  var cardNumber: String, var modify: Int, var bin: Int) {

    fun getcardName() : String {
        return cardName.toString()
    }
    fun getcardNumber() : String {
        return cardNumber.toString()
    }
    fun getmodify() : Int {
        return modify
    }
    fun getbin() : Int{
        return bin
    }
}

class PaymentCard_Adapter: RecyclerView.Adapter<PaymentCard_Adapter.CardViewHolder> {

    var CardList : ArrayList<PaymentCard_Model>? = null
    var listener : OnItemClickListener? = null

    constructor(cardList: ArrayList<PaymentCard_Model>) {
        CardList = cardList
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onModifyClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.customview_more_paycard_cards, parent, false)
        return CardViewHolder(view, listener!!)
    }

    override fun getItemCount(): Int {
        return CardList!!.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item: PaymentCard_Model = CardList!![position]

        holder.cardName.setText(item.getcardName())
        holder.cardNumber.setText(item.getcardNumber())
        holder.modify.setImageResource(item.getmodify())
        holder.bin.setImageResource(item.getbin())
    }

    class CardViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        var cardName: TextView
        var cardNumber: TextView
        var modify: ImageView
        var bin: ImageView

        init {
            cardName = itemView.findViewById(R.id.tvmore_paycard_cardname)
            cardNumber = itemView.findViewById(R.id.tvmore_paycard_cardnumber)
            modify = itemView.findViewById(R.id.ivmore_paycard_modify)
            bin = itemView.findViewById(R.id.ivmore_paycard_bin)

            itemView.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    val position: Int = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            })
            modify.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    val position: Int = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onModifyClick(position)
                    }
                }
            })
            bin.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    val position: Int = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position)
                    }
                }
            })
        }
    }
}