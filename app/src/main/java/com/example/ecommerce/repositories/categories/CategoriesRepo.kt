package com.example.ecommerce.repositories.categories

import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.model.productcateories.ParentCategoriesResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class CategoriesRepo {
    private var data1: MutableLiveData<ParentCategoriesResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
    }

    fun categoriesList(/*mJsonObject : String*/): MutableLiveData<ParentCategoriesResponse> {
        //if (!TextUtils.isEmpty(mJsonObject)) {
        val mApiService = ApiService<JsonObject>()
        mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<ParentCategoriesResponse>(
                                    "" + mResponse.body(),
                                ParentCategoriesResponse::class.java
                            )
                        else {
                            gson.fromJson<ParentCategoriesResponse>(
                                    mResponse.errorBody()!!.charStream(),
                                ParentCategoriesResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().getCategoriesList(/*mJsonObject*/)
        )

        //}
        return data1!!
    }
}