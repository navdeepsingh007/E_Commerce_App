package com.example.ecommerce.views.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.viewmodels.cart.CartViewModel
import com.example.ecommerce.viewmodels.services.ServicesViewModel
import com.example.ecommerce.databinding.ActivityCartListBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.cart.CartListResponse
import com.example.ecommerce.model.orders.CreateOrdersResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.utils.DialogssInterface
import com.example.ecommerce.views.home.DashboardActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.CartListAdapter

class CartListActivity : BaseActivity(), DialogssInterface {
    lateinit var cartBinding: ActivityCartListBinding
    lateinit var cartViewModel: CartViewModel
    lateinit var servicesViewModel: ServicesViewModel
    var cartList = ArrayList<CartListResponse.Data>()
    var myJobsListAdapter: CartListAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var cartObject = JsonObject()
    var updateCartObject = JsonObject()
    var pos = "0"
    var couponCode = ""
    var addressType = ""
    var addressId = ""

    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    override fun getLayoutId(): Int {
        return R.layout.activity_cart_list
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initViews() {
        cartBinding = viewDataBinding as ActivityCartListBinding
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        servicesViewModel = ViewModelProviders.of(this).get(ServicesViewModel::class.java)
        cartBinding.commonToolBar.imgRight.visibility = View.GONE
        cartBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        cartBinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.c_cart)
        cartBinding.cartViewModel = cartViewModel
        val userId = SharedPrefClass()!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.USERID
        ).toString()
        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
            //cartViewModel.getcartList(userId)
        }
        addressType = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.SelectedAddressType
        ).toString()

        val defaultDeliveryAddress = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.PREF_DELIVERY_ADDRESS_ID
        )
        var address = defaultDeliveryAddress?.toString() ?: ""
        addressId = address.replace("\"", "");
        cartBinding.btnCheckout.setBackgroundTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    GlobalConstants.COLOR_CODE
                )
            )/*mContext.getResources().getColorStateList(R.color.colorOrange)*/
        )

        cartViewModel.getCartListRes().observe(this,
            Observer<CartListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            cartList.addAll(response.body!!.data!!)
                            cartBinding.rvCart.visibility = View.VISIBLE
                            cartBinding.tvNoRecord.visibility = View.GONE
                            cartBinding.tvTotalItems.setText(response.body!!.totalQunatity)
                            cartBinding.tvshipping.setText(response.body!!.serviceCharges)
                            cartBinding.rvCart.visibility = View.VISIBLE
                            cartBinding.totalItemsLay1.visibility = View.VISIBLE
                            // cartBinding.rlCoupon.visibility = View.VISIBLE
                            cartBinding.btnCheckout.visibility = View.VISIBLE
                            initRecyclerView()
                            cartBinding.tvOfferPrice.setText(GlobalConstants.Currency + " " + response.body!!.sum)
                            /*if (response.coupanDetails?.isCouponApplied.equals("true")) {

                                if (response.coupanDetails?.isCoupanValid.equals("true")) {
                                    couponCode = response.coupanDetails?.coupanCode.toString()
                                    cartBinding.tvPromo.setText(response.coupanDetails?.coupanDiscount + "% " + "Discount coupon applied. Remove?")
                                    cartBinding.rlRealPrice.visibility = View.VISIBLE
                                    cartBinding.tvOfferPrice.setText(cartList[0].service?.currency + " " + response.coupanDetails?.payableAmount)
                                    cartBinding.tvRealPrice.setText(cartList[0].service?.currency + " " + response.coupanDetails?.totalAmount)
                                } else {
                                    couponCode = response.coupanDetails?.coupanCode.toString()
                                    cartBinding.tvPromo.setText("Invalid Coupon. Remove?")
                                }
                                val str = cartBinding.tvPromo.getText().toString()
                                var span = str.split(".")
                                val rr = span[1]
                                val styledString = SpannableStringBuilder(cartBinding.tvPromo.getText().toString())
                                var length = cartBinding.tvPromo.getText().toString().length
                                val startPos = length - 7
                                styledString.setSpan(StyleSpan(Typeface.BOLD), startPos, styledString.length, 0)
                                styledString.setSpan(ForegroundColorSpan(Color.RED), startPos, styledString.length, 0)
                                cartBinding.tvPromo.setText(*//*span[0] + ". " +*//* styledString)
                                } else {
                                    cartBinding.rlRealPrice.visibility = View.GONE
                                    cartBinding.tvPromo.setText(getString(R.string.apply_coupon))
                                }*/

                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            cartBinding.rvCart.visibility = View.GONE
                            cartBinding.totalItemsLay1.visibility = View.GONE
                            //  cartBinding.rlCoupon.visibility = View.GONE
                            cartBinding.btnCheckout.visibility = View.GONE
                            cartBinding.tvNoRecord.visibility = View.VISIBLE
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.SelectedAddressType,
                                "null"
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.isCartAdded,
                                "false"
                            )
                        }
                    }

                }
            })
//
        servicesViewModel.addRemoveCartRes().observe(this,
            Observer<CommonModel> { response ->

                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            cartList.clear()
                            cartViewModel.getCartList()

                        }
                        else -> message?.let {
                            stopProgressDialog()
                            UtilsFunctions.showToastError(it)
                        }
                    }

                }
            })

        cartViewModel.getOrderPlaceRes().observe(this,
            Observer<CreateOrdersResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.SelectedAddressType,
                                "null"
                            )
                            val intent = Intent(this, DashboardActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }

                }
            })

        cartViewModel.getOrderUpdateRes().observe(this,
            Observer<CommonModel> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            cartList.clear()
                            cartViewModel.getCartList()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            stopProgressDialog()
                        }
                    }

                }
            })

        cartViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    /* "tvPromo" -> {
                         if (cartBinding.tvPromo.getText().toString().equals(getString(R.string.apply_coupon))) {
                             val intent = Intent(this, PromoCodeActivity::class.java)
                             startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
                         } else {
                             confirmationDialog = mDialogClass.setDefaultDialog(
                                     this,
                                     this,
                                     "Remove Coupon",
                                     getString(R.string.warning_remove_coupon)
                             )
                             confirmationDialog?.show()

                         }
                     }*/
                    "btnCheckout" -> {
                        // if (addressType.equals(getString(R.string.home))) {
                        val intent = Intent(this, CheckoutAddressActivity::class.java)
                        startActivity(intent)
                        /*// } else {
                             showToastSuccess("Payment Api Hit")
                             var addressObject = JsonObject()
                             addressObject.addProperty(
                                     "addressId",
                             )
                             cartViewModel.orderPlace(addressObject)
                              val intent = Intent(this, CheckoutAddressActivity::class.java)
                              startActivity(intent)
                        // }*/
                    }
                }
            })
        )
    }

    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                cartList.clear()
                cartViewModel.getCartList()

            }
        }
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
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

    @SuppressLint("NewApi")
    fun addRemoveToCart(position: Int) {
        pos = cartList[position].id.toString() //position

        cartObject.addProperty(
            "serviceId", cartList[position].serviceId
        )
        cartObject.addProperty(
            "status", "false"
        )
        confirmationDialog = mDialogClass.setDefaultDialog(
            this,
            this,
            "Remove Cart",
            getString(R.string.warning_remove_cart)
        )
        confirmationDialog?.show()


    }

    fun updateCart(position: Int, quantity: Int) {

        updateCartObject.addProperty(
            "serviceId", cartList[position].serviceId.toString()
        )
        updateCartObject.addProperty(
            "companyId ", cartList[position].companyId
        )
        updateCartObject.addProperty(
            "cartId", cartList[position].id
        )
        updateCartObject.addProperty(
            "addressId",  addressId
        )
        updateCartObject.addProperty(
            "orderPrice", cartList[position].orderPrice
        )
        updateCartObject.addProperty(
            "quantity", quantity
        )
        updateCartObject.addProperty(
            "size", cartList[position].size
        )
        updateCartObject.addProperty(
            "color ", cartList[position].color
        )
        updateCartObject.addProperty(
            "orderTotalPrice", quantity * cartList[position].orderPrice!!.toInt()
        )
        if (UtilsFunctions.isNetworkConnected()) {
            cartViewModel.updateCart(updateCartObject)
            startProgressDialog()
            updateCartObject = JsonObject()
        }

    }

    fun addRemoveTofav(position: Int) {
        pos = cartList[position].id.toString() //position

        cartObject.addProperty(
            "serviceId", cartList[position].serviceId
        )
        cartObject.addProperty(
            "status", "false"
        )


    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "Remove Cart" -> {
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    servicesViewModel.removeCart(pos)
                    startProgressDialog()
                }

            }

        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            "Remove Cart" -> confirmationDialog?.dismiss()
        }
    }
}
