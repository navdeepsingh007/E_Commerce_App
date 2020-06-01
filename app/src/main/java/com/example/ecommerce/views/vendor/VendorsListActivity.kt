package com.example.ecommerce.views.vendor

import android.app.Dialog
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.DialogClass
import com.google.gson.JsonObject
import com.example.ecommerce.databinding.ActivityFavoriteListBinding
import com.example.ecommerce.model.vendor.VendorListResponse
import com.example.ecommerce.viewmodels.vendor.VendorsViewModel
import com.uniongoods.adapters.VendorsListAdapter

class VendorsListActivity : BaseActivity() {
    lateinit var favoriteBinding: ActivityFavoriteListBinding
    lateinit var vendorsViewModel: VendorsViewModel
    var vendorList = ArrayList<VendorListResponse.Body>()
    var vendorsListAdapter: VendorsListAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var cartObject = JsonObject()
    var pos = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_favorite_list
    }

    override fun initViews() {
        favoriteBinding = viewDataBinding as ActivityFavoriteListBinding
        vendorsViewModel = ViewModelProviders.of(this).get(VendorsViewModel::class.java)

        favoriteBinding.commonToolBar.imgRight.visibility = View.GONE
        favoriteBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        favoriteBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.vendor)

        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
            vendorsViewModel.getVendorList(GlobalConstants.CATEGORY_SELECTED, "0.0", "0.0")
            //cartViewModel.getvendorList(userId)
        }
        vendorsViewModel.getVendorListRes().observe(this,
            Observer<VendorListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            vendorList.addAll(response.body!!)
                            favoriteBinding.rvFavorite.visibility = View.VISIBLE
                            favoriteBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            favoriteBinding.rvFavorite.visibility = View.GONE
                            favoriteBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }

                }
            })
    }


    private fun initRecyclerView() {
        vendorsListAdapter = VendorsListAdapter(this, vendorList)
        // val linearLayoutManager = LinearLayoutManager(this)
        val gridLayoutManager = GridLayoutManager(this, 1)
        favoriteBinding.rvFavorite.layoutManager = gridLayoutManager
        favoriteBinding.rvFavorite.setHasFixedSize(true)
        // linearLayoutManager.orientation = RecyclerView.VERTICAL
        // favoriteBinding.rvFavorite.layoutManager = linearLayoutManager
        favoriteBinding.rvFavorite.adapter = vendorsListAdapter
        favoriteBinding.rvFavorite.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

}
