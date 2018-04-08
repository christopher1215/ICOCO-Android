package com.prangbi.android.icoco.controller.ico

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.model.ico.IcoStatus
import kotlinx.android.synthetic.main.ico_fragment.*

/**
 * Created by hsg on 2018. 2. 27..
 */
class IcoFragment: Fragment() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity

    /**
     * Lifecycle
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.ico_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity

        viewPager.adapter = IcoFragmentPagerAdapter(childFragmentManager)
        viewPager.offscreenPageLimit = viewPager.adapter.count
        tabLayout.setupWithViewPager(viewPager)
    }

    /**
     * IcoFragmentPagerAdapter
     */
    private class IcoFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence {
            var title: String? = null
            when (position) {
                0 -> title = "Ongoing"
                1 -> title = "Upcoming"
                2 -> title = "Ended"
                else -> title = ""
            }
            return title
        }

        override fun getItem(position: Int): Fragment {
            val icoSubFragment = IcoSubFragment()
            when (position) {
                0 -> icoSubFragment.setData(IcoStatus.ongoing)
                1 -> icoSubFragment.setData(IcoStatus.upcoming)
                2 -> icoSubFragment.setData(IcoStatus.ended)
            }
            return icoSubFragment
        }
    }
}