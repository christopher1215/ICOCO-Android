package com.prangbi.android.icoco.controller.more

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.Fragment
import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.model.more.MoreModel
import kotlinx.android.synthetic.main.more_fragment.*

/**
 * Created by hsg on 2018. 2. 27..
 */
class MoreFragment: Fragment() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var progressDialog: Dialog
    private val model = MoreModel()
    private var canConnect = false
    private var isLoading = false

    /**
     * Lifecycle
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.more_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity

        listView.setAdapter(model.moreListAdapter)
    }
}