package com.example.pocapplication.mainscreen

import com.example.pocapplication.models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FetchListService {
    @get:GET("/s/2iodh4vg0eortkl/facts.json")
    val list: Call<Response>
}