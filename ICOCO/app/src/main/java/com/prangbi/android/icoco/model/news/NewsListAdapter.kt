package com.prangbi.android.icoco.model.news

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.helper.util.DateTimeUtil
import com.prangbi.android.icoco.view.etc.PrImageView

/**
 * Created by hsg on 2018. 2. 27..
 */
class NewsListAdapter: BaseAdapter() {
    /**
     * Variable
     */
    private var items: MutableList<NewsInfo> = mutableListOf()

    /**
     * Implement
     */
    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): NewsInfo {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val newsInfo = items[position]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.news_item, parent, false)
        }

        val titleTextView = convertView!!.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = newsInfo.title

        val creatorTextView = convertView.findViewById<TextView>(R.id.creatorTextView)
        creatorTextView.text = newsInfo.creator

        val pubDate = DateTimeUtil.changeNewsPupDateFormat(newsInfo.pubDate)
        val dateTimeTextView = convertView.findViewById<TextView>(R.id.dateTimeTextView)
        dateTimeTextView.text = pubDate

        return convertView
    }

    /**
     * Function
     */
    fun addItem(newsList: NewsInfo) {
        items.add(newsList)
    }

    fun addItems(newsList: List<NewsInfo>) {
        items.addAll(newsList)
    }

    fun removeAll() {
        items.removeAll(items)
    }
}