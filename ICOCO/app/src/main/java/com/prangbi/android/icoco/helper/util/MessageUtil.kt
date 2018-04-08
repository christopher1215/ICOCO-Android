package com.prangbi.android.icoco.helper.util

import android.content.Context
import android.widget.Toast

/**
 * Created by hsg on 2018. 3. 4..
 */
class MessageUtil {
    companion object {
        private var toast: Toast? = null

        // UI Function
        fun showToast(context: Context, message: String) {
            toast?.let {
                toast?.cancel()
                toast = null
            }

            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }
}