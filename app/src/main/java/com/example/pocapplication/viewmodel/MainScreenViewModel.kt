package com.example.pocapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocapplication.models.BaseResult
import com.example.pocapplication.models.Response
import com.example.pocapplication.repository.Repository

class MainScreenViewModel : ViewModel() {

    private var result = MutableLiveData<BaseResult<Response>>()

    fun getResult(): LiveData<BaseResult<Response>> {
        return result
    }

    fun getResponse(force: Boolean) {
        if(force || result.value == null)
            Repository().getMainScreenList(result)
    }

    fun getErrorMessage(): String {
        return "There was some problem with the request. Please try again."
    }

    fun isSuccessful(res: Response?): Boolean {
        return res?.rows != null && res.rows.isNotEmpty()
    }
}