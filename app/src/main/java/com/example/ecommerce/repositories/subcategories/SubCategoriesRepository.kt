package com.example.ecommerce.repositories.subcategories

import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.subcategories.SubCategoriesRespnse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class SubCategoriesRepository {
    private var data : MutableLiveData<SubCategoriesRespnse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
    }

    fun getSubCategoriesList(jsonObject : JsonObject?) : MutableLiveData<SubCategoriesRespnse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<SubCategoriesRespnse>(
                                "" + mResponse.body(),
                                SubCategoriesRespnse::class.java
                            )
                        else {
                            gson.fromJson<SubCategoriesRespnse>(
                                mResponse.errorBody()!!.charStream(),
                                SubCategoriesRespnse::class.java
                            )
                        }
                        data!!.postValue(loginResponse)
                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().getSubCatList(jsonObject)
            )

        }
        return data!!

    }

}