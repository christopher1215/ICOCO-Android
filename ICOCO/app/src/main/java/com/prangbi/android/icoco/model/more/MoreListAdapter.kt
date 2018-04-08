package com.prangbi.android.icoco.model.more

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.prangbi.android.icoco.R

/**
 * Created by hsg on 2018. 2. 27..
 */
class MoreListAdapter: BaseExpandableListAdapter() {
    /**
     * Variable
     */
    private var items: MutableList<MoreMenu> = mutableListOf()

    /**
     * Implement
     */
    override fun getGroupCount(): Int {
        return items.count()
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroup(groupPosition: Int): Any {
        val title = items[groupPosition].title
        return if (null != title) title else ""
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val moreMenu = items[groupPosition]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.simple_item, parent, false)
        }
        convertView!!.setBackgroundColor(Color.parseColor("#EBEBF1"))

        val textView = convertView.findViewById<TextView>(R.id.textView)
        textView.textSize = 16f
        textView.text = moreMenu.title

        if (parent is ExpandableListView) {
            parent.expandGroup(groupPosition)
        }

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val content = items[groupPosition].content
        return if (null != content) content else ""
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val moreMenu = items[groupPosition]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.simple_item, parent, false)
        }

        val textView = convertView!!.findViewById<TextView>(R.id.textView)
        textView.textSize = 13f
        textView.text = moreMenu.content

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    /**
     * Function
     */
    fun addItem(moreMenu: MoreMenu) {
        items.add(moreMenu)
    }

    fun addItems(moreMenu: List<MoreMenu>) {
        items.addAll(moreMenu)
    }

    fun removeAll() {
        items.removeAll(items)
    }
}