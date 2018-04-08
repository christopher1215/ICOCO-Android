package com.prangbi.android.icoco.helper.httprequest

import android.util.Base64
import com.google.gson.Gson
import com.prangbi.android.icoco.base.Config
import com.prangbi.android.icoco.model.ico.IcoProfile
import com.prangbi.android.icoco.model.ico.IcoSummaryList
import okhttp3.*
import java.io.IOException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Created by hsg on 2018. 2. 11..
 */
class RequestIcoBench {
    // Companion
    companion object {
        const val ICO_BENCH_API_URL = Config.ICO_BENCH_SERVER_URL + "/api/v1"
    }

    // ResponseResult
    private class ResponseJsonError {
        var error: String? = null
    }

    // Variable
    private val client = OkHttpClient()

    // Common
    fun requestJson(
            method: String, urlStr: String,
            params: Map<String, Any>,
            success: ((Int?, Any?) -> Unit)?,
            failure: ((Int?, String?) -> Unit)?)
    {
        val gson = Gson()
        val keyBytes = Config.ICO_BENCH_API_PRIVATE_KEY.toByteArray()
        val jsonBytes = gson.toJson(params).toByteArray()
        val algorithmString = "HmacSHA384"
        val mac = Mac.getInstance(algorithmString)
        mac.init(SecretKeySpec(keyBytes, algorithmString))
        val sig = Base64.encodeToString(mac.doFinal(jsonBytes), Base64.NO_WRAP)
        val mediaType = MediaType.parse("application/json;charset=UTF-8")
        val body = RequestBody.create(mediaType, jsonBytes)

        val requestBuilder = Request.Builder().url(urlStr).method(method, body)
        requestBuilder.header("Content-Type", "application/json;charset=UTF-8")
        requestBuilder.header("X-ICObench-Key", Config.ICO_BENCH_API_PUBLIC_KEY)
        requestBuilder.header("Content-Length", sig.count().toString())
        requestBuilder.header("X-ICObench-Sig", sig)
        val request = requestBuilder.build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val statusCode = response.code()
                val bodyStr = response.body()?.string()
                val errMsg = gson.fromJson(bodyStr, ResponseJsonError::class.java).error
                if (null == errMsg) {
                    if (null != success) {
                        success(statusCode, bodyStr)
                    }
                } else {
                    if (null != failure) {
                        failure(statusCode, errMsg)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                if (null != failure) {
                    failure(null, e.localizedMessage)
                }
            }
        })
    }

    // ICO Info
    fun getIcoList(
            status: String, page: Int,
            success: ((Int?, IcoSummaryList?) -> Unit)?,
            failure: ((Int?, String?) -> Unit)?)
    {
        val urlStr = ICO_BENCH_API_URL + "/icos/all"
        val params = mutableMapOf<String, Any>()
        params["status"] = status
        params["page"] = page
        requestJson("POST", urlStr, params, { statusCode, resultValue -> Unit
            if ((null != success) && (resultValue is String)) {
                val gson = Gson()
                val resultObject = gson.fromJson(resultValue, IcoSummaryList::class.java)
                success(statusCode, resultObject)
            }
        }, { statusCode, errMsg -> Unit
            if (null != failure) {
                failure(statusCode, errMsg)
            }
        })
    }

    fun getIcoProfile(
            icoId: Long,
            success: ((Int?, IcoProfile?) -> Unit)?,
            failure: ((Int?, String?) -> Unit)?)
    {
        val urlStr = ICO_BENCH_API_URL + "/ico/" + icoId.toString()
        val params = mutableMapOf<String, Any>()
        requestJson("POST", urlStr, params, { statusCode, resultValue -> Unit
            if ((null != success) && (resultValue is String)) {
                val gson = Gson()
                val resultObject = gson.fromJson(resultValue, IcoProfile::class.java)
                success(statusCode, resultObject)
            }
        }, { statusCode, errMsg -> Unit
            if (null != failure) {
                failure(statusCode, errMsg)
            }
        })
    }
}