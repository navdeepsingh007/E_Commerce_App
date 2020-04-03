package com.example.services.views.subcategories

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.common.UtilsFunctions
import com.example.services.utils.BaseActivity
import com.example.services.databinding.ActivitySubCategoriesBinding
import com.example.services.model.CommonModel
import com.example.services.model.services.Body
import com.example.services.model.services.ServicesListResponse
import com.example.services.viewmodels.services.ServicesViewModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.ServicesListAdapter

class ServicesListActivity : BaseActivity() {
    lateinit var categoriesBinding : ActivitySubCategoriesBinding
    lateinit var servicesViewModel : ServicesViewModel
    private var serVicesList = ArrayList<Body>()
    var catId = ""
    var subCatId = ""
    var pos = 0
    var servicesListAdapter : ServicesListAdapter? = null
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
        val serviceObject = JsonObject()
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

        servicesViewModel.addRemovefavRes().observe(this,
            Observer<CommonModel> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (serVicesList[pos].favorite.equals("false")) {
                                serVicesList[pos].favorite = "true"
                            } else {
                                serVicesList[pos].favorite = "false"
                            }
                            servicesListAdapter?.notifyDataSetChanged()
                            // servicesViewModel.getServices(serviceObject)
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }

                }
            })

    }

    private fun initRecyclerView() {
        servicesListAdapter = ServicesListAdapter(this, serVicesList, this)
        val gridLayoutManager = GridLayoutManager(this, 2)
        categoriesBinding.rvSubcategories.layoutManager = gridLayoutManager
        categoriesBinding.rvSubcategories.setHasFixedSize(true)
        categoriesBinding.rvSubcategories.adapter = servicesListAdapter
        categoriesBinding.rvSubcategories.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    fun addRemoveToCart(position : Int, addRemove : String) {
        var isCart = "false"
        var cartObject = JsonObject()
        cartObject.addProperty(
            "serviceId", serVicesList[position].id
        )

        if (addRemove.equals(getString(R.string.add))) {
            isCart = "true"
        } else {
            isCart = "false"
        }
        cartObject.addProperty(
            "status", isCart
        )
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.addRemoveCart(cartObject)
            startProgressDialog()
        }
    }

    fun addRemovefav(position : Int, addRemove : String) {
        var isCart = "false"
        pos = position
        var cartObject = JsonObject()
        cartObject.addProperty(
            "serviceId", serVicesList[position].id
        )
        if (serVicesList[position].favorite.equals("false")) {
            isCart = "true"
        } else {
            isCart = "false"
        }
        cartObject.addProperty(
            "status", isCart
        )
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.addRemoveFav(cartObject)
            startProgressDialog()
        }

    }

    fun callServiceDetail(serviceId : String) {
        val intent = Intent(this, ServiceDetailActivity::class.java)
        intent.putExtra("serviceId", serviceId)
        startActivity(intent)
    }

}
