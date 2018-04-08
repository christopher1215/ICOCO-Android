package com.prangbi.android.icoco.controller.ico

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.ShareActionProvider
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.helper.util.UiUtil
import com.prangbi.android.icoco.model.ico.IcoProfileModel
import kotlinx.android.synthetic.main.ico_profile_activity.*

/**
 * Created by hsg on 2018. 3. 25..
 */
class IcoProfileActivity: AppCompatActivity() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private lateinit var progressDialog: Dialog
    private val model = IcoProfileModel()
    private var icoId = 0L
    private var shareActionProvider: ShareActionProvider? = null

    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.ico_profile_activity)
        mContext = this

        val title = intent.getStringExtra("title")
        icoId = intent.getLongExtra("icoId", 0)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
        progressDialog = UiUtil.makeLoadingDialog(mContext)
        viewPager.adapter = IcoProfileFragmentPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = viewPager.adapter.count
        tabLayout.setupWithViewPager(viewPager)
        getIcoProfile()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        val shareItem = menu?.findItem(R.id.menu_item_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Function
     */
    private fun setShareIntent(urlStr: String?) {
        if (null != urlStr) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Ico Profile")
            shareIntent.putExtra(Intent.EXTRA_TEXT, urlStr)
            shareActionProvider?.setShareIntent(shareIntent)
        }
    }

    /**
     * HTTP API
     */
    private fun getIcoProfile() {
        progressDialog.show()
        model.getIcoProfile(icoId, {
            val icoProfile = model.icoProfile

            val adapter = viewPager.adapter as IcoProfileFragmentPagerAdapter
            adapter.aboutFragment.updateData(icoProfile)
            adapter.teamFragment.updateData(icoProfile?.team)
            adapter.ratingsFragment.updateData(icoProfile?.ratings)

            setShareIntent(icoProfile?.links?.www)
            progressDialog.dismiss()
        }, { errMsg ->
            progressDialog.dismiss()
        })
    }

    /**
     * IcoProfileFragmentPagerAdapter
     */
    inner class IcoProfileFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        val aboutFragment = IcoProfileAboutFragment()
        val teamFragment = IcoProfileTeamFragment()
        val ratingsFragment = IcoProfileRatingsFragment()

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence {
            val title: String
            when (position) {
                0 -> title = "About"
                1 -> title = "Team"
                2 -> title = "Ratings"
                else -> title = ""
            }
            return title
        }

        override fun getItem(position: Int): Fragment {
            val fragment: Fragment
            when (position) {
                0 -> fragment = aboutFragment
                1 -> fragment = teamFragment
                2 -> fragment = ratingsFragment
                else -> fragment = Fragment()
            }
            return fragment
        }
    }
}