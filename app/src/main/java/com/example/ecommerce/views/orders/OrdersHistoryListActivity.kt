package com.example.ecommerce.views.orders

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.databinding.ActivityOrderListBinding
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.viewmodels.orders.OrdersViewModel
import com.uniongoods.adapters.OrderListAdapter

class OrdersHistoryListActivity : BaseActivity() {
    lateinit var orderBinding: ActivityOrderListBinding
    lateinit var ordersViewModel: OrdersViewModel
    var orderList = ArrayList<OrdersListResponse.Body>()
    var orderListAdapter: OrderListAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_order_list
    }

    override fun initViews() {
        orderBinding = viewDataBinding as ActivityOrderListBinding
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        orderBinding.commonToolBar.imgRight.visibility = View.GONE
        orderBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        orderBinding.commonToolBar.imgToolbarText.text =
                resources.getString(R.string.orders_history)
        orderBinding.cartViewModel = ordersViewModel

        ordersViewModel.getOrdersHistoryListRes().observe(this,
                Observer<OrdersListResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                orderList.addAll(response.data!!)
                                if (orderList.size > 0) {
                                    orderBinding.rvOrder.visibility = View.VISIBLE
                                    orderBinding.tvNoRecord.visibility = View.GONE
                                    initRecyclerView()
                                } else {
                                    UtilsFunctions.showToastError(getString(R.string.no_record_found))
                                    orderBinding.rvOrder.visibility = View.GONE
                                    orderBinding.tvNoRecord.visibility = View.VISIBLE
                                }

                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                                orderBinding.rvOrder.visibility = View.GONE
                                orderBinding.tvNoRecord.visibility = View.VISIBLE

                            }
                        }

                    }
                })

    }

    private fun initRecyclerView() {
        orderListAdapter = OrderListAdapter(this, orderList, null)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        orderBinding.rvOrder.layoutManager = linearLayoutManager
        orderBinding.rvOrder.adapter = orderListAdapter
        orderBinding.rvOrder.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }
}
