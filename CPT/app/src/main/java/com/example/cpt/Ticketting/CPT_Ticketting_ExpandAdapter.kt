package com.example.cpt.Ticketting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.cpt.R
import kotlinx.android.synthetic.main.customview_ticket_listchild.view.*
import kotlinx.android.synthetic.main.customview_ticket_listparent.view.*

class CPT_Ticketting_ExpandAdapter : BaseExpandableListAdapter{

    var context: Context
    var parentList = ArrayList<ParentItem_Model>()
    var childList = HashMap<String, ArrayList<ChildItem_Model>>()


    constructor(context: Context, parentList: ArrayList<ParentItem_Model>, childList: HashMap<String, ArrayList<ChildItem_Model>>) {
        this.context = context
        this.parentList = parentList
        this.childList = childList
    }

    override fun getGroup(groupPosition: Int): Any {
        return parentList[groupPosition]
    }

    override fun isChildSelectable(parentPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val parentItem = getGroup(groupPosition) as ParentItem_Model
        val view: View = LayoutInflater.from(context).inflate(R.layout.customview_ticket_listparent, parent, false)
        view.tvt_parenttitle.setText(parentItem.getparentTitle())
        view.tvt_parentcontent.setText(parentItem.getparentContent())
        view.ivt_arrow.setImageResource(parentItem.getarrow())
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childList[parentList[groupPosition].getparentTitle()]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childList[parentList[groupPosition].getparentTitle()]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val childItem = getChild(groupPosition, childPosition) as ChildItem_Model
        val view: View = LayoutInflater.from(context).inflate(R.layout.customview_ticket_listchild, parent, false)
        view.tvt_childtitle.setText(childItem.getchildTitle())
        view.tvt_childcontent.setText(childItem.getchildContent())
        view.tvt_childprice.setText(childItem.getchildPrice())

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return parentList.size
    }

}


class ParentItem_Model (var parentTitle: String, var parentContent: String, var arrow: Int ) {
    fun getparentTitle(): String {
        return parentTitle
    }
    fun getparentContent(): String {
        return parentContent
    }
    fun getarrow(): Int {
        return arrow
    }
}

class ChildItem_Model (var childTitle: String, var childContent: String, var childPrice: String) {
    fun getchildTitle(): String {
        return childTitle
    }
    fun getchildContent(): String {
        return childContent
    }
    fun getchildPrice(): String {
        return childPrice
    }
}