package com.example.ecommerce.viewmodels.product

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.model.sale.SalesListInput
import com.example.ecommerce.model.sales.SalesListResponse
import com.example.ecommerce.repositories.home.HomeJobsRepository
import com.example.ecommerce.repositories.homenew.HomeRepo
import com.example.ecommerce.repositories.product.ProductListingRepo
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class ProductListingVM : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var productListingRepo = ProductListingRepo()
    private var listingResponse = MutableLiveData<ProductListingResponse>()
    private var salesListResponse = MutableLiveData<SalesListResponse>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
//            listingResponse("05619abf-589d-4f4c-98c6-3d3a07d77ba4")
            salesListResponse = productListingRepo.getSalesListingResponse(null, "", "")
        }
    }

    fun getProductListing(): LiveData<ProductListingResponse> {
        return listingResponse
    }

    fun getSalesListing(): LiveData<SalesListResponse> {
        return salesListResponse
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

    fun listingResponse(categoryId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            listingResponse = productListingRepo.getProductListingResponse(categoryId)
            mIsUpdating.postValue(true)
        }
    }

    fun salesListResponse(salesListInput: SalesListInput) {
        if (UtilsFunctions.isNetworkConnected()) {
//            val salesListInput = SalesListInput()
//            jObj.addProperty("qkeufhkwd", "")
            salesListResponse = productListingRepo.getSalesListingResponse(salesListInput, "", "")
            mIsUpdating.postValue(true)
        }
    }
}