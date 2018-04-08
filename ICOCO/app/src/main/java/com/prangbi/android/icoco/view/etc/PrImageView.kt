package com.prangbi.android.icoco.view.etc

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.LruCache
import android.widget.ImageView
import com.prangbi.android.icoco.helper.util.ImageUtil

/**
 * Created by hsg on 2018. 3. 21..
 */
class PrImageView: ImageView {
    /**
     * Variable
     */
    private var urlStr: String? = null
    private var imageAsyncTask: ImageUtil.ImageAsyncTask? = null

    /**
     * Lifecycle
     */
    constructor(context: Context): super(context) {

    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {

    }

    /**
     * Function
     */
    fun loadImage(urlStr: String?) {
        if (null != imageAsyncTask) {
            imageAsyncTask?.cancel(true)
            imageAsyncTask = null
        }

        if (null == urlStr) {
            this.urlStr = null
            setImageBitmap(null)
            return
        }

        if (urlStr != this.urlStr) {
            setImageBitmap(null)
        }

        this.urlStr = urlStr
        imageAsyncTask = ImageUtil.ImageAsyncTask(this)
        imageAsyncTask?.execute(urlStr)
    }
}