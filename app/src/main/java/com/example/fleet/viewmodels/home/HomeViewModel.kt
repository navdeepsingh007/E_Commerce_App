package com.example.fleet.viewmodels.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.home.JobsResponse
import com.example.fleet.repositories.home.HomeJobsRepository
import com.example.fleet.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class HomeViewModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var homeRepository = HomeJobsRepository()
    private var jobsListResponse = MutableLiveData<JobsResponse>()
    private var acceptRejectJob = MutableLiveData<CommonModel>()

    init {
        /* val mJsonObject = JsonObject()
         mJsonObject.addProperty("start", 0)
         mJsonObject.addProperty("length", 10)
         if (UtilsFunctions.isNetworkConnectedReturn()) mIsUpdating.postValue(true)
 */
        jobsListResponse = homeRepository.getMyJobsList("")
        acceptRejectJob = homeRepository.acceptRejectJob(null)
    }

    fun getJobs() : LiveData<JobsResponse> {
        return jobsListResponse!!
    }

    fun acceptReject() : LiveData<CommonModel> {
        return acceptRejectJob!!
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

    fun getMyJobs(mJsonObject : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            jobsListResponse = homeRepository.getMyJobsList(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }

    fun acceptRejectJob(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            acceptRejectJob = homeRepository.acceptRejectJob(mJsonObject)
            mIsUpdating.postValue(true)
        }

    }

}