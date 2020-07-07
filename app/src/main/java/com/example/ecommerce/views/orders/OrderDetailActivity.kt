package com.example.ecommerce.views.orders

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.viewmodels.orders.OrdersViewModel
import com.google.gson.JsonObject
import com.example.ecommerce.databinding.ActivityOrderDetailBinding
import com.example.ecommerce.model.orders.OrdersDetailNewResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.Utils
import com.uniongoods.adapters.OrderDetailListAdapter
import com.uniongoods.adapters.OrderListAdapter

class OrderDetailActivity : BaseActivity() {

    lateinit var orderBinding: ActivityOrderDetailBinding
    lateinit var ordersViewModel: OrdersViewModel
    var orderList = ArrayList<OrdersDetailNewResponse.Suborders>()
    var orderDetailListAdapter: OrderDetailListAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var cancelOrderObject = JsonObject()
    var completeOrderObject = JsonObject()
    var pos = 0
    var orderId = ""
    var addressType = ""


    override fun initViews() {
        orderBinding = viewDataBinding as ActivityOrderDetailBinding
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        orderBinding.commonToolBar.imgRight.visibility = View.GONE
        orderBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        orderBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.order_details)
        initRecyclerView()
     //   orderBinding.cartViewModel = ordersViewModel
        orderList = ArrayList()
        ordersViewModel.getOrdersDetailRes().observe(this,
            Observer<OrdersDetailNewResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            orderList.clear()
                            orderBinding.tvOrderNo.text = response.data!!.orderNo
                            orderBinding.tvEstimateDate.text =  response.data!!.orderNo
                            orderBinding.tvOrderDate.text = Utils(this).getDate(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                response.data!!.createdAt,
                                "EEE MMM dd, yyyy hh:mm aa"
                            )
                            orderBinding.tvPurchaseDate.text = Utils(this).getDate(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                response.data!!.createdAt,
                                "EEE MMM dd, yyyy hh:mm aa"
                            )
                            orderBinding.tvItemsLabel.text = "Items("+orderList.size+")"
                            orderBinding.tvTotalItems.text = SharedPrefClass().getPrefValue(this,GlobalConstants.CurrencyPreference).toString()+response.data!!.orderPrice
                            orderBinding.tvShippingAmount.text = SharedPrefClass().getPrefValue(this,GlobalConstants.CurrencyPreference).toString()+response.data!!.serviceCharges
                            orderBinding.tvOfferPrice.text = SharedPrefClass().getPrefValue(this,GlobalConstants.CurrencyPreference).toString()+response.data!!.offerPrice
                            orderBinding.tvOfferPrice.text = SharedPrefClass().getPrefValue(this,GlobalConstants.CurrencyPreference).toString()+response.data!!.totalOrderPrice
                            orderList.addAll(response.data!!.suborders!!)

                            if (orderList.size > 0) {
                                orderBinding.rvOrder.visibility = View.VISIBLE
                                orderDetailListAdapter!!.setData(orderList)
                              //  orderBinding.tvNoRecord.visibility = View.GONE

                            } else {
                             //   orderBinding.rvOrder.visibility = View.GONE
                               // orderBinding.tvNoRecord.visibility = View.VISIBLE
                            }

                        }
                        else -> message?.let {
                           // UtilsFunctions.showToastError(it)
                           // orderBinding.rvOrder.visibility = View.GONE
                           // orderBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }

                }
            })

      //  initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        if (UtilsFunctions.isNetworkConnected()) {
            // startProgressDialog()
            orderList.clear()
            ordersViewModel.getOrderDetail("c834abf9-1b27-4ff6-899c-5b351b340a07")
        }
    }

    private fun initRecyclerView() {

        orderDetailListAdapter = OrderDetailListAdapter(this, orderList,this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        orderBinding.rvOrder.layoutManager = linearLayoutManager
        orderBinding.rvOrder.adapter = orderDetailListAdapter
        orderBinding.rvOrder.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_order_detail
    }
}
