package com.example.ecommerce.viewmodels.product

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.model.productdetail.ProductDetailResponse
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.repositories.home.HomeJobsRepository
import com.example.ecommerce.repositories.homenew.HomeRepo
import com.example.ecommerce.repositories.product.ProductDetailRepo
import com.example.ecommerce.repositories.product.ProductListingRepo
import com.example.ecommerce.viewmodels.BaseViewModel

class ProductDetailsVM : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var productDetailRepo = ProductDetailRepo()
    private var productDetails = MutableLiveData<ProductDetailResponse>()

    init {
//        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
//            productDetails("05619abf-589d-4f4c-98c6-3d3a07d77ba4")
//        }
    }

    fun initProductDetails(productId: String, addressId: String) {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            productDetails(productId, addressId)
        }
    }

    fun getProductDetails(): LiveData<ProductDetailResponse> {
        return productDetails
    }

    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return isClick
    }

    override fun clickListener(v: View) {
        isClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    private fun productDetails(categoryId: String, addressId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            productDetails = productDetailRepo.getProductDetailsResponse(categoryId, addressId)
            mIsUpdating.postValue(true)
        }
    }
}