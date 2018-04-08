package com.prangbi.android.icoco.helper.util

import android.app.Dialog
import android.content.Context
import android.support.v7.app.ActionBar
import android.widget.ProgressBar
import com.prangbi.android.icoco.R

/**
 * Created by hsg on 2018. 3. 4..
 */
class UiUtil {
    companion object {
        fun makeLoadingDialog(context: Context): Dialog {
            val dialog = Dialog(context, R.style.ProgressDialog)
            dialog.addContentView(
                    ProgressBar(context),
                    ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            )
            return dialog
        }
    }
}