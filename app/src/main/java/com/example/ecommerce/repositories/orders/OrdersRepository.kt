package com.example.ecommerce.repositories.orders

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.orders.OrdersDetailNewResponse
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.model.orders.ReasonListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class OrdersRepository {

    private var data2: MutableLiveData<CommonModel>? = null
    private var data1: MutableLiveData<OrdersListResponse>? = null
    private var data3: MutableLiveData<OrdersListResponse>? = null
    private var data4: MutableLiveData<CommonModel>? = null
    private var orderDetailResponse: MutableLiveData<OrdersDetailNewResponse>? = null
    private var reasonsResponse: MutableLiveData<ReasonListResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
        data3 = MutableLiveData()
        data2 = MutableLiveData()
        data4 = MutableLiveData()
        orderDetailResponse = MutableLiveData()
        reasonsResponse = MutableLiveData()
    }

    fun orderList(/*mJsonObject : String*/): MutableLiveData<OrdersListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<OrdersListResponse>(
                                    "" + mResponse.body(),
                                    OrdersListResponse::class.java
                            )
                        else {
                            gson.fromJson<OrdersListResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                    OrdersListResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().orderList("0,1,3")
        )

        //}
        return data1!!

    }

    fun orderHistoryList(/*mJsonObject : String*/): MutableLiveData<OrdersListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<OrdersListResponse>(
                                    "" + mResponse.body(),
                                    OrdersListResponse::class.java
                            )
                        else {
                            gson.fromJson<OrdersListResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                    OrdersListResponse::class.java
                            )
                        }
                        data3!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data3!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().orderHistroyList("2,4,5")
        )

        //}
        return data3!!

    }

    fun cancelOrder(mJsonObject: JsonObject?): MutableLiveData<CommonModel> {
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

                    }, ApiClient.getApiInterface().cancelOrder(mJsonObject)
            )

        }
        return data2!!

    }

    fun completeOrder(mJsonObject: JsonObject?): MutableLiveData<CommonModel> {
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
                            data4!!.postValue(loginResponse)
                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data4!!.postValue(null)
                        }

                    }, ApiClient.getApiInterface().completeOrder(mJsonObject)
            )

        }
        return data4!!

    }

    fun orderDetail(id : String): MutableLiveData<OrdersDetailNewResponse> {
        if (!TextUtils.isEmpty(id)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
            object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null)
                        gson.fromJson<OrdersDetailNewResponse>(
                            "" + mResponse.body(),
                            OrdersDetailNewResponse::class.java
                        )
                    else {
                        gson.fromJson<OrdersDetailNewResponse>(
                            mResponse.errorBody()!!.charStream(),
                            OrdersDetailNewResponse::class.java
                        )
                    }
                    orderDetailResponse!!.postValue(loginResponse)
                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    orderDetailResponse!!.postValue(null)
                }

            }, ApiClient.getApiInterface().orderDetail(id)
        )

        }
        return orderDetailResponse!!

    }

    fun getReason(): MutableLiveData<ReasonListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
            object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null)
                        gson.fromJson<ReasonListResponse>(
                            "" + mResponse.body(),
                            ReasonListResponse::class.java
                        )
                    else {
                        gson.fromJson<ReasonListResponse>(
                            mResponse.errorBody()!!.charStream(),
                            ReasonListResponse::class.java
                        )
                    }
                    reasonsResponse!!.postValue(loginResponse)
                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    reasonsResponse!!.postValue(null)
                }

            }, ApiClient.getApiInterface().getReason()
        )

        //}
        return reasonsResponse!!

    }

}