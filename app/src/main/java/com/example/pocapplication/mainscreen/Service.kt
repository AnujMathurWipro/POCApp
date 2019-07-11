package com.example.pocapplication.mainscreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.pocapplication.models.Response

open class Service {
    fun isNetworkAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    fun getErrorMessage(isSuccessful: Boolean):String {
        return if(isSuccessful) {
            ""
        } else {
            "There was some problem with the request. Please try again."
        }
    }

    fun isSuccessful(res: Response?): Boolean {
        return res?.rows != null && res.rows.isNotEmpty()
    }
}