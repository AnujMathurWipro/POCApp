package com.example.pocapplication.repository

import androidx.lifecycle.MutableLiveData
import com.example.pocapplication.mainscreen.FetchListService
import com.example.pocapplication.models.BaseResult
import com.example.pocapplication.models.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository {

    fun getMainScreenList(liveData: MutableLiveData<BaseResult<Response>>
    ) {
        val retrofit = createRetrofit("https://dl.dropboxusercontent.com")
        val service = retrofit.create(FetchListService::class.java)
        val call = service.list
        getResponseFromServer(call, liveData)
    }

    private fun <T> getResponseFromServer(
        call: Call<T>,
        liveData: MutableLiveData<BaseResult<T>>
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            handleResponse(call.execute(), liveData)
        }
    }

    private fun <T> handleResponse(
        result: retrofit2.Response<T>,
        liveData: MutableLiveData<BaseResult<T>>
    ) {

        val value = BaseResult<T>()
        if (result.isSuccessful) {
            val res = result.body()
                value.response = res
        }
        liveData.postValue(value)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}