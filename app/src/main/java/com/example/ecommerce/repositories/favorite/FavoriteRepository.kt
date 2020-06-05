package com.example.ecommerce.repositories.favorite

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class FavoriteRepository {
    private var data1: MutableLiveData<FavouriteListResponse>? = null
    private var data3: MutableLiveData<AddRemoveFavResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
        data3 = MutableLiveData()
    }

    fun favoriteList(/*mJsonObject : String*/): MutableLiveData<FavouriteListResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<FavouriteListResponse>(
                                    "" + mResponse.body(),
                                FavouriteListResponse::class.java
                            )
                        else {
                            gson.fromJson<FavouriteListResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                FavouriteListResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().favList(/*mJsonObject*/)
        )

        //}
        return data1!!

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

}