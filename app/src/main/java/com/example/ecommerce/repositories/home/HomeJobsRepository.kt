package com.example.ecommerce.repositories.home

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.viewmodels.home.CategoriesListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class HomeJobsRepository {
    private var data: MutableLiveData<CategoriesListResponse>? = null
    private var data2: MutableLiveData<CategoriesListResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
        data2 = MutableLiveData()
    }

    fun getCategories(mJsonObject: String): MutableLiveData<CategoriesListResponse> {
        // if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
            object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null)
                        gson.fromJson<CategoriesListResponse>(
                            "" + mResponse.body(),
                            CategoriesListResponse::class.java
                        )
                    else {
                        gson.fromJson<CategoriesListResponse>(
                            mResponse.errorBody()!!.charStream(),
                            CategoriesListResponse::class.java
                        )
                    }

                    data!!.postValue(loginResponse)

                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    data!!.postValue(null)

                }

            }, ApiClient.getApiInterface().getCategories()
        )
        //}
        return data!!
    }

    fun getSubServices(mJsonObject: String): MutableLiveData<CategoriesListResponse> {
        if (!TextUtils.isEmpty(mJsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<CategoriesListResponse>(
                                "" + mResponse.body(),
                                CategoriesListResponse::class.java
                            )
                        else {
                            gson.fromJson<CategoriesListResponse>(
                                mResponse.errorBody()!!.charStream(),
                                CategoriesListResponse::class.java
                            )
                        }

                        data2!!.postValue(loginResponse)

                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data2!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().getSubServices(mJsonObject)
            )
        }
        return data2!!
    }


    fun clearCart(mJsonObject: String): MutableLiveData<CommonModel> {
        if (!TextUtils.isEmpty(mJsonObject)) {
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

                        data1!!.postValue(loginResponse)

                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().clearWholeCart()
            )
        }
        return data1!!
    }

}