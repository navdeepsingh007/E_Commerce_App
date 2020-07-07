package com.example.ecommerce.viewmodels.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.orders.OrdersDetailNewResponse
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.model.orders.ReasonListResponse
import com.example.ecommerce.repositories.orders.OrdersRepository
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class OrdersViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()
    private var cancelOrder = MutableLiveData<CommonModel>()
    private var completeOrder = MutableLiveData<CommonModel>()
    private var ordersList = MutableLiveData<OrdersListResponse>()
    private var orderDetail = MutableLiveData<OrdersDetailNewResponse>()
    private var reasonListResponse = MutableLiveData<ReasonListResponse>()
    private var ordersHistoryList = MutableLiveData<OrdersListResponse>()
    private var ordersRepository = OrdersRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            ordersList = ordersRepository.orderList()
            ordersHistoryList = ordersRepository.orderHistoryList()
            orderDetail = ordersRepository.orderDetail("")
            reasonListResponse = ordersRepository.getReason()
            cancelOrder = ordersRepository.cancelOrder(null)
            completeOrder = ordersRepository.completeOrder(null)
        }

    }

    fun getOrdersListRes(): LiveData<OrdersListResponse> {
        return ordersList
    }

    fun getOrdersDetailRes(): LiveData<OrdersDetailNewResponse> {
        return orderDetail
    }

    fun getReasonResponse(): LiveData<ReasonListResponse> {
        return reasonListResponse
    }

    fun getOrdersHistoryListRes(): LiveData<OrdersListResponse> {
        return ordersHistoryList
    }

    fun getCancelOrderRes(): LiveData<CommonModel> {
        return cancelOrder
    }

    fun getCompleteOrderRes(): LiveData<CommonModel> {
        return completeOrder
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

    fun cancelOrder(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            cancelOrder = ordersRepository.cancelOrder(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun completeOrder(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            cancelOrder = ordersRepository.completeOrder(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getOrderList() {
        if (UtilsFunctions.isNetworkConnected()) {
            ordersList = ordersRepository.orderList()
            mIsUpdating.postValue(true)
        }

    }

    fun getOrderDetail(id : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            orderDetail = ordersRepository.orderDetail(id)
            mIsUpdating.postValue(true)
        }

    }

    fun getReason() {
        if (UtilsFunctions.isNetworkConnected()) {
            reasonListResponse = ordersRepository.getReason()
            mIsUpdating.postValue(true)
        }

    }
}