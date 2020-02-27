package com.example.fleet.viewmodels


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fleet.common.UtilsFunctions
import com.google.gson.JsonObject

class HomeViewModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()

    init {
        val mJsonObject = JsonObject()
        mJsonObject.addProperty("start", 0)
        mJsonObject.addProperty("length", 10)
        if (UtilsFunctions.isNetworkConnectedReturn()) mIsUpdating.postValue(true)

    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }


    override fun isClick() : LiveData<String> {
        return isClick
    }

    override fun clickListener(v : View) {
        isClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }



}