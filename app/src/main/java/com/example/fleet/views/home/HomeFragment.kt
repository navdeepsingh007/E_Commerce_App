package com.example.fleet.views.home

import androidx.lifecycle.ViewModelProviders
import com.example.fleet.R
import com.example.fleet.databinding.FragmentHomeBinding
import com.example.fleet.utils.BaseFragment
import com.example.fleet.viewmodels.HomeViewModel

class HomeFragment : BaseFragment() {
    override fun getLayoutResId() : Int {
        return R.layout.fragment_home
    }

    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel

    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentHomeBinding
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel

    }
}