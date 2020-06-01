package com.example.ecommerce.repositories


import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class ResetPasswordRepository {
    private var data: MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()


    init{
        data = MutableLiveData()

    }

    fun getResetPasswordResponse(jsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if(jsonObject!=null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val commonResponse = if (mResponse.body() != null)
                            gson.fromJson<CommonModel>("" + mResponse.body(), CommonModel::class.java)
                        else {
                            gson.fromJson<CommonModel>(
                                 mResponse.errorBody()!!.charStream(),
                                CommonModel::class.java
                            )
                        }

                        data!!.postValue(commonResponse)




                    }

                    override fun onError(mKey : String) {
                        data!!.postValue(null)
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))

                    }

                }, ApiClient.getApiInterface().resetPassword(jsonObject))

        }
        return data!!


    }


    fun getChangePasswordResponse(jsonObject : JsonObject) : MutableLiveData<CommonModel> {
        if (jsonObject.toString() != "{}") {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val commonResponse = gson.fromJson<CommonModel>(
                            "" + mResponse.body(),
                            CommonModel::class.java
                        )

                        data!!.postValue(commonResponse)

                    }

                    override fun onError(mKey : String) {
                        data!!.postValue(null)
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))

                    }

                }, ApiClient.getApiInterface().chnagePassword(jsonObject)
            )

        }
        return data!!

    }




}