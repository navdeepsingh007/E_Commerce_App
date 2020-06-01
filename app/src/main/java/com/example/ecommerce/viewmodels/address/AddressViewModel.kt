package com.example.ecommerce.viewmodels.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressListResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.repositories.address.AddressRepository
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class AddressViewModel : BaseViewModel() {
    private var data : MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()

    private var addressDetailList = MutableLiveData<AddressListResponse>()
    private var addressRepository = AddressRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            addressDetailList = addressRepository.getAddressList()
            deleteAddress= addressRepository.deleteAddress("")
            addressDetail = addressRepository.addAddress(null)
        }

    }

    fun getAddressList() : LiveData<AddressListResponse> {
        return addressDetailList!!
    }
    fun deleteAddressRes() : LiveData<CommonModel> {
        return deleteAddress!!
    }

    fun getAddress() : LiveData<AddressResponse> {
        return addressDetail!!
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

    fun addAddressDetail(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            addressDetail = addressRepository.addAddress(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }

    fun updateAddressDetail(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            addressDetail = addressRepository.updateAddress(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }
    fun addressList() {
        if (UtilsFunctions.isNetworkConnected()) {
            addressDetailList = addressRepository.getAddressList()
            mIsUpdating.postValue(true)
        }

    }
    fun deleteAddress(id : String?) {
        if (UtilsFunctions.isNetworkConnected()) {
            deleteAddress = addressRepository.deleteAddress(id)
            mIsUpdating.postValue(true)
        }

    }

}