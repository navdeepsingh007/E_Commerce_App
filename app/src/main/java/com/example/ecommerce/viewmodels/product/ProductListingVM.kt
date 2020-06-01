package com.example.ecommerce.viewmodels.product

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.repositories.home.HomeJobsRepository
import com.example.ecommerce.repositories.homenew.HomeRepo
import com.example.ecommerce.repositories.product.ProductListingRepo
import com.example.ecommerce.viewmodels.BaseViewModel

class ProductListingVM : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var productListingRepo = ProductListingRepo()
    private var listingResponse = MutableLiveData<ProductListingResponse>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            listingResponse("05619abf-589d-4f4c-98c6-3d3a07d77ba4")
        }
    }

    fun getProductListing(): LiveData<ProductListingResponse> {
        return listingResponse
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

    private fun listingResponse(categoryId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            listingResponse = productListingRepo.getProductListingResponse(categoryId)
            mIsUpdating.postValue(true)
        }
    }
}