package com.prangbi.android.icoco.helper.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hsg on 2018. 3. 4..
 */
class DateTimeUtil {
    companion object {
        // Date with String
        fun date(originStr: String, targetFormat: String, locale: Locale?): Date? {
            var date: Date? = null
            val locale = if (null != locale) locale else Locale.getDefault()
            val sdf = SimpleDateFormat(targetFormat, locale)
            sdf.isLenient = false
            try {
                date = sdf.parse(originStr)
            } catch (e: Exception) {

            }
            return date
        }

        // String with Date
        fun string(originDate: Date, targetFormat: String, locale: Locale?): String? {
            var dateStr: String? = null
            val locale = if (null != locale) locale else Locale.getDefault()
            val sdf = SimpleDateFormat(targetFormat, locale)
            sdf.isLenient = false
            try {
                dateStr = sdf.format(originDate)
            } catch (e: Exception) {

            }
            return dateStr
        }

        // String with String
        fun string(originStr: String, originFormat: String, targetFormat: String, locale: Locale?): String? {
            var dateStr: String? = null
            val locale = if (null != locale) locale else Locale.getDefault()
            val originSdf = SimpleDateFormat(originFormat, locale)
            originSdf.isLenient = false
            val targetSdf = SimpleDateFormat(targetFormat, locale)
            targetSdf.isLenient = false
            try {
                val originDate = originSdf.parse(originStr)
                dateStr = targetSdf.format(originDate)
            } catch (e: Exception) {

            }
            return dateStr
        }

        // Custom
        fun changeIcoDateFormat(dateStr: String?): String? {
            var targetDateStr: String? = null
            if (null != dateStr) {
                targetDateStr = string(
                        dateStr,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyy-MM-dd HH:mm",
                        null
                )
            }
            return targetDateStr
        }

        fun changeNewsPupDateFormat(dateStr: String?): String? {
            var pubDateStr: String? = null
            if (null != dateStr) {
                pubDateStr = string(
                        dateStr,
                        "E, d MMM yyyy HH:mm:ss Z",
                        "yyyy-MM-dd HH:mm",
                        Locale.US
                )
            }
            return pubDateStr
        }
    }
}