package com.example.fleet.repositories

import androidx.lifecycle.MutableLiveData
import com.example.fleet.R
import com.example.fleet.api.ApiClient
import com.example.fleet.api.ApiResponse
import com.example.fleet.api.ApiService
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class ForgotPasswordRepository {
    private var data : MutableLiveData<CommonModel>? = null
    private var forgotData : MutableLiveData<CommonModel>? = null
    private var resendtData : MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        forgotData = MutableLiveData()
        resendtData = MutableLiveData()
    }

    fun getForgotPasswordResponse(mobileNumber : String, countryCode : String) : MutableLiveData<CommonModel> {
//        if (mobileNumber != "") {
//            val mApiService = ApiService<JsonObject>()
//            mApiService.get(
//                object : ApiResponse<JsonObject> {
//                    override fun onResponse(mResponse : Response<JsonObject>) {
//                        val loginResponse = if (mResponse.body() != null)
//                            gson.fromJson<CommonModel>("" + mResponse.body(), CommonModel::class.java)
//                        else {
//                            gson.fromJson<CommonModel>(
//                                mResponse.errorBody()!!.charStream(),
//                                CommonModel::class.java
//                            )
//                        }
//
//                        forgotData!!.postValue(loginResponse)
//
//                    }
//
//                    override fun onError(mKey : String) {
//                        forgotData!!.postValue(null)
//                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
//
//                    }
//
//               },
//                ApiClient.getApiInterface().callForgotPassword(mobileNumber, countryCode)
//            )
//
//        }
        return forgotData!!

    }

}