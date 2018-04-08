package com.prangbi.android.icoco.model.ico

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
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
class IcoListAdapter: BaseAdapter() {
    /**
     * Variable
     */
    private var items: MutableList<IcoSummary> = mutableListOf()

    /**
     * Implement
     */
    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): IcoSummary {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val icoSummary = items[position]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.ico_summary_item, parent, false)
        }

        val titleTextView = convertView!!.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = icoSummary.name

        val titleImageView = convertView.findViewById<PrImageView>(R.id.titleImageView)
        val titleImageViewBg = titleImageView.background as? GradientDrawable
        if (null != titleImageViewBg) {
            titleImageViewBg.setColor(Color.parseColor("#EBEBF1"))
            titleImageView.background = titleImageViewBg
        }
        titleImageView.clipToOutline = true
        titleImageView.loadImage(icoSummary.logo)

        val descriptionTextView = convertView.findViewById<TextView>(R.id.descriptionTextView)
        descriptionTextView.text = icoSummary.desc

        val dates = icoSummary.dates
        val preIcoStart = DateTimeUtil.changeIcoDateFormat(dates?.preIcoStart)
        val preIcoEnd = DateTimeUtil.changeIcoDateFormat(dates?.preIcoEnd)
        val preIcoTitleTextView = convertView.findViewById<TextView>(R.id.preIcoTitleTextView)
        val preIcoTitleTextViewBg = preIcoTitleTextView.background as? GradientDrawable
        if (null != preIcoTitleTextViewBg) {
            preIcoTitleTextViewBg.setColor(Color.parseColor("#000000"))
            preIcoTitleTextView.background = preIcoTitleTextViewBg
        }
        preIcoTitleTextView.clipToOutline = true
        val preIcoDateTextView = convertView.findViewById<TextView>(R.id.preIcoDateTextView)
        if (null == preIcoStart && null == preIcoEnd) {
            preIcoTitleTextView.visibility = View.GONE
            preIcoDateTextView.visibility = View.GONE
        } else {
            preIcoTitleTextView.visibility = View.VISIBLE
            preIcoDateTextView.visibility = View.VISIBLE
            var preIcoDateStr = ""
            if (null != preIcoStart) {
                preIcoDateStr += preIcoStart
            }
            preIcoDateStr += " ~ "
            if (null != preIcoEnd) {
                preIcoDateStr += preIcoEnd
            }
            preIcoDateTextView.text = preIcoDateStr
        }

        val icoStart = DateTimeUtil.changeIcoDateFormat(dates?.icoStart)
        val icoEnd = DateTimeUtil.changeIcoDateFormat(dates?.icoEnd)
        val icoTitleTextView = convertView.findViewById<TextView>(R.id.icoTitleTextView)
        val icoTitleTextViewBg = icoTitleTextView.background as? GradientDrawable
        if (null != icoTitleTextViewBg) {
            icoTitleTextViewBg.setColor(Color.parseColor("#000000"))
            icoTitleTextView.background = icoTitleTextViewBg
        }
        icoTitleTextView.clipToOutline = true
        val icoDateTextView = convertView.findViewById<TextView>(R.id.icoDateTextView)
        if (null == icoStart && null == icoEnd) {
            icoTitleTextView.visibility = View.GONE
            icoDateTextView.visibility = View.GONE
        } else {
            icoTitleTextView.visibility = View.VISIBLE
            icoDateTextView.visibility = View.VISIBLE
            var icoDateStr = ""
            if (null != icoStart) {
                icoDateStr += icoStart
            }
            icoDateStr += " ~ "
            if (null != icoEnd) {
                icoDateStr += icoEnd
            }
            icoDateTextView.text = icoDateStr
        }

        val rating = icoSummary.rating
        val ratingTextView = convertView.findViewById<TextView>(R.id.ratingTextView)
        val ratingTextViewBg = ratingTextView.background as? GradientDrawable
        if (null != ratingTextViewBg) {
            ratingTextViewBg.setColor(Color.parseColor("#1E90FF"))
            ratingTextView.background = ratingTextViewBg
        }
        ratingTextView.clipToOutline = true
        ratingTextView.text = (if (0<rating) rating else 0f).toString()

        return convertView
    }

    /**
     * Function
     */
    fun addItem(icoSummary: IcoSummary) {
        items.add(icoSummary)
    }

    fun addItems(icoSummaryList: List<IcoSummary>) {
        items.addAll(icoSummaryList)
    }

    fun removeAll() {
        items.removeAll(items)
    }
}