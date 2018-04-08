package com.prangbi.android.icoco.controller.etc

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.prangbi.android.icoco.R
import com.prangbi.android.icoco.helper.util.UiUtil
import kotlinx.android.synthetic.main.web_activity.*

/**
 * Created by hsg on 2018. 2. 27..
 */
class WebActivity: AppCompatActivity() {
    /**
     * Variable
     */
    private lateinit var mContext: Activity
    private lateinit var loadingDialog: Dialog
    private var urlStr: String? = null
    private var shareActionProvider: ShareActionProvider? = null

    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(R.layout.web_activity)

        val title = intent.getStringExtra("title")
        urlStr = intent.getStringExtra("url")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
        loadingDialog = UiUtil.makeLoadingDialog(mContext)
        initWebView()
        webView.loadUrl(urlStr)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        val shareItem = menu?.findItem(R.id.menu_item_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        setShareIntent(urlStr)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Function
     */
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object: WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingDialog.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingDialog.dismiss()
            }
        }
    }

    private fun setShareIntent(urlStr: String?) {
        if (null != urlStr) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Ico Profile")
            shareIntent.putExtra(Intent.EXTRA_TEXT, urlStr)
            shareActionProvider?.setShareIntent(shareIntent)
        }
    }
}