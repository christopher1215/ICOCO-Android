package com.prangbi.android.icoco.helper.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.AsyncTask
import android.util.LruCache
import android.widget.ImageView
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.URL

/**
 * Created by hsg on 2018. 3. 20..
 */
class ImageUtil {
    companion object {
        private val maxMemory = Runtime.getRuntime().maxMemory() / 1024
        private val cacheSize = maxMemory / 8
        private val memCache = object: LruCache<String, Bitmap>(cacheSize.toInt()) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                var size = 0
                if (null != bitmap) {
                    size = bitmap.byteCount / 1024
                }
                return size
            }
        }

        fun getBitmap(urlStr: String?): Bitmap? {
            var bitmap: Bitmap? = null
            var inputStream: InputStream? = null
            try {
                val url = URL(urlStr)
                inputStream = url.openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
            } finally {
                if (null != inputStream) {
                    inputStream.close()
                }
            }
            return bitmap
        }

        fun resizeBitmap(origin: Bitmap, scale: Float): Bitmap {
            val width = origin.width
            val height = origin.height
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            return Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        }

        fun addBitmapToMemCache(key: String?, bitmap: Bitmap?) {
            if (null != key && null != bitmap && null == getBitmapFromMemCache(key)) {
                memCache.put(key, bitmap)
            }
        }

        fun getBitmapFromMemCache(key: String?): Bitmap? {
            var bitmap: Bitmap? = null
            if (null != key) {
                bitmap = memCache.get(key)
            }
            return bitmap
        }
    }

    /**
     * ImageAsyncTask
     */
    class ImageAsyncTask: AsyncTask<String?, Void, Bitmap?> {
        private var weakImageView: WeakReference<ImageView>? = null

        constructor(imageView: ImageView) {
            weakImageView = WeakReference(imageView)
        }

        override fun doInBackground(vararg args: String?): Bitmap? {
            var bitmap: Bitmap? = null
            val urlStr = if (false == args.isEmpty()) args[0] else null
            bitmap = getBitmapFromMemCache(urlStr)
            if (null == bitmap && null != urlStr) {
                bitmap = ImageUtil.getBitmap(urlStr)
                addBitmapToMemCache(urlStr, bitmap)
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            weakImageView?.get()?.setImageBitmap(result)
        }
    }
}