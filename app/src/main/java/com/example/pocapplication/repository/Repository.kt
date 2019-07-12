package com.example.pocapplication.repository

import androidx.lifecycle.MutableLiveData
import com.example.pocapplication.mainscreen.FetchListService
import com.example.pocapplication.models.MainScreenResult
import com.example.pocapplication.models.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository {

    fun getMainScreenList(errorMessage: (success: Boolean) -> String, isSuccessful: (res: Response?) -> Boolean, liveData: MutableLiveData<MainScreenResult>) {

            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dl.dropboxusercontent.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(FetchListService::class.java)
                val result = service.list.execute()
                var message = errorMessage(result.isSuccessful)
                val value = MainScreenResult()
                if (message.isEmpty()) {
                    val res = result.body()
                    if (isSuccessful(res)) {
                        value.response = res
                    } else {
                        message = "Unable to connect"
                    }
                }
                value.errorMessage = message
                liveData.postValue(value)
            }
            }
}