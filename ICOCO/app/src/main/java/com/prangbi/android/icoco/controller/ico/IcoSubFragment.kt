package com.prangbi.android.icoco.controller.ico

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.helper.util.NetStatusUtil
import com.prangbi.android.icoco.helper.util.UiUtil
import com.prangbi.android.icoco.model.ico.IcoStatus
import com.prangbi.android.icoco.model.ico.IcoSubModel
import com.prangbi.android.icoco.model.ico.IcoSummary
import kotlinx.android.synthetic.main.ico_sub_fragment.*

/**
 * Created by hsg on 2018. 3. 17..
 */
class IcoSubFragment: Fragment() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var progressDialog: Dialog
    private val model = IcoSubModel()
    private var canConnect = false
    private var isLoading = false

    /**
     * Lifecycle
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.ico_sub_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity
        initBroadcastReciever()
        initListView()

        progressDialog = UiUtil.makeLoadingDialog(mContext)
        canConnect = NetStatusUtil.canConnectToInternet(mContext)
        getFirstIcoListPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (null != broadcastReceiver) {
            mContext.unregisterReceiver(broadcastReceiver)
        }
    }

    /**
     * Function
     */
    fun setData(icoStatus: IcoStatus) {
        model.icoStatus = icoStatus
    }

    private fun initBroadcastReciever() {
        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.getAction()
                if ("com.prangbi.android.icoco.CONNECTIVITY_CHANGE" == action) {
                    canConnect = NetStatusUtil.canConnectToInternet(mContext)
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.prangbi.android.icoco.CONNECTIVITY_CHANGE")
        if (null != broadcastReceiver) {
            mContext.registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    private fun initListView() {
        listView.adapter = model.listAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.adapter.getItem(position) as IcoSummary
            val intent = Intent(mContext, IcoProfileActivity::class.java)
            intent.putExtra("title", item.name)
            intent.putExtra("icoId", item.id)
            startActivity(intent)
        }
        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (false == model.isMorePage() || true == isLoading || false == canConnect) {
                    return
                }

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    getNextIcoListPage()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }

        })
        refreshLayout.setOnRefreshListener {
            getFirstIcoListPage()
        }
    }

    /**
     * HTTP API
     */
    private fun commonCompletion() {
        refreshLayout.isRefreshing = false
        model.listAdapter.notifyDataSetChanged()
        progressDialog.dismiss()
        isLoading = false
    }

    private fun getFirstIcoListPage() {
        isLoading = true
        progressDialog.show()
        model.getFirstIcoListPage({
            commonCompletion()
        }, { errMsg ->
            commonCompletion()
        })
    }

    private fun getNextIcoListPage() {
        isLoading = true
        progressDialog.show()
        model.getNextIcoListPage({
            commonCompletion()
        }, { errMsg ->
            commonCompletion()
        })
    }
}