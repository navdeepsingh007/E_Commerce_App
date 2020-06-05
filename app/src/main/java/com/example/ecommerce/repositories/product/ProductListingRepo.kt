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
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.model.sale.SalesListInput
import com.example.ecommerce.model.sales.SalesListResponse
import com.example.ecommerce.viewmodels.home.CategoriesListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class ProductListingRepo {
    private var data: MutableLiveData<ProductListingResponse>
    private var data1: MutableLiveData<SalesListResponse>
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
    }

    fun getProductListingResponse(categoryId: String): MutableLiveData<ProductListingResponse> {
        ApiService<JsonObject>().get(object : ApiResponse<JsonObject> {
            override fun onResponse(mResponse: Response<JsonObject>) {
                val loginResponse = if (mResponse.body() != null) {
                    gson.fromJson<ProductListingResponse>(
                        "" + mResponse.body(),
                        ProductListingResponse::class.java
                    )
                } else {
                    gson.fromJson<ProductListingResponse>(
                        mResponse.errorBody()!!.charStream(),
                        ProductListingResponse::class.java
                    )
                }
                data.postValue(loginResponse)
            }

            override fun onError(mKey: String) {
                UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                data.postValue(null)
            }
        }, ApiClient.getApiInterface().getProductsListing(categoryId))
        return data
    }

    fun getSalesListingResponse(
        salesListInput: SalesListInput?,
        page: String,
        limit: String
    ): MutableLiveData<SalesListResponse> {
        if (salesListInput != null) {
            ApiService<JsonObject>().get(object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val loginResponse = if (mResponse.body() != null) {
                        gson.fromJson<SalesListResponse>(
                            "" + mResponse.body(),
                            SalesListResponse::class.java
                        )
                    } else {
                        gson.fromJson<SalesListResponse>(
                            mResponse.errorBody()!!.charStream(),
                            SalesListResponse::class.java
                        )
                    }
                    data1.postValue(loginResponse)
                }

                override fun onError(mKey: String) {
                    UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                    data1.postValue(null)
                }
            }, ApiClient.getApiInterface().getSalesListing(salesListInput, page, limit))
        }
        return data1
    }
}