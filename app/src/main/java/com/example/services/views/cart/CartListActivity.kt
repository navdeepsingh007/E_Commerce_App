package com.example.services.views.cart

import android.app.Dialog
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.application.MyApplication
import com.example.services.common.UtilsFunctions
import com.example.services.constants.GlobalConstants
import com.example.services.utils.BaseActivity
import com.example.services.viewmodels.cart.CartViewModel
import com.example.services.viewmodels.services.ServicesViewModel
import com.example.services.databinding.ActivityCartListBinding
import com.example.services.model.CommonModel
import com.example.services.model.cart.CartListResponse
import com.example.services.model.services.ServicesListResponse
import com.example.services.sharedpreference.SharedPrefClass
import com.example.services.utils.DialogClass
import com.example.services.utils.DialogssInterface
import com.google.gson.JsonObject
import com.uniongoods.adapters.CartListAdapter
import com.uniongoods.adapters.ServicesListAdapter

class CartListActivity : BaseActivity(), DialogssInterface {
    lateinit var cartBinding : ActivityCartListBinding
    lateinit var cartViewModel : CartViewModel
    lateinit var servicesViewModel : ServicesViewModel
    var cartList = ArrayList<CartListResponse.Body>()
    var myJobsListAdapter : CartListAdapter?=null
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var cartObject = JsonObject()
    var pos=0
    override fun getLayoutId() : Int {
        return R.layout.activity_cart_list
    }

    override fun initViews() {
        cartBinding = viewDataBinding as ActivityCartListBinding
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        servicesViewModel = ViewModelProviders.of(this).get(ServicesViewModel::class.java)

        cartBinding.commonToolBar.imgRight.visibility = View.GONE
        cartBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        cartBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.cart)
        val userId = SharedPrefClass()!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.USERID
        ).toString()
        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
            //cartViewModel.getcartList(userId)
        }
        cartViewModel.getCartListRes().observe(this,
            Observer<CartListResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            cartList.addAll(response.data!!)
                            cartBinding.rvCart.visibility = View.VISIBLE
                            cartBinding.tvNoRecord.visibility = View.GONE
                            cartBinding.tvTotalItems.setText(cartList.size.toString())
                            initRecyclerView()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            cartBinding.rvCart.visibility = View.GONE
                            cartBinding.tvNoRecord.visibility = View.VISIBLE
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
                            //cartViewModel.getcartList(userId)
                            cartList.removeAt(pos)
                            if(cartList.size>0){
                                myJobsListAdapter?.notifyDataSetChanged()
                            }else{
                                cartBinding.rvCart.visibility = View.GONE
                                cartBinding.tvNoRecord.visibility = View.VISIBLE
                            }

                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }

                }
            })
    }

    private fun initRecyclerView() {
          myJobsListAdapter = CartListAdapter(this, cartList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        //  val gridLayoutManager = GridLayoutManager(this, 2)
        //cartBinding.rvSubcategories.layoutManager = gridLayoutManager
        //  cartBinding.rvSubcategories.setHasFixedSize(true)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        cartBinding.rvCart.layoutManager = linearLayoutManager
        cartBinding.rvCart.adapter = myJobsListAdapter
        cartBinding.rvCart.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    fun addRemoveToCart(position : Int) {
        pos=position

        cartObject.addProperty(
            "serviceId", cartList[position].serviceId
        )
        cartObject.addProperty(
            "status", "false"
        )
        confirmationDialog =mDialogClass.setDefaultDialog(
            this,
            this,
            "Remove Cart",
            getString(R.string.warning_remove_cart)
        )
        confirmationDialog?.show()


    }

    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        when (mKey) {
            "Remove Cart" -> {
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    servicesViewModel.addRemoveCart(cartObject)
                    startProgressDialog()
                }
            }
        }
    }

    override fun onDialogCancelAction(mView : View?, mKey : String) {
        when (mKey) {
            "Remove Cart" -> confirmationDialog?.dismiss()

        }
    }
}
