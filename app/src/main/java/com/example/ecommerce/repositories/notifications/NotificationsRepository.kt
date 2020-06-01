package com.example.ecommerce.repositories.notifications

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.notificaitons.NotificationsListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class NotificationsRepository {
    private var data : MutableLiveData<NotificationsListResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
    }

    fun getNotificationsList(userId : String) : MutableLiveData<NotificationsListResponse> {
        if (!TextUtils.isEmpty(userId)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<NotificationsListResponse>(
                                "" + mResponse.body(),
                                NotificationsListResponse::class.java
                            )
                        else {
                            gson.fromJson<NotificationsListResponse>(
                                mResponse.errorBody()!!.charStream(),
                                NotificationsListResponse::class.java
                            )
                        }
                        data!!.postValue(loginResponse)
                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().getNotificationList(userId)
            )
        }
        return data!!
    }

    fun clearAllNotifications(id : String) : MutableLiveData<CommonModel> {
        if (!TextUtils.isEmpty(id)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
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

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().clearAllNotification(id)
            )
        }
        return data1!!
    }

}