package com.prangbi.android.icoco.helper.util

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker

/**
 * Created by hsg on 2018. 3. 4..
 */
class NetStatusUtil: BroadcastReceiver() {
    // Definition
    enum class NetworkType {
        None,
        Wifi,
        Mobile
    }

    // Companion
    companion object {
        fun checkInternetPermission(context: Context): Boolean {
            val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
            return PermissionChecker.PERMISSION_GRANTED == permissionCheck
        }

        fun getActiveNetworkType(context: Context): NetworkType {
            var networkType = NetworkType.None
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            when (connectivityManager.activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> networkType = NetworkType.Wifi
                ConnectivityManager.TYPE_MOBILE -> networkType = NetworkType.Mobile
            }
            return networkType
        }

        fun canConnectToInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

    // Broadcast Receiver
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if ("android.net.conn.CONNECTIVITY_CHANGE" == action) {
            val connChangedIntent = Intent("com.prangbi.android.icoco.CONNECTIVITY_CHANGE")
            context.sendBroadcast(connChangedIntent)
        }
    }
}