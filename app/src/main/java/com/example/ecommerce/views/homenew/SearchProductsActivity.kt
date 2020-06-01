package com.example.ecommerce.views.homenew

import android.graphics.Typeface
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.homenew.SearchAdapter
import com.example.ecommerce.databinding.ActivitySearchBinding
import com.example.ecommerce.model.homenew.SearchResponse
import com.example.ecommerce.utils.BaseActivity

class SearchProductsActivity: BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var searchList = ArrayList<SearchResponse.Body>()

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initViews() {
        binding = viewDataBinding as ActivitySearchBinding

        initToolbar()
        setSearchAdapter()
    }

    private fun initToolbar() {
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.search_products)
        val headingView = binding.commonToolBar.imgToolbarText

        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun setSearchAdapter() {
//        if (searchList.size > 0) {
        val notificationAdapter = SearchAdapter(this, searchList)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.rvSearchedProducts.layoutManager = linearLayoutManager
        binding.rvSearchedProducts.adapter = notificationAdapter

        binding.rvSearchedProducts.visibility = View.VISIBLE
        binding.tvNoRecord.visibility = View.GONE
//        } else {
//            binding.rvJobHistory.visibility = View.GONE
//            binding.tvNoRecord.visibility = View.VISIBLE
//        }
    }
}