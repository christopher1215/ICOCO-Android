package com.prangbi.android.icoco.controller.news

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.Fragment
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.controller.etc.WebActivity
import com.prangbi.android.icoco.helper.util.NetStatusUtil
import com.prangbi.android.icoco.helper.util.UiUtil
import com.prangbi.android.icoco.model.news.NewsInfo
import com.prangbi.android.icoco.model.news.NewsModel
import kotlinx.android.synthetic.main.news_fragment.*
import java.net.URL

/**
 * Created by hsg on 2018. 2. 27..
 */
class NewsFragment: Fragment() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var progressDialog: Dialog
    private val model = NewsModel()
    private var canConnect = false
    private var isLoading = false

    /**
     * Lifecycle
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity
        initBroadcastReciever()
        initListView()

        progressDialog = UiUtil.makeLoadingDialog(mContext)
        canConnect = NetStatusUtil.canConnectToInternet(mContext)
        getFirstNewsListPage()
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
            val item = parent.adapter.getItem(position) as NewsInfo
            val link = item.link
            if (null != link) {
                val host = URL(link).host
                val intent = Intent(mContext, WebActivity::class.java)
                intent.putExtra("title", host)
                intent.putExtra("url", link)
                startActivity(intent)
            }
        }
        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (false == model.isMorePage() || true == isLoading || false == canConnect) {
                    return
                }

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    getNextNewsListPage()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }

        })
        refreshLayout.setOnRefreshListener {
            getFirstNewsListPage()
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

    private fun getFirstNewsListPage() {
        isLoading = true
        progressDialog.show()
        model.getFirstNewsListPage({
            commonCompletion()
        }, { errMsg ->
            commonCompletion()
        })
    }

    private fun getNextNewsListPage() {
        isLoading = true
        progressDialog.show()
        model.getNextNewsListPage({
            commonCompletion()
        }, { errMsg ->
            commonCompletion()
        })
    }
}