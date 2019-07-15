package com.example.pocapplication.models

class BaseResult<T> {
    var errorMessage: String? =  null
    var response: T? = null
}