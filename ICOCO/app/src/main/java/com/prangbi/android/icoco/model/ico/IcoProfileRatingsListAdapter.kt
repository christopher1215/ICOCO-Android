package com.prangbi.android.icoco.model.ico

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
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
class IcoProfileRatingsListAdapter: BaseAdapter() {
    /**
     * Variable
     */
    private val items: MutableList<IcoRating> = mutableListOf()

    /**
     * Implement
     */
    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(position: Int): IcoRating {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val context = parent?.context
        val icoRating = items[position]
        if (null == convertView) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.ico_profile_rating_item, parent, false)
        }

        val profileImageView = convertView!!.findViewById<PrImageView>(R.id.profileImageView)
        val profileImageViewBg = profileImageView.background as? GradientDrawable
        if (null != profileImageViewBg) {
            profileImageViewBg.setColor(Color.parseColor("#EBEBF1"))
            profileImageView.background = profileImageViewBg
        }
        profileImageView.clipToOutline = true
        profileImageView.loadImage(icoRating.photo)

        val nameTextView = convertView.findViewById<TextView>(R.id.nameTextView)
        nameTextView.text = icoRating.name

        val titleTextView = convertView.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = icoRating.title

        val dateTextView = convertView.findViewById<TextView>(R.id.dateTextView)
        dateTextView.text = icoRating.date

        var weight = "Weight "
        if (null != icoRating.weight) {
            weight += icoRating.weight
        } else {
            weight += "0%"
        }
        val weightTextView = convertView.findViewById<TextView>(R.id.weightTextView)
        weightTextView.text = weight

        val agree = "Agree " + icoRating.agree.toString()
        val agreeTextView = convertView.findViewById<TextView>(R.id.agreeTextView)
        agreeTextView.text = agree

        val teamRatingTextView = convertView.findViewById<TextView>(R.id.teamRatingTextView)
        val teamRatingTextViewBg = teamRatingTextView.background as? GradientDrawable
        if (null != teamRatingTextViewBg) {
            teamRatingTextViewBg.setColor(Color.parseColor("#1E90FF"))
            teamRatingTextView.background = teamRatingTextViewBg
        }
        teamRatingTextView.text = icoRating.team.toString()

        val visionRatingTextView = convertView.findViewById<TextView>(R.id.visionRatingTextView)
        val visionRatingTextViewBg = visionRatingTextView.background as? GradientDrawable
        if (null != visionRatingTextViewBg) {
            visionRatingTextViewBg.setColor(Color.parseColor("#1E90FF"))
            visionRatingTextView.background = visionRatingTextViewBg
        }
        visionRatingTextView.text = icoRating.vision.toString()

        val productRatingTextView = convertView.findViewById<TextView>(R.id.productRatingTextView)
        val productRatingTextViewBg = productRatingTextView.background as? GradientDrawable
        if (null != productRatingTextViewBg) {
            productRatingTextViewBg.setColor(Color.parseColor("#1E90FF"))
            productRatingTextView.background = productRatingTextViewBg
        }
        productRatingTextView.text = icoRating.product.toString()

        val profileRatingTextView = convertView.findViewById<TextView>(R.id.profileRatingTextView)
        val profileRatingTextViewBg = profileRatingTextView.background as? GradientDrawable
        if (null != profileRatingTextViewBg) {
            profileRatingTextViewBg.setColor(Color.parseColor("#1E90FF"))
            profileRatingTextView.background = profileRatingTextViewBg
        }
        profileRatingTextView.text = icoRating.profile.toString()

        val reviewRatingTextView = convertView.findViewById<TextView>(R.id.reviewTextView)
        reviewRatingTextView.text = icoRating.review

        val teamRatingLayout = convertView.findViewById<ConstraintLayout>(R.id.teamRatingLayout)
        val visionRatingLayout = convertView.findViewById<ConstraintLayout>(R.id.visionRatingLayout)
        val productRatingLayout = convertView.findViewById<ConstraintLayout>(R.id.productRatingLayout)
        val profileRatingLayout = convertView.findViewById<ConstraintLayout>(R.id.profileRatingLayout)
        if (0 < icoRating.profile) {
            teamRatingLayout.visibility = View.INVISIBLE
            visionRatingLayout.visibility = View.INVISIBLE
            productRatingLayout.visibility = View.INVISIBLE
            profileRatingLayout.visibility = View.VISIBLE
        } else {
            teamRatingLayout.visibility = View.VISIBLE
            visionRatingLayout.visibility = View.VISIBLE
            productRatingLayout.visibility = View.VISIBLE
            profileRatingLayout.visibility = View.INVISIBLE
        }

        return convertView
    }

    /**
     * Function
     */
    /**
     * Function
     */
    fun addItem(icoRating: IcoRating) {
        items.add(icoRating)
    }

    fun addItems(icoRatingList: List<IcoRating>) {
        items.addAll(icoRatingList)
    }

    fun removeAll() {
        items.removeAll(items)
    }
}