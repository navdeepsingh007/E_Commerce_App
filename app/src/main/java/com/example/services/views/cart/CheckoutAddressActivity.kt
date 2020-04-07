package com.example.services.views.cart

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.application.MyApplication
import com.example.services.common.UtilsFunctions
import com.example.services.constants.GlobalConstants
import com.example.services.utils.BaseActivity
import com.example.services.viewmodels.cart.CartViewModel
import com.example.services.databinding.ActivityCheckoutAddressBinding
import com.example.services.model.CommonModel
import com.example.services.model.address.AddressListResponse
import com.example.services.model.cart.CartListResponse
import com.example.services.sharedpreference.SharedPrefClass
import com.example.services.utils.DialogClass
import com.example.services.utils.DialogssInterface
import com.example.services.viewmodels.address.AddressViewModel
import com.example.services.views.home.DashboardActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.CartListAdapter
import com.uniongoods.adapters.CheckoutAddressListAdapter

class CheckoutAddressActivity : BaseActivity(), DialogssInterface {
    lateinit var cartBinding: ActivityCheckoutAddressBinding
    lateinit var cartViewModel: CartViewModel
    var cartList = ArrayList<CartListResponse.Body>()
    var addressListAdapter: CheckoutAddressListAdapter? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()

    var pos = 0
    lateinit var addressViewModel: AddressViewModel
    private var addressesList = ArrayList<AddressListResponse.Body>()
    var addressId = ""
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    override fun getLayoutId(): Int {
        return R.layout.activity_checkout_address
    }

    override fun initViews() {
        cartBinding = viewDataBinding as ActivityCheckoutAddressBinding
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)

        cartBinding.commonToolBar.imgRight.visibility = View.GONE
        cartBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        cartBinding.commonToolBar.imgToolbarText.text =
                resources.getString(R.string.checkout)
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
                                cartBinding.tvTotalItems.setText(cartList.size.toString())
                                cartBinding.tvOfferPrice.setText(cartList[0].service?.currency + " " + response.coupanDetails?.payableAmount)
                                if (response.coupanDetails?.isCouponApplied.equals("true")) {
                                    if (response.coupanDetails?.isCoupanValid.equals("true")) {
                                        cartBinding.rlRealPrice.visibility = View.VISIBLE
                                        cartBinding.tvOfferPrice.setText(cartList[0].service?.currency + " " + response.coupanDetails?.payableAmount)
                                        cartBinding.tvRealPrice.setText(cartList[0].service?.currency + " " + response.coupanDetails?.totalAmount)
                                    }
                                }
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)

                            }
                        }
                    }
                })

        cartViewModel.getOrderPlaceRes().observe(this,
                Observer<CommonModel> { response ->
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
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                                finish()
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }
                    }
                })

        addressViewModel.getAddressList().observe(this,
                Observer<AddressListResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                addressesList.addAll(response.data!!)

                                var default = "false"
                                for (item in addressesList) {
                                    if (item.default.equals("1")) {
                                        default = "true"
                                        addressId = item.id.toString()
                                        cartBinding.tvAddress.text = item.addressType
                                        cartBinding.tvAddressDetail.text = item.addressName

                                        if (item.addressType.equals(getString(R.string.home))) {
                                            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_home))
                                        } else if (item.addressType.equals(getString(R.string.work))) {
                                            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_work))
                                        } else {
                                            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_other))
                                        }
                                    }
                                }
                                if (default.equals("false")) {
                                    if (addressesList.size > 0) {
                                        setAddressData(0)
                                    }
                                }
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
                "tvChange" -> {
                    addressDialog()
                }
                "btnCheckout" -> {
                    var addressObject = JsonObject()
                    addressObject.addProperty(
                            "addressId", addressId
                    )
                    cartViewModel.orderPlace(addressObject)
                }
            }
        })
        )
    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "Remove Cart" -> {
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {

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

    fun addressDialog() {
        confirmationDialog = Dialog(this, R.style.transparent_dialog_borderless)
        confirmationDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding =
                DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(this),
                        R.layout.address_list_dialog,
                        null,
                        false
                )

        confirmationDialog?.setContentView(binding.root)
        confirmationDialog?.setCancelable(false)

        confirmationDialog?.window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        confirmationDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rvAddress = confirmationDialog?.findViewById<RecyclerView>(R.id.rvAddRess)
        val cancel = confirmationDialog?.findViewById<Button>(R.id.cancel)

        addressListAdapter = CheckoutAddressListAdapter(this, addressesList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        //  val gridLayoutManager = GridLayoutManager(this, 2)
        //cartBinding.rvSubcategories.layoutManager = gridLayoutManager
        //  cartBinding.rvSubcategories.setHasFixedSize(true)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rvAddress?.layoutManager = linearLayoutManager
        rvAddress?.adapter = addressListAdapter
        rvAddress?.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })

        cancel?.setOnClickListener {
            confirmationDialog?.dismiss()
        }
        confirmationDialog?.show()
    }

    fun selectAddress(position: Int) {
        confirmationDialog?.dismiss()
        setAddressData(position)
    }

    private fun setAddressData(pos: Int) {
        addressId = addressesList[pos].id.toString()
        cartBinding.tvAddress.text = addressesList[pos].addressType
        cartBinding.tvAddressDetail.text = addressesList[pos].addressName

        if (addressesList[pos].addressType.equals(getString(R.string.home))) {
            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_home))
        } else if (addressesList[pos].addressType.equals(getString(R.string.work))) {
            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_work))
        } else {
            cartBinding.addresssImg.setImageDrawable(resources.getDrawable(R.drawable.ic_other))
        }
    }

}
