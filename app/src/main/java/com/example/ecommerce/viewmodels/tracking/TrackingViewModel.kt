package com.example.ecommerce.viewmodels.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.repositories.home.HomeJobsRepository
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class TrackingViewModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var completejob = MutableLiveData<CommonModel>()
    private var homeJobsRepository = HomeJobsRepository()

    init {
        //completejob = homeJobsRepository.startCompleteJob(null)
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return isClick
    }

    override fun clickListener(v : View) {
        isClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun startCompleteJob() : LiveData<CommonModel> {
        return completejob
    }

    fun startJob(status : String, jobId : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            var jsonObject = JsonObject()
            jsonObject.addProperty("progressStatus", status)
            jsonObject.addProperty("jobId", jobId)
            //completejob = homeJobsRepository.startCompleteJob(jsonObject)
            mIsUpdating.postValue(true)
        }

    }

}