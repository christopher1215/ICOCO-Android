package com.prangbi.android.icoco.model.more

import com.prangbi.android.icoco.base.Config
import com.prangbi.android.icoco.helper.httprequest.PrHttpRequest

/**
 * Created by hsg on 2018. 3. 1..
 */
class MoreModel {
    /**
     * Variable
     */
    val moreListAdapter = MoreListAdapter()

    /**
     * Lifecycle
     */
    init {
        val aboutMenu = MoreMenu()
        aboutMenu.title = "About"
        aboutMenu.contentUrlStr = Config.SERVICE_INFO_RAW_URL + "/About.txt"
        aboutMenu.content = "ICOCO v1.1.1\n"
        aboutMenu.content += "Copyright © 2018 Prangbi.\n\n"
        aboutMenu.content += "ICOCO provides crypto currency ICO list and news.\n"
        aboutMenu.content += "ICO list from : https://icobench.com/\n"
        aboutMenu.content += "News from : https://www.ccn.com\n\n"
        aboutMenu.content += "Contact : prangbi@gmail.com"
        moreListAdapter.addItem(aboutMenu)

        val termsMenu = MoreMenu()
        termsMenu.title = "Terms"
        termsMenu.contentUrlStr = Config.SERVICE_INFO_RAW_URL + "/Terms.txt"
        termsMenu.content = "Disclaimers\n : All information on ICOCO serve informational purposes only. ICOCO does not provide investment forecast, recommendations or any consulting for that matter. ICOCO cannot be hold responsible for the users' investment decisions.\n\n"
        termsMenu.content += "Updated: 2018-01-31"
        moreListAdapter.addItem(termsMenu)

        val libraryMenu = MoreMenu()
        libraryMenu.title = "Library"
        libraryMenu.contentUrlStr = Config.SERVICE_INFO_RAW_URL + "/Library.txt"
        libraryMenu.content = "google-gson\n : https://github.com/google/gson\n : A Java serialization/deserialization library to convert Java Objects into JSON and back.\n\n"
        libraryMenu.content += "jsoup\n : https://jsoup.org/\n : Open source Java HTML parser, with DOM, CSS, and jquery-like methods for easy data extraction.\n\n"
        libraryMenu.content += "OkHttp\n : http://square.github.io/okhttp\n : An HTTP+HTTP/2 client for Android and Java applications.\n\n"
        libraryMenu.content += "XML to JSON for Android\n : https://github.com/smart-fun/XmlToJson\n : Android Library for converting XML to JSON and JSON to XML."
        moreListAdapter.addItem(libraryMenu)
    }
}