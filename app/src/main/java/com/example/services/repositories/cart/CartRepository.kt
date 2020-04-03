package com.example.services.repositories.cart

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.services.R
import com.example.services.api.ApiClient
import com.example.services.api.ApiResponse
import com.example.services.api.ApiService
import com.example.services.application.MyApplication
import com.example.services.common.UtilsFunctions
import com.example.services.model.CommonModel
import com.example.services.model.address.AddressListResponse
import com.example.services.model.address.AddressResponse
import com.example.services.model.cart.CartListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class CartRepository {
    private var data1 : MutableLiveData<CartListResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
    }

    fun cartList(/*mJsonObject : String*/) : MutableLiveData<CartListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<CartListResponse>(
                                "" + mResponse.body(),
                                CartListResponse::class.java
                            )
                        else {
                            gson.fromJson<CartListResponse>(
                                mResponse.errorBody()!!.charStream(),
                                CartListResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().cartList(/*mJsonObject*/)
            )

        //}
        return data1!!

    }

}