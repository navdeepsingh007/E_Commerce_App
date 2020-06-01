package com.example.ecommerce.viewmodels.ratingreviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.orders.OrdersDetailResponse
import com.example.ecommerce.model.ratnigreviews.RatingReviewListInput
import com.example.ecommerce.model.ratnigreviews.ReviewsListResponse
import com.example.ecommerce.repositories.ratingreviews.RatingReviewsRepository
import com.example.ecommerce.viewmodels.BaseViewModel

class RatingReviewsViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()

    private var reviewsList = MutableLiveData<ReviewsListResponse>()
    private var orderDetail = MutableLiveData<OrdersDetailResponse>()
    private var ratingReview = MutableLiveData<CommonModel>()
    private var ratingReviewsRepository = RatingReviewsRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            reviewsList = ratingReviewsRepository.reviewsListList("", "")
            orderDetail = ratingReviewsRepository.getOrderDetail("")
            ratingReview= ratingReviewsRepository.addRatings(null)

        }

    }

    fun getReviewsRes(): LiveData<ReviewsListResponse> {
        return reviewsList
    }

    fun getOrderDetail(): LiveData<OrdersDetailResponse> {
        return orderDetail
    }
    fun getRatingRes(): LiveData<CommonModel> {
        return ratingReview
    }

    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun getReviewsList(id: String, page: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            reviewsList = ratingReviewsRepository.reviewsListList(id, page)
            mIsUpdating.postValue(true)
        }

    }

    fun orderDetail(id: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            orderDetail = ratingReviewsRepository.getOrderDetail(id)
            mIsUpdating.postValue(true)
        }

    }

    fun addRatings(params: RatingReviewListInput) {
        if (UtilsFunctions.isNetworkConnected()) {
            ratingReview = ratingReviewsRepository.addRatings(params)
            mIsUpdating.postValue(true)
        }

    }


}