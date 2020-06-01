package com.example.ecommerce.repositories.promocode

import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.promocode.ApplyPromoCodeResponse
import com.example.ecommerce.model.promocode.PromoCodeListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class PromoCodeRepository {
    private var data1: MutableLiveData<PromoCodeListResponse>? = null
    private var data2: MutableLiveData<CommonModel>? = null
    private var data3: MutableLiveData<ApplyPromoCodeResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
        data2 = MutableLiveData()
        data3 = MutableLiveData()
    }

    fun promoCodeList(/*mJsonObject : String*/): MutableLiveData<PromoCodeListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<PromoCodeListResponse>(
                                    "" + mResponse.body(),
                                    PromoCodeListResponse::class.java
                            )
                        else {
                            gson.fromJson<PromoCodeListResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                    PromoCodeListResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().getPromoList(/*mJsonObject*/)
        )

        //}
        return data1!!

    }

    fun removePromoCode(mJsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if (mJsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<CommonModel>(
                                        "" + mResponse.body(),
                                        CommonModel::class.java
                                )
                            else {
                                gson.fromJson<CommonModel>(
                                        mResponse.errorBody()!!.charStream(),
                                        CommonModel::class.java
                                )
                            }
                            data2!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data2!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().removeCoupon(mJsonObject)
            )

        }
        return data2!!

    }

    fun applyPromoCode(mJsonObject: JsonObject?): MutableLiveData<ApplyPromoCodeResponse> {
        if (mJsonObject!=null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<ApplyPromoCodeResponse>(
                                        "" + mResponse.body(),
                                        ApplyPromoCodeResponse::class.java
                                )
                            else {
                                gson.fromJson<ApplyPromoCodeResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        ApplyPromoCodeResponse::class.java
                                )
                            }
                            data3!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data3!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().applyCoupon(mJsonObject)
            )

        }
        return data3!!

    }
}