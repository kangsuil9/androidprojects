package com.example.kangs.guestapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kangs.dbmanagertest.R
import kotlinx.android.synthetic.main.fragment_guest__chat.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Guest_Chat : Fragment() {

    private var aChat_FoodModel : ArrayList<Chat_FoodModel>? = null

    private val aMainNameList = arrayOf("Susan", "Sofia", "Kim", "Kang", "Seo", "Edward", "Kyle", "Yeseul", "Sooil", "Namban")
    private val aMainContentList = arrayOf("Oh, that is so bad, I really wanted to try on korean food and speak with you.. I feel so bad.",
                                                "Hello, please response",
                                                "Ok great, See you then.",
                                                "Hello my name is Susan, I want to try USA breakfast.",
                                                "Ok good",
                                                "why not?",
                                                "really nice, I am so looking forward to meeting you guys",
                                                "Ok Thanks.",
                                                "See you~",
                                                "okok good")
    private val aMainLastdateList = arrayOf("26 Feb >", "25 Feb >", "25 Feb >", "12 Feb >", "10 Feb >", "26 Jan >", "24 Jan >", "24 Jan >", "6 Jan >", "1 Jan >")

    private val aMainImage = intArrayOf(R.drawable.dot, R.drawable.circle, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot, R.drawable.dot)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest__chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        aChat_FoodModel = populateList()
        var customeAdapter_Chat :CustomAdapter_Chat = CustomAdapter_Chat(view!!.context, aChat_FoodModel!!)
        lvGuestChat.adapter = customeAdapter_Chat
    }

    private fun populateList(): ArrayList<Chat_FoodModel> {
        val list = ArrayList<Chat_FoodModel>()

        for (i in 0..9) {
            val Chat_FoodModel = Chat_FoodModel()
            Chat_FoodModel.setMainImage(aMainImage[i])
            Chat_FoodModel.setMainName(aMainNameList[i])

            if(aMainContentList[i].toString().length > 20)
                Chat_FoodModel.setMainContent(aMainContentList[i].toString().substring(0, 19) + " ...")
            else
                Chat_FoodModel.setMainContent(aMainContentList[i])

            Chat_FoodModel.setMainLastdate(aMainLastdateList[i])
            list.add(Chat_FoodModel)
        }
        return list
    }

}
