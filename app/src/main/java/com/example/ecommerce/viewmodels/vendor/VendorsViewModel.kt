package com.example.ecommerce.viewmodels.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.vendor.VendorListResponse
import com.example.ecommerce.repositories.vendor.VendorsRepository
import com.example.ecommerce.viewmodels.BaseViewModel

class VendorsViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()

    private var favList = MutableLiveData<VendorListResponse>()
    private var vendorRepository = VendorsRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            favList = vendorRepository.vendorList("", "", "")
        }

    }

    fun getVendorListRes(): LiveData<VendorListResponse> {
        return favList
    }


    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    /* fun addRemoveCart(mJsonObject : JsonObject) {
         if (UtilsFunctions.isNetworkConnected()) {
             addressDetail = cartRepository.addAddress(mJsonObject)
             mIsUpdating.postValue(true)
         }
     }*/

    fun getVendorList(id: String, lat: String, longi: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            favList = vendorRepository.vendorList(id, lat, longi)
            mIsUpdating.postValue(true)
        }

    }

}