package com.example.ecommerce.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View

class OTPVerificationModel : BaseViewModel() {
    override fun isLoading() : LiveData<Boolean> {
    return mIsUpdating   }

    override fun isClick() : LiveData<String> {
        return btnClick     }

    override fun clickListener(v : View) {
        btnClick.value=v.resources.getResourceName(v.id).split("/")[1]
    }

    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()




    val loading : LiveData<Boolean>
        get() = mIsUpdating






}