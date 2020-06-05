package com.example.ecommerce.views.category

import android.graphics.Typeface
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.productcategories.CategoriesAdapter
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.databinding.ActivityCategoriesBinding
import com.example.ecommerce.model.productcateories.CategoriesResponse
import com.example.ecommerce.model.productcateories.ParentCategoriesResponse
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.viewmodels.categories.CategoriesVM
import com.miguelcatalan.materialsearchview.MaterialSearchView

class ProductCategoriesActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var categoriesVM: CategoriesVM

    override fun getLayoutId(): Int {
        return R.layout.activity_categories
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityCategoriesBinding
        categoriesVM = ViewModelProvider(this).get(CategoriesVM::class.java)

        initToolbar()
        searchBarListener()

        startProgressDialog()
        categoriesVM.getCategoriesList()
        initProductCategories()
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

    private fun setCategoriesAdapter(categoriesList: ArrayList<ParentCategoriesResponse.Service>) {
        if (categoriesList.size > 0) {
            val notificationAdapter = CategoriesAdapter(this, categoriesList)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            binding.rvJobHistory.layoutManager = linearLayoutManager
            binding.rvJobHistory.adapter = notificationAdapter

            binding.rvJobHistory.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
        } else {
            binding.rvJobHistory.visibility = View.GONE
            binding.tvNoRecord.visibility = View.VISIBLE
        }
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

        binding.commonToolBar.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
    }

    private fun initProductCategories() {
        categoriesVM.getCatList().observe(this,
            Observer<ParentCategoriesResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (response.body != null) {
                                if (response.body.services != null) {
                                    setCategoriesAdapter(response.body.services)
                                }
                            }
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }
}