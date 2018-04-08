package com.prangbi.android.icoco.model.ico

/**
 * Created by hsg on 2018. 2. 7..
 */

enum class IcoStatus {
    none,
    active,
    ongoing,
    upcoming,
    ended
}

data class IcoSummaryList(
        var icos: Int = 0,
        var pages: Int = 0,
        var currentPage: Int = 0,
        var results: List<IcoSummary>? = null
)

data class IcoSummary(
        var id: Long = 0L,
        var name: String? = null,
        var url: String? = null,
        var logo: String? = null,
        var desc: String? = null,
        var rating: Float = 0f,
        var premium: Int = 0,
        var raised: Int = 0,
        var dates: IcoDates? = null
)

data class IcoDates(
        var preIcoStart: String? = null,
        var preIcoEnd: String? = null,
        var icoStart: String? = null,
        var icoEnd: String? = null
)