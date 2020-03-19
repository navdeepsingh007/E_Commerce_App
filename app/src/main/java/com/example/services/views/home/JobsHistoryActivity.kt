package com.example.services.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.common.UtilsFunctions
import com.example.services.databinding.ActivityJobsHistoryBinding
import com.example.services.model.home.JobsResponse
import com.example.services.utils.BaseActivity
import com.example.services.viewmodels.home.HomeViewModel
import com.uniongoods.adapters.JobsHistoryListAdapter
import com.uniongoods.adapters.MyJobsListAdapter

class JobsHistoryActivity : BaseActivity() {
    private var jobsList = ArrayList<JobsResponse.Data>()
    lateinit var jobsHistoryBinding : ActivityJobsHistoryBinding
    lateinit var homeViewModel : HomeViewModel
    override fun getLayoutId() : Int {
        return R.layout.activity_jobs_history
    }

    override fun initViews() {
        jobsHistoryBinding = viewDataBinding as ActivityJobsHistoryBinding
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        jobsHistoryBinding.homeViewModel = homeViewModel
        jobsHistoryBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.job_history)

        startProgressDialog()
        //acceptStatus
        homeViewModel.getMyJobsHistory("1")
        homeViewModel.getJobsHistory().observe(this,
            Observer<JobsResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            jobsList.addAll(response.data!!)
                            jobsHistoryBinding.rvJobHistory.visibility = View.VISIBLE
                            jobsHistoryBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> message?.let { UtilsFunctions.showToastError(it) }
                    }

                }
            })
        // initRecyclerView()

    }

    private fun initRecyclerView() {
        var jobsHistoryListAdapter =
            JobsHistoryListAdapter(this@JobsHistoryActivity, jobsList, this@JobsHistoryActivity)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        jobsHistoryBinding.rvJobHistory.layoutManager = linearLayoutManager
        jobsHistoryBinding.rvJobHistory.adapter = jobsHistoryListAdapter
        jobsHistoryBinding.rvJobHistory.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

}