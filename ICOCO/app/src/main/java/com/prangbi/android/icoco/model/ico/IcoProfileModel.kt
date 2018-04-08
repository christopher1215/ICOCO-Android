package com.prangbi.android.icoco.model.ico

import com.prangbi.android.icoco.helper.httprequest.PrHttpRequest
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by hsg on 2018. 2. 7..
 */
class IcoProfileModel {
    // Variable
    private val request = PrHttpRequest()
    var icoProfile: IcoProfile? = null

    // Function
    fun getIcoProfile(icoId: Long, success: (() -> Unit)?, failure: ((String?) -> Unit)?) {
        request.icoBench.getIcoProfile(icoId, { statusCode, icoProfile -> Unit
            this.icoProfile = icoProfile
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