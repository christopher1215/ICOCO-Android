package com.prangbi.android.icoco.model.ico

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.helper.util.DateTimeUtil
import com.prangbi.android.icoco.view.etc.PrImageView

/**
 * Created by hsg on 2018. 3. 27..
 */
class IcoProfileAboutListAdapter: BaseAdapter() {
    /**
     * Variable
     */
    var icoProfile: IcoProfile? = null

    /**
     * Implement
     */
    override fun getCount(): Int {
        var count = 3
        val milestoneCount = icoProfile?.milestones?.count()
        if (null != milestoneCount) {
            count += milestoneCount
        }
        return count
    }

    override fun getItem(position: Int): Any? {
        var item: Any? = null
        if (0 == position) {
            item = icoProfile
        } else if (1 == position) {
            item = icoProfile?.intro
        } else if (2 == position) {
            item = icoProfile?.finance
        } else {
            item = icoProfile?.milestones!![position]
        }
        return item
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        if (0 == position) {
            if (R.layout.ico_summary_item != convertView?.id) {
                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.ico_summary_item, parent, false)
            }

            val titleTextView = convertView!!.findViewById<TextView>(R.id.titleTextView)
            titleTextView.text = icoProfile?.name

            val titleImageView = convertView.findViewById<PrImageView>(R.id.titleImageView)
            val titleImageViewBg = titleImageView.background as? GradientDrawable
            if (null != titleImageViewBg) {
                titleImageViewBg.setColor(Color.parseColor("#EBEBF1"))
                titleImageView.background = titleImageViewBg
            }
            titleImageView.clipToOutline = true
            titleImageView.loadImage(icoProfile?.logo)

            var desc = if (null != icoProfile?.tagline) icoProfile!!.tagline!! else ""
            desc += "\n"
            if (null != icoProfile?.country) {
                desc += ("(" + icoProfile!!.country!! + ")")
            }
            val descriptionTextView = convertView.findViewById<TextView>(R.id.descriptionTextView)
            descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            descriptionTextView.text = desc

            val dates = icoProfile?.dates
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

            val rating = if (null != icoProfile) icoProfile!!.rating else 0f
            val ratingTextView = convertView.findViewById<TextView>(R.id.ratingTextView)
            val ratingTextViewBg = ratingTextView.background as? GradientDrawable
            if (null != ratingTextViewBg) {
                ratingTextViewBg.setColor(Color.parseColor("#1E90FF"))
                ratingTextView.background = ratingTextViewBg
            }
            ratingTextView.clipToOutline = true
            ratingTextView.text = (if (0<rating) rating else 0f).toString()
        } else if (1 == position) {
            if (R.layout.simple_item != convertView?.id) {
                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.simple_item, parent, false)
            }

            val textView = convertView!!.findViewById<TextView>(R.id.textView)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.text = icoProfile?.intro
        } else if (2 == position) {
            if (R.layout.ico_profile_finance_item != convertView?.id) {
                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.ico_profile_finance_item, parent, false)
            }
            val finance = icoProfile?.finance

            val tokenTextView = convertView!!.findViewById<TextView>(R.id.tokenTextView)
            tokenTextView.text = finance?.token

            val priceTextView = convertView.findViewById<TextView>(R.id.priceTextView)
            priceTextView.text = finance?.price

            val bonusTextView = convertView.findViewById<TextView>(R.id.bonusTextView)
            bonusTextView.text = if (true == finance?.bonus) "Yes" else "No"

            val tokensTextView = convertView.findViewById<TextView>(R.id.tokensTextView)
            tokensTextView.text = if (null != finance?.tokens) finance.tokens.toString() else "0"

            val tokenTypeTextView = convertView.findViewById<TextView>(R.id.tokenTypeTextView)
            tokenTypeTextView.text = finance?.tokentype

            val hardcapTextView = convertView.findViewById<TextView>(R.id.hardcapTextView)
            hardcapTextView.text = finance?.hardcap

            val softcapTextView = convertView.findViewById<TextView>(R.id.softcapTextView)
            softcapTextView.text = finance?.softcap

            val raisedTextView = convertView.findViewById<TextView>(R.id.raisedTextView)
            raisedTextView.text = if (null != finance?.raised) finance.raised.toString() else "0"

            val platformTextView = convertView.findViewById<TextView>(R.id.platformTextView)
            platformTextView.text = finance?.platform

            val distributedTextView = convertView.findViewById<TextView>(R.id.distributedTextView)
            distributedTextView.text = finance?.distributed

            val minimumTextView = convertView.findViewById<TextView>(R.id.minimumTextView)
            minimumTextView.text = finance?.minimum

            val acceptingTextView = convertView.findViewById<TextView>(R.id.acceptingTextView)
            acceptingTextView.text = finance?.accepting
        } else {
            if (R.layout.ico_profile_milestone_item != convertView?.id) {
                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.ico_profile_milestone_item, parent, false)
            }
            val index = position - 3
            val milestone = icoProfile!!.milestones!![index]

            val leftTitleTextView = convertView!!.findViewById<TextView>(R.id.leftTitleTextView)
            val leftContentTextView = convertView.findViewById<TextView>(R.id.leftContentTextView)
            val rightTitleTextView = convertView.findViewById<TextView>(R.id.rightTitleTextView)
            val rightContentTextView = convertView.findViewById<TextView>(R.id.rightContentTextView)
            if (0 == index%2) {
                leftTitleTextView.text = milestone.title
                leftTitleTextView.visibility = View.VISIBLE
                leftContentTextView.text = milestone.content
                leftContentTextView.visibility = View.VISIBLE
                rightTitleTextView.text = null
                rightTitleTextView.visibility = View.INVISIBLE
                rightContentTextView.text = null
                rightContentTextView.visibility = View.INVISIBLE
            } else {
                leftTitleTextView.text = null
                leftTitleTextView.visibility = View.INVISIBLE
                leftContentTextView.text = null
                leftContentTextView.visibility = View.INVISIBLE
                rightTitleTextView.text = milestone.title
                rightTitleTextView.visibility = View.VISIBLE
                rightContentTextView.text = milestone.content
                rightContentTextView.visibility = View.VISIBLE
            }
            val dotView = convertView.findViewById<View>(R.id.dotView)
            val dotViewBg = dotView.background as? GradientDrawable
            if (null != dotViewBg) {
                dotViewBg.setColor(Color.parseColor("#1E90FF"))
                dotView.background = dotViewBg
            }
        }
        return convertView
    }
}