package com.example.ecommerce.repositories.homenew

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.viewmodels.home.CategoriesListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class HomeRepo {
    private var data: MutableLiveData<HomeResponse>
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
    }

    fun getHomeResponse(): MutableLiveData<HomeResponse> {
            ApiService<JsonObject>().get(object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null) {
                        gson.fromJson<HomeResponse>(
                            "" + mResponse.body(),
                            HomeResponse::class.java
                        )
                    } else {
                        gson.fromJson<HomeResponse>(
                            mResponse.errorBody()!!.charStream(),
                            HomeResponse::class.java
                        )
                    }
                    data.postValue(loginResponse)
                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    data.postValue(null)
                }
            }, ApiClient.getApiInterface().getHome())
        return data
    }

/*    fun getHomeResponse(jsonObject: JsonObject?): MutableLiveData<HomeResponse> {
        if (jsonObject != null) {
            ApiService<JsonObject>().get(object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null) {
                        gson.fromJson<HomeResponse>(
                            "" + mResponse.body(),
                            HomeResponse::class.java
                        )
                    } else {
                        gson.fromJson<HomeResponse>(
                            mResponse.errorBody()!!.charStream(),
                            HomeResponse::class.java
                        )
                    }
                    data.postValue(loginResponse)
                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    data.postValue(null)
                }
            }, ApiClient.getApiInterface().getHome())
        }
        return data
    }*/
}