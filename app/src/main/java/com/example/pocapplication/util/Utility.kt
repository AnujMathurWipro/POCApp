package com.example.pocapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Utility {
    companion object {
        fun isNetworkAvailable(context: Context?): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }
    }
}