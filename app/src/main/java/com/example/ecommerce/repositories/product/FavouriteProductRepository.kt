package com.example.ecommerce.repositories.product

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.services.DateSlotsResponse
import com.example.ecommerce.model.services.ServicesDetailResponse
import com.example.ecommerce.model.services.ServicesListResponse
import com.example.ecommerce.model.services.TimeSlotsResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class FavouriteProductRepository {
    private var data: MutableLiveData<ServicesListResponse>? = null
    private var data2: MutableLiveData<ServicesDetailResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private var data3: MutableLiveData<AddRemoveFavResponse>? = null
    private var data4: MutableLiveData<TimeSlotsResponse>? = null
    private var data5: MutableLiveData<DateSlotsResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data2 = MutableLiveData()
        data1 = MutableLiveData()
        data3 = MutableLiveData()
        data4 = MutableLiveData()
        data5 = MutableLiveData()
    }

    fun getServicesList(jsonObject: String): MutableLiveData<ServicesListResponse> {
        if (!TextUtils.isEmpty(jsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<ServicesListResponse>(
                                        "" + mResponse.body(),
                                        ServicesListResponse::class.java
                                )
                            else {
                                gson.fromJson<ServicesListResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        ServicesListResponse::class.java
                                )
                            }
                            data!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().getServices(jsonObject)
            )

        }
        return data!!

    }

    fun getServiceDetail(jsonObject: String): MutableLiveData<ServicesDetailResponse> {
        if (!TextUtils.isEmpty(jsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<ServicesDetailResponse>(
                                        "" + mResponse.body(),
                                        ServicesDetailResponse::class.java
                                )
                            else {
                                gson.fromJson<ServicesDetailResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        ServicesDetailResponse::class.java
                                )
                            }
                            data2!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data2!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().getServiceDetail(jsonObject)
            )

        }
        return data2!!

    }

    fun addCart(jsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if (jsonObject != null) {
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

                    }, ApiClient.getApiInterface().addCart(jsonObject)
            )

        }
        return data1!!

    }

    fun removeCart(jsonObject: String): MutableLiveData<CommonModel> {
        if (!TextUtils.isEmpty(jsonObject)) {
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

                    }, ApiClient.getApiInterface().removeCart(jsonObject)
            )

        }
        return data1!!

    }

    fun addFav(jsonObject: JsonObject?): MutableLiveData<AddRemoveFavResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null) {
                                gson.fromJson<AddRemoveFavResponse>(
                                    "" + mResponse.body(),
                                    AddRemoveFavResponse::class.java
                                )
                            } else {
                                gson.fromJson<AddRemoveFavResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                    AddRemoveFavResponse::class.java
                                )
                            }
                            data3!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data3!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().addFav(jsonObject))

        }
        return data3!!

    }

    fun removeFav(jsonObject: String): MutableLiveData<AddRemoveFavResponse> {
        if (!TextUtils.isEmpty(jsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<AddRemoveFavResponse>(
                                        "" + mResponse.body(),
                                    AddRemoveFavResponse::class.java
                                )
                            else {
                                gson.fromJson<AddRemoveFavResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                    AddRemoveFavResponse::class.java
                                )
                            }
                            data3!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data3!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().removeFav(jsonObject)
            )

        }
        return data3!!

    }

    fun getTimeSlots(jsonObject: String): MutableLiveData<TimeSlotsResponse> {
        if (!TextUtils.isEmpty(jsonObject)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<TimeSlotsResponse>(
                                        "" + mResponse.body(),
                                        TimeSlotsResponse::class.java
                                )
                            else {
                                gson.fromJson<TimeSlotsResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        TimeSlotsResponse::class.java
                                )
                            }
                            data4!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data4!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().getTimeSlots(jsonObject)
            )

        }
        return data4!!

    }

    fun getDateSlots(): MutableLiveData<DateSlotsResponse> {
        //if (jsonObject!=null) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<DateSlotsResponse>(
                                    "" + mResponse.body(),
                                    DateSlotsResponse::class.java
                            )
                        else {
                            gson.fromJson<DateSlotsResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                    DateSlotsResponse::class.java
                            )
                        }
                        data5!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data5!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().getDateSlots()
        )

        // }
        return data5!!

    }


}