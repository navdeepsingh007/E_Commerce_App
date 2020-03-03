package com.example.fleet.views.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.common.UtilsFunctions.showToastError
import com.example.fleet.databinding.FragmentHomeBinding
import com.example.fleet.model.home.JobsResponse
import com.example.fleet.utils.BaseFragment
import com.example.fleet.viewmodels.home.HomeViewModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.MyJobsListAdapter

class HomeFragment : BaseFragment() {
    private var jobsList = ArrayList<JobsResponse.Data>()
    private var myJobsListAdapter : MyJobsListAdapter? = null
    override fun getLayoutResId() : Int {
        return R.layout.fragment_home
    }

    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private val mJsonObject = JsonObject()
    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentHomeBinding
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel
        baseActivity.startProgressDialog()
        //acceptStatus
        mJsonObject.addProperty(
            "acceptStatus", "1"/* sharedPrefClass!!.getPrefValue(
                MyApplication.instance,
                GlobalConstants.USERID
            ).toString()*/
        )
        homeViewModel.getMyJobs("1")
        homeViewModel.getJobs().observe(this,
            Observer<JobsResponse> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            jobsList.addAll(response.data!!)
                            fragmentHomeBinding.rvJobs.visibility = View.VISIBLE
                            initRecyclerView()
                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> message?.let { showToastError(it) }
                    }

                }
            })
        // initRecyclerView()

    }

    private fun initRecyclerView() {
        myJobsListAdapter = MyJobsListAdapter(this@HomeFragment, jobsList, activity!!)
        val linearLayoutManager = LinearLayoutManager(this.baseActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentHomeBinding.rvJobs.layoutManager = linearLayoutManager
        fragmentHomeBinding.rvJobs.adapter = myJobsListAdapter
        fragmentHomeBinding.rvJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

}