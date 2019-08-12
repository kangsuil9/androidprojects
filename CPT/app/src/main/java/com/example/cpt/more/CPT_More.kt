package com.example.cpt.more


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.cpt.*
import kotlinx.android.synthetic.main.customview_more_item.view.*
import kotlinx.android.synthetic.main.fragment_cpt__more.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CPT_More : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpt__more, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        listmore_myinfo.setOnClickListener {
            menu("myinfo")
        }
        listmore_paycard.setOnClickListener {
            menu("paycard")
        }
        listmore_transitnews.setOnClickListener {
            menu("news")
        }
        listmore_locationpermit.setOnClickListener {
            menu("locationpermit")
        }
        tvmore_terms.setOnClickListener {
            menu("termsofuse")
        }
        tvmore_dataprotection.setOnClickListener {
            menu("dataprotection")
        }
        btnmore_logout.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            val token = activity!!.baseContext.getSharedPreferences(resources.getString(R.string.SIGNIN_INFO), Context.MODE_PRIVATE)
            val token_ticketlist = activity!!.baseContext.getSharedPreferences(R.string.TICKET_LIST.toString(), Context.MODE_PRIVATE)
            builder.setTitle("Log Out")
                .setMessage("Are you sure that you want to Log out?")
                .setPositiveButton("Confirm", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        token.edit().clear().apply()
                        token_ticketlist.edit().clear().apply()
                        val intent = Intent(activity!!.baseContext, SignIn::class.java)
                        startActivity(intent)
                        dialog!!.dismiss()
                    }
                })
                .setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                }).show()
        }
    }

    private fun menu(item: String) {
        val intent: Intent
        when(item) {
            "myinfo" -> {
                intent = Intent(activity!!.baseContext, CPT_More_Myinfo::class.java)
            }
            "paycard" -> {
                intent = Intent(activity!!.baseContext, CPT_More_PaymentCard::class.java)
            }
            "news" -> {
                intent = Intent(activity!!.baseContext, CPT_More_News::class.java)
            }
            "locationpermit" -> {
                intent = Intent(activity!!.baseContext, CPT_More_LocationPermission::class.java)
            }
            "termsofuse" -> {
                intent = Intent(activity!!.baseContext, CPT_More_Terms::class.java)
            }
            "dataprotection" -> {
                intent = Intent(activity!!.baseContext, CPT_More_Dataprotection::class.java)
            }
            else -> {
                return
            }
        }
        startActivity(intent)
        activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}

class More_ItemOfList(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.customview_more_item, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.More_ItemOfList)
        ivmore_lefticon.setImageDrawable(attributes.getDrawable(R.styleable.More_ItemOfList_ivmore_lefticon))
        tvmore_itemname.text = attributes.getString(R.styleable.More_ItemOfList_tvmore_itemname)
        ivmore_righticon.setImageDrawable(attributes.getDrawable(R.styleable.More_ItemOfList_ivmore_righticon))

        attributes.recycle()
    }
}