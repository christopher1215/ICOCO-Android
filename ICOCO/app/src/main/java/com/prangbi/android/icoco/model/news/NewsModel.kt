package com.prangbi.android.icoco.model.news

import com.prangbi.android.icoco.helper.httprequest.PrHttpRequest
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by hsg on 2018. 2. 11..
 */
class NewsModel {
    // Variable
    private val request = PrHttpRequest()
    private var currentPage = 1
    val listAdapter = NewsListAdapter()
    var noMorePage = false

    // Function
    fun isMorePage(): Boolean {
        return !noMorePage
    }

    // Request
    fun getFirstNewsListPage(success: (() -> Unit)?, failure: ((String?) -> Unit)?) {
        val page = 1
        noMorePage = false
        request.ccn.getNewsList(page, { statusCode, newsList ->
            currentPage = page
            launch(UI) {
                listAdapter.removeAll()
            }
            if (null == newsList || 0 == newsList.count()) {
                noMorePage = true
            } else {
                launch(UI) {
                    listAdapter.addItems(newsList)
                }
            }
            if (null != success) {
                launch(UI) {
                    success()
                }
            }
        }, { statusCode, errMsg ->
            if (null != failure) {
                launch(UI) {
                    failure(errMsg)
                }
            }
        })
    }

    fun getNextNewsListPage(success: (() -> Unit)?, failure: ((String?) -> Unit)?) {
        val page = currentPage + 1
        request.ccn.getNewsList(page, { statusCode, newsList ->
            currentPage = page
            if (1 >= page) {
                launch(UI) {
                    listAdapter.removeAll()
                }
            }
            if (null == newsList || 0 == newsList.count()) {
                noMorePage = true
            } else {
                launch(UI) {
                    listAdapter.addItems(newsList)
                }
            }
            if (null != success) {
                launch(UI) {
                    success()
                }
            }
        }, { statusCode, errMsg ->
            if (null != failure) {
                launch(UI) {
                    failure(errMsg)
                }
            }
        })
    }
}