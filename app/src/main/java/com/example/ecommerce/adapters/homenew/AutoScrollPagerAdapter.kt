package com.example.ecommerce.adapters.homenew

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ecommerce.views.home.viewpager.SlideFragment

class AutoScrollPagerAdapter(fm: FragmentManager?, val bannerUrls: ArrayList<String>) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment { // Return a SlideFragment (defined as a static inner class below).
        return SlideFragment.newInstance(position + 1, bannerUrls)
    }

    override fun getCount(): Int { // Show 3 total pages.
        return bannerUrls.size
    }
}