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

    fun getMainScreenList(
        errorMessage: (success: Boolean) -> String,
        isSuccessful: (res: Response?) -> Boolean,
        liveData: MutableLiveData<BaseResult<Response>>
    ) {
        val retrofit = createRetrofit("https://dl.dropboxusercontent.com")
        val service = retrofit.create(FetchListService::class.java)
        val call = service.list
        getResponseFromServer(call, errorMessage, isSuccessful, liveData)
    }

    private fun <T> getResponseFromServer(
        call: Call<T>,
        errorMessage: (success: Boolean) -> String,
        successful: (res: T?) -> Boolean,
        liveData: MutableLiveData<BaseResult<T>>
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            handleResponse(call.execute(), errorMessage, successful, liveData)
        }
    }

    private fun <T> handleResponse(
        result: retrofit2.Response<T>,
        errorMessage: (success: Boolean) -> String,
        successful: (res: T?) -> Boolean,
        liveData: MutableLiveData<BaseResult<T>>
    ) {

        var message = errorMessage(result.isSuccessful)
        val value = BaseResult<T>()
        if (message.isEmpty()) {
            val res = result.body()
            if (successful(res)) {
                value.response = res
            } else {
                message = "Unable to connect"
            }
        }
        value.errorMessage = message
        liveData.postValue(value)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}