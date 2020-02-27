package com.example.fleet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.repositories.LoginRepository
import com.google.gson.JsonObject
import org.json.JSONObject

class LoginViewModel : BaseViewModel() {
    private var emialExistenceResponse = MutableLiveData<CommonModel>()
    private var loginRepository = LoginRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        emialExistenceResponse = loginRepository.checkPhoneExistence(null)
    }
    fun checkEmailExistence() : LiveData<CommonModel> {
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
            emialExistenceResponse = loginRepository.checkPhoneExistence(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }

}