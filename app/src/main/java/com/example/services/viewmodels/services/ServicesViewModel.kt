package com.example.services.viewmodels.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.services.common.UtilsFunctions
import com.example.services.model.CommonModel
import com.example.services.model.LoginResponse
import com.example.services.model.address.AddressResponse
import com.example.services.model.services.DateSlotsResponse
import com.example.services.model.services.ServicesDetailResponse
import com.example.services.model.services.ServicesListResponse
import com.example.services.model.services.TimeSlotsResponse
import com.example.services.repositories.services.ServicesRepository
import com.example.services.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class ServicesViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var servicesList = MutableLiveData<ServicesListResponse>()
    private var servicesDetail = MutableLiveData<ServicesDetailResponse>()
    private var carRes = MutableLiveData<CommonModel>()
    private var favRes = MutableLiveData<CommonModel>()
    private var timeSlotsList = MutableLiveData<TimeSlotsResponse>()
    private var dateSlots = MutableLiveData<DateSlotsResponse>()
    private var servicesRepository = ServicesRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            servicesList = servicesRepository.getServicesList(null)
            servicesDetail = servicesRepository.getServiceDetail("")
            carRes = servicesRepository.addRemoveCart(null)
            favRes = servicesRepository.addRemoveFav(null)
            timeSlotsList = servicesRepository.getTimeSlots(null)
             dateSlots= servicesRepository.getDateSlots()
        }

    }

    fun addRemoveCartRes(): LiveData<CommonModel> {
        return carRes
    }

    fun addRemovefavRes(): LiveData<CommonModel> {
        return favRes
    }

    fun getTimeSlotsRes(): LiveData<TimeSlotsResponse> {
        return timeSlotsList
    }

    fun getDateSlotsRes(): LiveData<DateSlotsResponse> {
        return dateSlots
    }

    fun getServiceDetailRes(): LiveData<ServicesDetailResponse> {
        return servicesDetail
    }

    fun serviceListRes(): LiveData<ServicesListResponse> {
        return servicesList
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

    fun getServices(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            servicesList = servicesRepository.getServicesList(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun addRemoveCart(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            carRes = servicesRepository.addRemoveCart(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun addRemoveFav(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            favRes = servicesRepository.addRemoveFav(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getServiceDetail(mJsonObject: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            servicesDetail = servicesRepository.getServiceDetail(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getTimeSlot(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            timeSlotsList = servicesRepository.getTimeSlots(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getDateSlots() {
        if (UtilsFunctions.isNetworkConnected()) {
            dateSlots = servicesRepository.getDateSlots()
            mIsUpdating.postValue(true)
        }
    }

}