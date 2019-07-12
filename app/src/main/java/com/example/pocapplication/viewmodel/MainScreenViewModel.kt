package com.example.pocapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocapplication.repository.Repository
import com.example.pocapplication.models.RowsItem
import com.example.pocapplication.service.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainScreenViewModel() : ViewModel() {

    private var responseList = MutableLiveData<List<RowsItem?>>()
    private var responseTitle = MutableLiveData<String>()
    private var responseError = MutableLiveData<String>()
    private val service = Service()

    fun getResponseList(): LiveData<List<RowsItem?>> {
        return responseList
    }

    fun getResponseTitle(): LiveData<String> {
        return responseTitle
    }

    fun getResponseError(): LiveData<String> {
        return responseError
    }

    private var job: Job? = null
    fun getResponse() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Repository().getList()
            var message = service.getErrorMessage(response.isSuccessful)
            if(message.isEmpty()) {
                val res = response.body()
                if(service.isSuccessful(res)) {
                    responseList.postValue(res?.rows)
                    responseTitle.postValue(res?.title)
                } else {
                    message = "Unable to connect"
                }
            }
            responseError.postValue(message)
        }
    }
    fun isNetworkAvailable(context: Context?): Boolean {
        return service.isNetworkAvailable(context)
    }
}