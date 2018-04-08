package com.prangbi.android.icoco.helper.httprequest

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prangbi.android.icoco.base.Config
import com.prangbi.android.icoco.model.news.NewsInfo
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException

class RequestCcn {
    // Companion
    companion object {
        const val CCN_API_URL = Config.CCN_SERVER_URL + "/feed"
    }

    // Variable
    private val client = OkHttpClient()

    // Common
    fun requestJson(
            urlStr: String,
            success: ((Int?, Any?) -> Unit)?,
            failure: ((Int?, String?) -> Unit)?)
    {
        val requestBuilder = Request.Builder().url(urlStr).get()
        requestBuilder.header("Content-Type", "application/json;charset=UTF-8")
        val request = requestBuilder.build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val statusCode = response.code()
                val bodyStr = response.body()?.string()
                if (null != success) {
                    success(statusCode, bodyStr)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                if (null != failure) {
                    failure(null, e.localizedMessage)
                }
            }
        })
    }

    // News List
    fun getNewsList(
            page: Int,
            success: ((Int?, List<NewsInfo>?) -> Unit)?,
            failure: ((Int?, String?) -> Unit)?)
    {
        val gson = Gson()
        val urlStr = "$CCN_API_URL?paged=$page"
        requestJson(urlStr, { statusCode, resultValue -> Unit
            if ((null != success) && (resultValue is String)) {
                val resultObject: MutableList<NewsInfo> = mutableListOf()
                try {
                    val xml = XmlToJson.Builder(resultValue).build()
                    val items = xml.toJson()?.getJSONObject("rss")?.getJSONObject("channel")?.getJSONArray("item")
                    if (null != items) {
                        for (index in 0 until items.length()) {
                            val item = items[index]
                            val jsonStr = item.toString()
                            val obj = gson.fromJson(jsonStr, NewsInfo::class.java)
                            if (null != obj) {
                                obj.description = Jsoup.parse(obj.description).text()
                                resultObject.add(obj)
                            }
                        }
                    }
                } catch (e: Exception) {
                }
                success(statusCode, resultObject)
            }
        }, { statusCode, errMsg -> Unit
            if (null != failure) {
                failure(statusCode, errMsg)
            }
        })
    }
}