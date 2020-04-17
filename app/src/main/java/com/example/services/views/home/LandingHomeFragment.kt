package com.example.services.views.home

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.services.R
import com.example.services.common.UtilsFunctions
import com.example.services.common.UtilsFunctions.showToastError
import com.example.services.constants.GlobalConstants
import com.example.services.databinding.FragmentHomeLandingBinding
import com.example.services.utils.BaseFragment
import com.example.services.viewmodels.home.CategoriesListResponse
import com.example.services.viewmodels.home.HomeViewModel
import com.example.services.viewmodels.home.Services
import com.google.gson.JsonObject
import com.uniongoods.adapters.*

class
LandingHomeFragment : BaseFragment() {
    private var categoriesList = ArrayList<Services>()
    private lateinit var fragmentHomeBinding: FragmentHomeLandingBinding
    private var offersList =
            ArrayList<com.example.services.viewmodels.home.Banners>()

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home_landing
    }

    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentHomeLandingBinding
        val homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel
        categoriesList?.clear()
        val mJsonObject = JsonObject()
        mJsonObject.addProperty(
                "acceptStatus", "1"
        )
        if (UtilsFunctions.isNetworkConnected()) {
            baseActivity.startProgressDialog()
        }
        homeViewModel.getJobs().observe(this,
                Observer<CategoriesListResponse> { response ->
                    baseActivity.stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                categoriesList?.addAll(response.body.services)
                                fragmentHomeBinding.gvServices.visibility = View.VISIBLE
                                initRecyclerView()

                                offersList.addAll(response.body.banners)
                                if (offersList.size > 0) {
                                    offerListViewPager()
                                    fragmentHomeBinding.offersViewpager.visibility = View.VISIBLE
                                } else {
                                    fragmentHomeBinding.offersViewpager.visibility = View.GONE
                                }
                            }
                            else -> message?.let {
                                showToastError(it)
                                fragmentHomeBinding.gvServices.visibility = View.GONE
                            }
                        }
                    }
                })

        fragmentHomeBinding.gvServices.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    // if (categoriesList[position].isService.equals("false")) {
                    val intent = Intent(activity!!, DashboardActivity::class.java)
                    intent.putExtra("catId", categoriesList[position].id)
                    intent.putExtra("name", categoriesList[position].name)
                    GlobalConstants.CATEGORY_SELECTED = categoriesList[position].id
                    startActivity(intent)
                    // }
                }
    }


    private fun initRecyclerView() {
        val adapter = LandingHomeCategoriesGridListAdapter(this@LandingHomeFragment, categoriesList, activity!!)
        fragmentHomeBinding.gvServices.adapter = adapter
    }

    private fun offerListViewPager() {
        val adapter = LandingHomeOffersListAdapter(this@LandingHomeFragment, offersList, activity!!)
        fragmentHomeBinding.offersViewpager.adapter = adapter

    }

}