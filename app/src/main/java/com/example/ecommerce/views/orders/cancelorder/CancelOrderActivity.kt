package com.example.ecommerce.views.orders.cancelorder

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityCancelOrderBinding
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.viewmodels.orders.OrdersViewModel
import com.google.gson.JsonObject
import com.example.ecommerce.databinding.ActivityOrderDetailBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.orders.OrdersDetailNewResponse
import com.example.ecommerce.model.orders.ReasonListResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.Utils
import com.payumoney.sdkui.ui.utils.ToastUtils
import com.uniongoods.adapters.CancelOrderReasonAdapter
import com.uniongoods.adapters.OrderDetailListAdapter
import com.uniongoods.adapters.OrderListAdapter

class CancelOrderActivity : BaseActivity() {

    lateinit var orderBinding: ActivityCancelOrderBinding
    lateinit var ordersViewModel: OrdersViewModel
    var reasonList = ArrayList<ReasonListResponse.Body>()
    var cancelOrderReasonAdapter: CancelOrderReasonAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var cancelOrderObject = JsonObject()
    var completeOrderObject = JsonObject()
    var pos = 0
    var orderId = ""
    var addressType = ""


    override fun initViews() {
        orderBinding = viewDataBinding as ActivityCancelOrderBinding
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
        orderBinding.commonToolBar.imgRight.visibility = View.GONE
        orderBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        orderBinding.commonToolBar.imgToolbarText.text =
            "Cancel Order"
        if (intent.hasExtra("orderId")) {
            val extras = intent.extras
            orderId = (extras!!.getString("orderId"))
        }
        initRecyclerView()
        orderBinding.etDescription.isEnabled = false
        orderBinding.rbReason.setOnCheckedChangeListener { buttonView, isChecked ->
            orderBinding.etDescription.isEnabled = isChecked
            if (orderBinding.rbReason.isPressed) {
                cancelOrderReasonAdapter!!.handleListRadioButton()
            }
        }
     //   orderBinding.cartViewModel = ordersViewModel
        orderBinding.btnSubmit.setOnClickListener {
            if (UtilsFunctions.isNetworkConnected()) {
                cancelOrder()
                ordersViewModel.cancelOrder(cancelOrderObject)
                ordersViewModel.getCancelOrderRes().observe(this,
                    Observer<CommonModel> { response ->
                        stopProgressDialog()
                        if (response != null) {
                            val message = response.message
                            when {
                                response.code == 200 -> {
                                    showToastSuccess(message)
                                    finish()
                                }
                                else -> message?.let {
                                    UtilsFunctions.showToastError(it)
                                }
                            }

                        }
                    })
            }
        }
        reasonList = ArrayList()
        ordersViewModel.getReasonResponse().observe(this,
            Observer<ReasonListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            reasonList.clear()

                               reasonList.addAll(response.data!!)

                            if (reasonList.size > 0) {
                             //   reasonList.add()
                                orderBinding.rvOrder.visibility = View.VISIBLE
                                cancelOrderReasonAdapter!!.setData(reasonList)
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
            reasonList.clear()
            ordersViewModel.getReason()
        }
    }

    private fun initRecyclerView() {

        cancelOrderReasonAdapter = CancelOrderReasonAdapter(this, reasonList,this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        orderBinding.rvOrder.layoutManager = linearLayoutManager
        orderBinding.rvOrder.adapter = cancelOrderReasonAdapter
        orderBinding.rvOrder.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cancel_order
    }

    fun handleRadioButton(){
        if (orderBinding.rbReason.isChecked) {
            orderBinding.rbReason.isChecked = false
        }
    }

    fun cancelOrder() {
        cancelOrderObject.addProperty(
            "orderId", orderId
        )
        if (orderBinding.rbReason.isChecked) {
            cancelOrderObject.addProperty(
                "cancellationReason", "other"
            )
            if (!TextUtils.isEmpty(orderBinding.etDescription.text.toString())) {
                cancelOrderObject.addProperty(
                    "otherReason", orderBinding.etDescription.text.toString()
                )
            }else{
                showToastError("Please enter your reason")
               // Toast.makeText(this, "Please enter your reason", Toast.LENGTH_SHORT).show()
                return
            }
        }
        else{
            cancelOrderObject.addProperty(
                "cancellationReason", cancelOrderReasonAdapter!!.getSelectedReason()
            )
            cancelOrderObject.addProperty(
                "otherReason", ""
            )
        }

       /* confirmationDialog = mDialogClass.setDefaultDialog(
            this,
            this,
            "Cancel Order",
            getString(R.string.warning_cancel_order)
        )
        confirmationDialog?.show()*/

    }
}
