package com.example.ecommerce.viewmodels.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.model.productcateories.ParentCategoriesResponse
import com.example.ecommerce.repositories.categories.CategoriesRepo
import com.example.ecommerce.viewmodels.BaseViewModel

class CategoriesVM : BaseViewModel() {
    private var catList = MutableLiveData<ParentCategoriesResponse>()
    private var categoriesRepo = CategoriesRepo()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
//            catList = categoriesRepo.categoriesList()
        }
    }

    fun getCatList(): LiveData<ParentCategoriesResponse> {
        return catList
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

    fun getCategoriesList() {
        if (UtilsFunctions.isNetworkConnected()) {
            catList = categoriesRepo.categoriesList()
            mIsUpdating.postValue(true)
        }
    }
}