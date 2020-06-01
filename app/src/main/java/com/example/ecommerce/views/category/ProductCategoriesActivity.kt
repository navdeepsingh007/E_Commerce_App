package com.example.ecommerce.views.category

import android.graphics.Typeface
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.productcategories.CategoriesAdapter
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.databinding.ActivityCategoriesBinding
import com.example.ecommerce.model.productcateories.CategoriesResponse
import com.example.ecommerce.utils.BaseActivity
import com.miguelcatalan.materialsearchview.MaterialSearchView

class ProductCategoriesActivity: BaseActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    private var categoriesList = ArrayList<CategoriesResponse.Body>()

    override fun getLayoutId(): Int {
        return R.layout.activity_categories
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityCategoriesBinding

        initToolbar()
        setCategoriesAdapter()
        searchBarListener()
    }

    private fun initToolbar() {
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.categories)
        val headingView = binding.commonToolBar.imgToolbarText

        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun setCategoriesAdapter() {
//        if (categoriesList.size > 0) {
            val notificationAdapter = CategoriesAdapter(this, categoriesList)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            binding.rvJobHistory.layoutManager = linearLayoutManager
            binding.rvJobHistory.adapter = notificationAdapter

            binding.rvJobHistory.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
//        } else {
//            binding.rvJobHistory.visibility = View.GONE
//            binding.tvNoRecord.visibility = View.VISIBLE
//        }
    }

    private fun searchBarListener() {
        binding.commonToolBar.searchView.visibility = View.VISIBLE

        binding.commonToolBar.searchView.setOnCloseListener {
            // On CLOSE
            binding.commonToolBar.imgToolbarText.visibility = View.VISIBLE
            false
        }

        binding.commonToolBar.searchView.setOnSearchClickListener {
            // On Search click
            binding.commonToolBar.imgToolbarText.visibility = View.GONE
        }

        binding.commonToolBar.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // true if handled request, otherwise false
                UtilsFunctions.showToastSuccess("text - $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // true if handled request, otherwise false
                return true
            }
        })

//        binding.commonToolBar.searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
//            override fun onSearchViewClosed() {
//                binding.commonToolBar.imgToolbarText.visibility = View.VISIBLE
//            }
//
//            override fun onSearchViewShown() {
//                binding.commonToolBar.imgToolbarText.visibility = View.GONE
//            }
//        })
//
//        binding.commonToolBar.searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // true if handled request, otherwise false
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // true if handled request, otherwise false
//                return true
//            }
//        })
    }
}