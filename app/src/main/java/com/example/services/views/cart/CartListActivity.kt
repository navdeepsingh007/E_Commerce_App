package com.example.services.views.cart

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.application.MyApplication
import com.example.services.common.FirebaseFunctions
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
import com.example.services.viewmodels.promocode.PromoCodeViewModel
import com.example.services.views.address.AddAddressActivity
import com.example.services.views.home.DashboardActivity
import com.example.services.views.promocode.PromoCodeActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.CartListAdapter
import com.uniongoods.adapters.ServicesListAdapter
import org.json.JSONException

class CartListActivity : BaseActivity(), DialogssInterface {
    lateinit var cartBinding: ActivityCartListBinding
    lateinit var cartViewModel: CartViewModel
    lateinit var servicesViewModel: ServicesViewModel
    var cartList = ArrayList<CartListResponse.Body>()
    var myJobsListAdapter: CartListAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var cartObject = JsonObject()
    var pos = 0
    var couponCode = ""
    lateinit var promcodeViewModel: PromoCodeViewModel
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    override fun getLayoutId(): Int {
        return R.layout.activity_cart_list
    }

    override fun initViews() {
        cartBinding = viewDataBinding as ActivityCartListBinding
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        servicesViewModel = ViewModelProviders.of(this).get(ServicesViewModel::class.java)
        promcodeViewModel = ViewModelProviders.of(this).get(PromoCodeViewModel::class.java)
        cartBinding.commonToolBar.imgRight.visibility = View.GONE
        cartBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        cartBinding.commonToolBar.imgToolbarText.text =
                resources.getString(R.string.cart)
        cartBinding.cartViewModel = cartViewModel
        val userId = SharedPrefClass()!!.getPrefValue(
                MyApplication.instance,
                GlobalConstants.USERID
        ).toString()
        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
            //cartViewModel.getcartList(userId)
        }
        cartViewModel.getCartListRes().observe(this,
                Observer<CartListResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                cartList.addAll(response.data!!)
                                cartBinding.rvCart.visibility = View.VISIBLE
                                cartBinding.tvNoRecord.visibility = View.GONE
                                cartBinding.tvTotalItems.setText(cartList.size.toString())
                                cartBinding.rvCart.visibility = View.VISIBLE
                                cartBinding.totalItemsLay1.visibility = View.VISIBLE
                                cartBinding.rlCoupon.visibility = View.VISIBLE
                                cartBinding.btnCheckout.visibility = View.VISIBLE
                                initRecyclerView()
                                cartBinding.tvOfferPrice.setText(response.coupanDetails?.payableAmount)
                                if (response.coupanDetails?.isCouponApplied.equals("true")) {

                                    if (response.coupanDetails?.isCoupanValid.equals("true")) {
                                        couponCode = response.coupanDetails?.coupanCode.toString()
                                        cartBinding.tvPromo.setText(response.coupanDetails?.coupanDiscount + "% " + "Discount coupon applied. Remove?")
                                        cartBinding.rlRealPrice.visibility = View.VISIBLE
                                        cartBinding.tvOfferPrice.setText(response.coupanDetails?.payableAmount)
                                        cartBinding.tvRealPrice.setText(response.coupanDetails?.totalAmount)
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
                                    cartBinding.tvPromo.setText(/*span[0] + ". " +*/ styledString)
                                } else {
                                    cartBinding.rlRealPrice.visibility = View.GONE
                                    cartBinding.tvPromo.setText(getString(R.string.apply_coupon))
                                }

                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                                cartBinding.rvCart.visibility = View.GONE
                                cartBinding.totalItemsLay1.visibility = View.GONE
                                cartBinding.rlCoupon.visibility = View.GONE
                                cartBinding.btnCheckout.visibility = View.GONE
                                cartBinding.tvNoRecord.visibility = View.VISIBLE
                            }
                        }

                    }
                })
//
        servicesViewModel.addRemoveCartRes().observe(this,
                Observer<CommonModel> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                //cartViewModel.getcartList(userId)
                                cartList.removeAt(pos)
                                if (cartList.size > 0) {
                                    myJobsListAdapter?.notifyDataSetChanged()
                                } else {
                                    cartBinding.rvCart.visibility = View.GONE
                                    cartBinding.totalItemsLay1.visibility = View.GONE
                                    cartBinding.rlCoupon.visibility = View.GONE
                                    cartBinding.btnCheckout.visibility = View.GONE

                                    cartBinding.tvNoRecord.visibility = View.VISIBLE
                                }

                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }

                    }
                })

        promcodeViewModel.getRemovePromoRes().observe(this,
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
                            }
                        }

                    }
                })

        cartViewModel.isClick().observe(
                this, Observer<String>(function =
        fun(it: String?) {
            when (it) {
                "tvPromo" -> {
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

                    //  finish()

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

    fun addRemoveToCart(position: Int) {
        pos = position

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

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "Remove Cart" -> {
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    servicesViewModel.addRemoveCart(cartObject)
                    startProgressDialog()
                }

            }
            "Remove Coupon" -> {
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    promcodeViewModel.removePromoCode(couponCode)
                }

            }
        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            "Remove Cart" -> confirmationDialog?.dismiss()
            "Remove Coupon" -> confirmationDialog?.dismiss()
        }
    }
}