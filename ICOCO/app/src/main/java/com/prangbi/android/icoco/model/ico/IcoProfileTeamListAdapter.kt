package com.prangbi.android.icoco.model.ico

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.view.etc.PrImageView

/**
 * Created by hsg on 2018. 3. 28..
 */
class IcoProfileTeamListAdapter: BaseAdapter() {
    /**
     * Variable
     */
    private val items: MutableList<IcoTeam> = mutableListOf()

    /**
     * Implement
     */
    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): IcoTeam {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val icoTeam = items[position]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.ico_profile_member_item, parent, false)
        }

        val profileImageView = convertView!!.findViewById<PrImageView>(R.id.profileImageView)
        val profileImageViewBg = profileImageView.background as? GradientDrawable
        if (null != profileImageViewBg) {
            profileImageViewBg.setColor(Color.parseColor("#EBEBF1"))
            profileImageView.background = profileImageViewBg
        }
        profileImageView.clipToOutline = true
        profileImageView.loadImage(icoTeam.photo)

        val nameTextView = convertView.findViewById<TextView>(R.id.nameTextView)
        nameTextView.text = icoTeam.name

        var title = icoTeam.title
        val group = icoTeam.group
        if (null != group && 0 < group.count()) {
            title += (" in (" + group + ")")
        }
        val titleTextView = convertView.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = title

        return convertView
    }

    /**
     * Function
     */
    fun addItem(icoTeam: IcoTeam) {
        items.add(icoTeam)
    }

    fun addItems(icoTeamList: List<IcoTeam>) {
        items.addAll(icoTeamList)
    }

    fun removeAll() {
        items.removeAll(items)
    }
}