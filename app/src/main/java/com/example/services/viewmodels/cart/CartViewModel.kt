package com.example.services.viewmodels.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.services.common.UtilsFunctions
import com.example.services.model.CommonModel
import com.example.services.model.LoginResponse
import com.example.services.model.address.AddressListResponse
import com.example.services.model.address.AddressResponse
import com.example.services.model.cart.CartListResponse
import com.example.services.repositories.cart.CartRepository
import com.example.services.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class CartViewModel : BaseViewModel() {
    private var data : MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()
    private var orderPlace = MutableLiveData<CommonModel>()

    private var cartList = MutableLiveData<CartListResponse>()
    private var cartRepository = CartRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            cartList = cartRepository.cartList()
            orderPlace = cartRepository.orderPlace(null)
        }

    }

    fun getCartListRes() : LiveData<CartListResponse> {
        return cartList
    }

    fun getOrderPlaceRes() : LiveData<CommonModel> {
        return orderPlace
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

    fun orderPlace(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            orderPlace = cartRepository.orderPlace(mJsonObject)
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