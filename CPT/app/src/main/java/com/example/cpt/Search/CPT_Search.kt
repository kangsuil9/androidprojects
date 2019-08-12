package com.example.cpt.Search


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cpt.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_cpt__search.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CPT_Search : Fragment() {

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    var routestoplist: ArrayList<RouteStop_Model> = arrayListOf()
    //var routeadapter: ArrayAdapter<RouteStop_Model>? = null
    var routeadapter: RouteStop_Adapter? = null

    //var likelist: ArrayList<String> = arrayListOf()
    //var likeadapter: ArrayAdapter<String>? = null

    companion object {
        var likeadapter: RouteStop_Adapter? = null
        var likelist: ArrayList<RouteStop_Model> = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpt__search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setButtons()
    }

    private fun init() {

        val token = activity!!.baseContext.getSharedPreferences(R.string.LIKE_LIST.toString(), Context.MODE_PRIVATE)

        if(token.getString(R.string.LIKE_LIST.toString(), " ") != " ") {
            val jsonlikelist = token.getString(R.string.LIKE_LIST.toString(), " ")

            val gson = Gson()
            val type = object: TypeToken<ArrayList<RouteStop_Model>>() {}.type
            likelist = gson.fromJson<ArrayList<RouteStop_Model>>(jsonlikelist, type)
        }

        for(i in 0..4) {
            var route = RouteStop_Model("$i", "R")
            routestoplist.add(route)
        }
        for(i in 0..4) {
            var route = RouteStop_Model("Stop $i", "S")
            routestoplist.add(route)
        }

        //routeadapter = ArrayAdapter(activity!!.baseContext, android.R.layout.simple_list_item_1, routestoplist)
        routeadapter = RouteStop_Adapter(activity!!.baseContext, routestoplist)
        lvs_main_searchlist.adapter = routeadapter
        bottomSheetBehavior = BottomSheetBehavior.from(lls_bottomsheet)

        //likeadapter = ArrayAdapter(activity!!.baseContext, android.R.layout.simple_list_item_1, likelist)
        //likeadapter = RouteStop_Adapter(activity!!.baseContext, likelist)
        likeadapter = RouteStop_Adapter(activity!!.baseContext, likelist)
        lvs_likelist.adapter = likeadapter

        //solving the problem that not enabled scrolling listview in NestedScrollview.
        /*
        lls_bottomsheet.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                lvs_main_searchlist.parent.requestDisallowInterceptTouchEvent(false)
                return false
            }
        })
        lvs_main_searchlist.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                v!!.parent.requestDisallowInterceptTouchEvent(true)
                return false
            }
        })
        */
        //------------------------------------------------------------------------------
    }

    private fun setButtons() {
        lls_searchBar.setOnClickListener {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
        ivs_main_closesearchview.setOnClickListener {
            ets_main_routeorstation.setText("")
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior!!.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(v: View, offset: Float) { }
            override fun onStateChanged(v: View, state: Int) {
                if(state == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        })

        ets_main_routeorstation.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                routeadapter!!.filter.filter(s)
            }
        })

        lvs_main_searchlist.setOnItemClickListener { _, _, position, _ ->
            val intent: Intent
            if(routestoplist[position].gettype() == "R") {
                intent = Intent(activity, CPT_Search_Route::class.java)
            }
            else {
                intent = Intent(activity, CPT_Search_Stop::class.java)
            }
            intent.putExtra("ROUTESTOPNUMBER", routeadapter!!.routestoplist[position].getname())
            intent.putExtra("ROUTESTOPTYPE", routeadapter!!.routestoplist[position].gettype())
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        lvs_likelist.setOnItemClickListener { _, _, position, _ ->
            val intent: Intent
            if(likelist.get(position).gettype().equals("R")) {
                intent = Intent(activity, CPT_Search_Route::class.java)
            }
            else {
                intent = Intent(activity, CPT_Search_Stop::class.java)
            }
            intent.putExtra("ROUTESTOPNUMBER", likelist.get(position).getname())
            intent.putExtra("ROUTESTOPTYPE", likelist.get(position).gettype())
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}

class RouteStop_Model(val name: String, val type: String) {
    fun getname(): String {
        return name
    }
    fun gettype(): String {
        return type
    }
}

class RouteStop_Adapter(val context: Context, var routestoplist: ArrayList<RouteStop_Model>) : BaseAdapter(), Filterable {

    private var valueFilter: ValueFilter? = null
    private var listforfilter = routestoplist



    fun updateList() {
        val token = context.getSharedPreferences(R.string.LIKE_LIST.toString(), Context.MODE_PRIVATE)
        val editor = token.edit()

        var gson = Gson()
        val jsonList: String = gson.toJson(routestoplist)
        editor.putString(R.string.LIKE_LIST.toString(), jsonList).apply()
    }

    override fun getItem(position: Int): Any {
        return routestoplist.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return routestoplist.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertview = convertView
        val holder: ViewHolder

        if (convertview == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertview = inflater.inflate(R.layout.customview_search_routestoplist, null, true)

            holder.title = convertview!!.findViewById(R.id.tvs_routestopname) as TextView

            convertview.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertview.tag as ViewHolder
        }

        holder.title!!.setText(routestoplist[position].getname())
        holder.type = routestoplist[position].gettype()

        return convertview
    }

    private inner class ViewHolder {

        var title: TextView? = null
        var type: String? = null
    }

    override fun getFilter(): Filter {
        if(valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter as ValueFilter
    }

    private inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()

            if(constraint != null && constraint.length > 0) {
                val resultList = ArrayList<RouteStop_Model>()
                for(i in 0..listforfilter.size-1) {
                    if(listforfilter.get(i).getname().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        val routestop = listforfilter.get(i)
                        resultList.add(routestop)
                    }
                }
                result.count = resultList.size
                result.values = resultList
            }
            else {
                result.count = listforfilter.size
                result.values = listforfilter
            }

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            routestoplist = results?.values as ArrayList<RouteStop_Model>
            notifyDataSetChanged()
        }

    }
}