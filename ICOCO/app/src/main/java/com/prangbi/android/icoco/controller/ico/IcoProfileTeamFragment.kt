package com.prangbi.android.icoco.controller.ico

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.model.ico.IcoProfileTeamListAdapter
import com.prangbi.android.icoco.model.ico.IcoTeam
import kotlinx.android.synthetic.main.ico_sub_fragment.*

/**
 * Created by hsg on 2018. 3. 25..
 */
class IcoProfileTeamFragment: Fragment() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private val listAdapter = IcoProfileTeamListAdapter()

    /**
     * Lifecycle
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.ico_profile_sub_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity
        listView.adapter = listAdapter
    }

    /**
     * Function
     */
    fun updateData(icoTeamList: List<IcoTeam>?) {
        listAdapter.removeAll()
        if (null != icoTeamList) {
            listAdapter.addItems(icoTeamList)
        }
        listAdapter.notifyDataSetChanged()
    }
}