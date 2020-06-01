package com.example.ecommerce.repositories.ratingreviews

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.ApiResponse
import com.example.ecommerce.api.ApiService
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.orders.OrdersDetailResponse
import com.example.ecommerce.model.ratnigreviews.RatingReviewListInput
import com.example.ecommerce.model.ratnigreviews.ReviewsListResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class RatingReviewsRepository {
    private var data1: MutableLiveData<ReviewsListResponse>? = null
    private var data2: MutableLiveData<OrdersDetailResponse>? = null
    private var data3: MutableLiveData<CommonModel>? = null

    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data1 = MutableLiveData()
        data2 = MutableLiveData()
        data3 = MutableLiveData()
    }

    fun reviewsListList(id: String, page: String): MutableLiveData<ReviewsListResponse> {
        if (!TextUtils.isEmpty(id)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<ReviewsListResponse>(
                                "" + mResponse.body(),
                                ReviewsListResponse::class.java
                            )
                        else {
                            gson.fromJson<ReviewsListResponse>(
                                mResponse.errorBody()!!.charStream(),
                                ReviewsListResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().ratingRaviewsList(id, "1", "100")
            )

        }
        return data1!!

    }

    fun getOrderDetail(id: String): MutableLiveData<OrdersDetailResponse> {
        if (!TextUtils.isEmpty(id)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<OrdersDetailResponse>(
                                "" + mResponse.body(),
                                OrdersDetailResponse::class.java
                            )
                        else {
                            gson.fromJson<OrdersDetailResponse>(
                                mResponse.errorBody()!!.charStream(),
                                OrdersDetailResponse::class.java
                            )
                        }
                        data2!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data2!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().orderDetail(id)
            )

        }
        return data2!!

    }

    fun addRatings(id: RatingReviewListInput?): MutableLiveData<CommonModel> {
        if (id != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
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
                        data3!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data3!!.postValue(null)
                    }

                }, ApiClient.getApiInterface().addRatings(id)
            )

        }
        return data3!!

    }


}