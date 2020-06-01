package com.example.ecommerce.viewmodels.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.cart.CartListResponse
import com.example.ecommerce.model.orders.CreateOrdersResponse
import com.example.ecommerce.repositories.cart.CartRepository
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class CartViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()
    private var orderPlace = MutableLiveData<CreateOrdersResponse>()
    private var updatePaymentStatus = MutableLiveData<CommonModel>()

    private var cartList = MutableLiveData<CartListResponse>()
    private var cartRepository = CartRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            cartList = cartRepository.cartList()
            orderPlace = cartRepository.orderPlace(null)
            updatePaymentStatus = cartRepository.updatePaymentStatus(null)
        }

    }

    fun getCartListRes(): LiveData<CartListResponse> {
        return cartList
    }

    fun getOrderPlaceRes(): LiveData<CreateOrdersResponse> {
        return orderPlace
    }

    fun getUpdatePayemntStatusRes(): LiveData<CommonModel> {
        return updatePaymentStatus
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

    fun orderPlace(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            orderPlace = cartRepository.orderPlace(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun updatePaymentStatus(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            updatePaymentStatus = cartRepository.updatePaymentStatus(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getCartList() {
        if (UtilsFunctions.isNetworkConnected()) {
            cartList = cartRepository.cartList()
            mIsUpdating.postValue(true)
        }

    }

}