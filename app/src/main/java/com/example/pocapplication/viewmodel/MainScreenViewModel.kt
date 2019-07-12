package com.example.pocapplication.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocapplication.models.MainScreenResult
import com.example.pocapplication.models.Response
import com.example.pocapplication.repository.Repository

class MainScreenViewModel : ViewModel() {

    private var result = MutableLiveData<MainScreenResult>()

    fun getResult(): LiveData<MainScreenResult> {
        return result
    }

    fun getResponse() {
        Repository().getMainScreenList({ getErrorMessage(it) }, { isSuccessful(it) }, result)
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    fun getErrorMessage(isSuccessful: Boolean): String {
        return if (isSuccessful) {
            ""
        } else {
            "There was some problem with the request. Please try again."
        }
    }

    fun isSuccessful(res: Response?): Boolean {
        return res?.rows != null && res.rows.isNotEmpty()
    }

    fun isSuccessful(it: MainScreenResult?): Boolean {
        return isSuccessful(it?.response)
    }
}