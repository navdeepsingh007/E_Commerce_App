package com.example.services.views.subcategories

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.common.UtilsFunctions
import com.example.services.constants.GlobalConstants
import com.example.services.utils.BaseActivity
import com.example.services.databinding.ActivitySubCategoriesBinding
import com.example.services.model.CommonModel
import com.example.services.model.address.AddressListResponse
import com.example.services.model.address.AddressResponse
import com.example.services.model.services.Body
import com.example.services.model.services.ServicesListResponse
import com.example.services.sharedpreference.SharedPrefClass
import com.example.services.viewmodels.services.ServicesViewModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.ServicesListAdapter

class ServicesListActivity : BaseActivity() {
    lateinit var categoriesBinding : ActivitySubCategoriesBinding
    lateinit var servicesViewModel : ServicesViewModel
    private var serVicesList = ArrayList<Body>()
    var catId = ""
    var subCatId = ""
    override fun getLayoutId() : Int {
        return R.layout.activity_sub_categories
    }

    override fun initViews() {
        categoriesBinding = viewDataBinding as ActivitySubCategoriesBinding
        servicesViewModel = ViewModelProviders.of(this).get(ServicesViewModel::class.java)

        categoriesBinding.commonToolBar.imgRight.visibility = View.GONE
        categoriesBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        categoriesBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.services)

        catId = intent.extras?.get("catId").toString()
        subCatId = intent.extras?.get("subCatId").toString()
        initRecyclerView()
        var serviceObject = JsonObject()
        serviceObject.addProperty(
            "category", catId
        )
        serviceObject.addProperty(
            "subcategory", subCatId
        )
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.getServices(serviceObject)
            startProgressDialog()
        }

        servicesViewModel.serviceListRes().observe(this,
            Observer<ServicesListResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            serVicesList.addAll(response.body!!)
                            categoriesBinding.rvSubcategories.visibility = View.VISIBLE
                            categoriesBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            categoriesBinding.rvSubcategories.visibility = View.GONE
                            categoriesBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }

                }
            })

        servicesViewModel.addRemoveCartRes().observe(this,
            Observer<CommonModel> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            servicesViewModel.getServices(serviceObject)
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }

                }
            })

    }

    private fun initRecyclerView() {
        val myJobsListAdapter = ServicesListAdapter(this, serVicesList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        val gridLayoutManager = GridLayoutManager(this, 2)
        /* gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {

             override fun getSpanSize(position: Int): Int {
                 return if (position == 16) { // totalRowCount : How many item you want to show
                     2 // the item in position now takes up 4 spans
                 } else 1
             }
         })*/

        categoriesBinding.rvSubcategories.layoutManager = gridLayoutManager
        categoriesBinding.rvSubcategories.setHasFixedSize(true)
        //linearLayoutManager.orientation = RecyclerView.VERTICAL
        //categoriesBinding.rvSubcategories.layoutManager = linearLayoutManager
        categoriesBinding.rvSubcategories.adapter = myJobsListAdapter
        categoriesBinding.rvSubcategories.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    fun addRemoveToCart(position : Int, addRemove : String) {
        var cartObject = JsonObject()
        cartObject.addProperty(
            "service_id", serVicesList[position].id
        )
        cartObject.addProperty(
            "price", serVicesList[position].price.toString()
        )
        cartObject.addProperty(
            "user_id", SharedPrefClass()!!.getPrefValue(this, GlobalConstants.USERID).toString()
        )
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.addRemoveCart(cartObject)
            startProgressDialog()
        }

    }

}
