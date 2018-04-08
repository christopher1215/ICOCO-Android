package com.prangbi.android.icoco.model.ico

import com.prangbi.android.icoco.helper.httprequest.PrHttpRequest
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by hsg on 2018. 2. 7..
 */

class IcoSubModel {
    // Variable
    private val request = PrHttpRequest()
    var icoStatus: IcoStatus? = null
    val listAdapter = IcoListAdapter()
    private var icos: Int = 0
    private var pages: Int = 0
    private var currentPage: Int = 0
    private var noMorePage: Boolean = false

    // Function
    fun isMorePage(): Boolean {
        return !noMorePage
    }

    // Reuqest
    fun getFirstIcoListPage(success: (() -> Unit)?, failure: ((String?) -> Unit)?) {
        if (null == icoStatus) {
            if (null != failure) {
                launch(UI) {
                    failure("IcoStatus did not initialized.")
                }
            }
            return
        }

        val page = 0
        noMorePage = false
        request.icoBench.getIcoList(icoStatus!!.name, page, { statusCode, icoSummaryList ->
            launch(UI) {
                listAdapter.removeAll()
            }
            if (null != icoSummaryList) {
                icos = icoSummaryList.icos
                pages = icoSummaryList.pages
                currentPage = icoSummaryList.currentPage
                val results = icoSummaryList.results
                if ((null != results) && (0 < results.count())) {
                    launch(UI) {
                        listAdapter.addItems(results)
                    }
                } else {
                    noMorePage = true
                }
            } else {
                icos = 0
                pages = 0
                currentPage = 0
                noMorePage = true
            }
            if (null != success) {
                launch(UI) {
                    success()
                }
            }
        }, { statusCode, errMsg -> Unit
            if (null != failure) {
                launch(UI) {
                    failure(errMsg)
                }
            }
        })
    }

    fun getNextIcoListPage(success: (() -> Unit)?, failure: ((String?) -> Unit)?) {
        if (null == icoStatus) {
            if (null != failure) {
                launch(UI) {
                    failure("IcoStatus did not initialized.")
                }
            }
            return
        }

        val page = currentPage + 1
        request.icoBench.getIcoList(icoStatus!!.name, page, { statusCode, icoSummaryList ->
            if (null != icoSummaryList) {
                icos = icoSummaryList.icos
                pages = icoSummaryList.pages
                currentPage = icoSummaryList.currentPage
                val results = icoSummaryList.results
                if ((null != results) && (0 < results.count())) {
                    launch(UI) {
                        listAdapter.addItems(results)
                    }
                } else {
                    noMorePage = true
                }
            } else {
                noMorePage = true
            }
            if (null != success) {
                launch(UI) {
                    success()
                }
            }
        }, { statusCode, errMsg -> Unit
            if (null != failure) {
                launch(UI) {
                    failure(errMsg)
                }
            }
        })
    }
}
