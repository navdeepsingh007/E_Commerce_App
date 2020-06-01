package com.example.ecommerce.viewmodels.homenew

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.repositories.home.HomeJobsRepository
import com.example.ecommerce.repositories.homenew.HomeRepo
import com.example.ecommerce.viewmodels.BaseViewModel

class HomeVM : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var homeRepo = HomeRepo()
    private var homeResponse = MutableLiveData<HomeResponse>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            homeResponse()
        }
    }

    fun getHomeResponse(): LiveData<HomeResponse> {
        return homeResponse
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

    private fun homeResponse() {
        if (UtilsFunctions.isNetworkConnected()) {
            homeResponse = homeRepo.getHomeResponse()
            mIsUpdating.postValue(true)
        }
    }
}