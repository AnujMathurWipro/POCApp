package com.example.pocapplication.mainscreen

import com.example.pocapplication.models.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository {

    fun getList(): retrofit2.Response<Response> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(FetchListService::class.java)
        /*service.list.enqueue(object : Callback<Response>{
            override fun onFailure(call: Call<Response>, t: Throwable) {

            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {

            }

        })*/
        return service.list.execute()
    }
}