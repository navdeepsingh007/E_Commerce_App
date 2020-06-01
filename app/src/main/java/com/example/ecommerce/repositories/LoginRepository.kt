package com.example.ecommerce.repositories

import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class LoginRepository {
    private var data : MutableLiveData<LoginResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()

    }

    fun getLoginData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                 mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogin(jsonObject)

            )

        }
        return data!!

    }

    fun checkPhoneExistence(jsonObject : JsonObject?) : MutableLiveData<CommonModel> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<CommonModel>("" + mResponse.body(), CommonModel::class.java)
                        else {
                            gson.fromJson<CommonModel>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data1!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().checkPhoneExistence(jsonObject)

            )

        }
        return data1!!

    }


    fun getLogoutResonse(jsonObject : JsonObject?) : MutableLiveData<CommonModel> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val logoutResponse = if (mResponse.body() != null)
                            gson.fromJson<CommonModel>("" + mResponse.body(), CommonModel::class.java)
                        else {
                            gson.fromJson<CommonModel>(
                                mResponse.errorBody()!!.charStream(),
                                CommonModel::class.java
                            )
                        }

                        data1!!.postValue(logoutResponse)

                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogout(jsonObject)

            )

        }
        return data1!!

    }

}