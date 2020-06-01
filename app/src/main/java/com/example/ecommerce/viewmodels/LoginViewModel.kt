package com.example.ecommerce.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.repositories.LoginRepository
import com.google.gson.JsonObject

class LoginViewModel : BaseViewModel() {
    private var emialExistenceResponse = MutableLiveData<LoginResponse>()
    private var loginRepository = LoginRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        emialExistenceResponse = loginRepository.getLoginData(null)
    }
    fun checkEmailExistence() : LiveData<LoginResponse> {
        return emialExistenceResponse!!
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]

    }

    fun checkPhoneExistence(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            //emialExistenceResponse = loginRepository.checkPhoneExistence(mJsonObject)
            emialExistenceResponse = loginRepository.getLoginData(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }

}