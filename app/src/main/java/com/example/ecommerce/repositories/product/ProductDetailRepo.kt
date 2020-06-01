package com.example.ecommerce.repositories.product

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
import com.example.ecommerce.model.productdetail.ProductDetailResponse
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.viewmodels.home.CategoriesListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class ProductDetailRepo {
    private var data2: MutableLiveData<ProductDetailResponse>
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data2 = MutableLiveData()
    }


    fun getProductDetailsResponse(categoryId: String): MutableLiveData<ProductDetailResponse> {
        ApiService<JsonObject>().get(object : ApiResponse<JsonObject> {
            override fun onResponse(mResponse: Response<JsonObject>) {
                val loginResponse = if (mResponse.body() != null) {
                    gson.fromJson<ProductDetailResponse>(
                        "" + mResponse.body(),
                        ProductDetailResponse::class.java
                    )
                } else {
                    gson.fromJson<ProductDetailResponse>(
                        mResponse.errorBody()!!.charStream(),
                        ProductDetailResponse::class.java
                    )
                }
                data2.postValue(loginResponse)
            }

            override fun onError(mKey: String) {
                UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                data2.postValue(null)
            }
        }, ApiClient.getApiInterface().getProductDetail(categoryId))
        return data2
    }
}