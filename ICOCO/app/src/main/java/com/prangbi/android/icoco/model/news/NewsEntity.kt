package com.prangbi.android.icoco.model.news

import com.google.gson.annotations.SerializedName

/**
 * Created by hsg on 2018. 2. 11..
 */
data class NewsInfo(
        var title: String? = null,
        var link: String? = null,
        var pubDate: String? = null,
        @SerializedName("dc:creator")
        var creator: String? = null,
        var description: String? = null,
        @SerializedName("slash:comments")
        var commentCount: Int? = null,
        var categoryList: List<String>? = null
)
