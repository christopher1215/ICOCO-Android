package com.prangbi.android.icoco.controller

import android.app.Activity
import android.support.v4.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.controller.ico.IcoFragment
import com.prangbi.android.icoco.controller.more.MoreFragment
import com.prangbi.android.icoco.controller.news.NewsFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private val icoFragment = IcoFragment()
    private val newsFragment = NewsFragment()
    private val moreFragment = MoreFragment()

    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(R.layout.main_activity)
        initTab()
        replaceFragment(icoFragment)
    }

    private fun initTab() {
        icoTabButton.setOnClickListener(View.OnClickListener {
            replaceFragment(icoFragment)
        })

        newsTabButton.setOnClickListener(View.OnClickListener {
            replaceFragment(newsFragment)
        })

        moreTabButton.setOnClickListener(View.OnClickListener {
            replaceFragment(moreFragment)
        })
    }

    /**
     * Function
     */
    private fun replaceFragment(fragment: Fragment) {
        val selectedColor = Color.parseColor("#007AFF")
        val unselectedColor = Color.parseColor("#A1A1A1")
        val transaction = supportFragmentManager.beginTransaction()
        icoTabImageView.setColorFilter(if (fragment === icoFragment) selectedColor else unselectedColor)
        icoTabTextView.setTextColor(if (fragment === icoFragment) selectedColor else unselectedColor)
        newsTabImageView.setColorFilter(if (fragment === newsFragment) selectedColor else unselectedColor)
        newsTabTextView.setTextColor(if (fragment === newsFragment) selectedColor else unselectedColor)
        moreTabImageView.setColorFilter(if (fragment === moreFragment) selectedColor else unselectedColor)
        moreTabTextView.setTextColor(if (fragment === moreFragment) selectedColor else unselectedColor)
        transaction.replace(R.id.fragmentLayout, fragment)
        transaction.commit()
    }
}
