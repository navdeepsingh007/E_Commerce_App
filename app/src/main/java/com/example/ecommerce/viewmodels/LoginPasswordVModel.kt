package com.example.ecommerce.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.repositories.LoginRepository
import org.json.JSONObject
import com.google.gson.JsonParser



class LoginPasswordVModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()
    private var loginRepository = LoginRepository()
    private var loginData : MutableLiveData<LoginResponse>? = null

    init {
        loginData = loginRepository.getLoginData(null)

    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }




    fun getLoginResponse() : LiveData<LoginResponse> {
        return loginData!!
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun login(mJsonObject : JSONObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            val jsonObject = JsonParser().parse(mJsonObject.toString()).asJsonObject
            //var jsonObject= mJsonObject as JsonObject
            loginData = loginRepository.getLoginData(jsonObject)
            mIsUpdating.postValue(true)
        }
    }

}