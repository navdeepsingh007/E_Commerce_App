package com.example.services.views.subcategories

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.services.R
import com.example.services.application.MyApplication
import com.example.services.common.UtilsFunctions
import com.example.services.constants.GlobalConstants
import com.example.services.utils.BaseActivity
import com.example.services.viewmodels.services.ServicesViewModel
import com.example.services.databinding.ActivityServiceDetailBinding
import com.example.services.model.CommonModel
import com.example.services.model.services.DateSlotsResponse
import com.example.services.model.services.ServicesDetailResponse
import com.example.services.model.services.ServicesListResponse
import com.example.services.model.services.TimeSlotsResponse
import com.example.services.sharedpreference.SharedPrefClass
import com.example.services.utils.DialogClass
import com.example.services.utils.DialogssInterface
import com.example.services.views.cart.CartListActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.gson.JsonObject
import com.uniongoods.adapters.CartListAdapter
import com.uniongoods.adapters.DateListAdapter
import com.uniongoods.adapters.ServicesListAdapter
import com.uniongoods.adapters.TimeSlotsListAdapter

class ServiceDetailActivity : BaseActivity(), DialogssInterface {
    lateinit var serviceDetailBinding: ActivityServiceDetailBinding
    lateinit var servicesViewModel: ServicesViewModel
    var serviceId = ""
    var isCart = "false"
    var currency = "false"
    var priceAmount = "false"
    var selectedDate = ""
    var selectedTime = ""
    var quantityCount = 0
    var selectedAddressType = "1"
    var price = 0
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var timeSlotsAdapter: TimeSlotsListAdapter? = null
    var dateSlotsAdapter: DateListAdapter? = null
    var slotsList = ArrayList<TimeSlotsResponse.Body>()
    var dateList = ArrayList<DateSlotsResponse.Body>()
    var isfav = "false"
    var addressType = "false"
    // public var addressType = ""
    override fun onResume() {
        super.onResume()
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.getServiceDetail(serviceId)
            startProgressDialog()
        }
        isCart = SharedPrefClass().getPrefValue(
                MyApplication.instance,
                GlobalConstants.isCartAdded
        ).toString()
        if (isCart.equals("true")) {
            serviceDetailBinding.commonToolBar.imgRight.visibility = View.VISIBLE
        } else {
            serviceDetailBinding.commonToolBar.imgRight.visibility = View.GONE
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_service_detail
    }

    override fun initViews() {
        serviceDetailBinding = viewDataBinding as ActivityServiceDetailBinding
        servicesViewModel = ViewModelProviders.of(this).get(ServicesViewModel::class.java)

        serviceDetailBinding.commonToolBar.imgRight.visibility = View.GONE
        serviceDetailBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        serviceDetailBinding.commonToolBar.imgToolbarText.text =
                resources.getString(R.string.services_detail)
        serviceDetailBinding.servicesViewModel = servicesViewModel
        serviceId = intent.extras?.get("serviceId").toString()
        var serviceObject = JsonObject()
        serviceObject.addProperty(
                "serviceId", serviceId
        )

        addressType = SharedPrefClass().getPrefValue(
                MyApplication.instance,
                GlobalConstants.SelectedAddressType
        ).toString()
        serviceDetailBinding.radioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    // selectedAddressType=radio.text.toString()

                    if (radio.text.toString().equals(getString(R.string.at_home))) {
                        selectedAddressType = "0"
                    } else {
                        selectedAddressType = "1"
                    }
                })

        // initDateRecyclerView()
        servicesViewModel.getServiceDetailRes().observe(this,
                Observer<ServicesDetailResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                serviceDetailBinding.serviceDetail = response.data
                                var rt = response.data!!.rating
                                isfav = response.data!!.favorite!!
                                isCart = response.data!!.cart!!
                                currency = response.data!!.currency.toString()
                                priceAmount = response.data!!.price.toString()
                                serviceDetailBinding.tvOfferPrice.setText(currency + " " + priceAmount)
                                serviceDetailBinding.rBar.setRating(response.data!!.rating!!.toFloat())
                                Glide.with(this)
                                        .load(response.data!!.icon)
                                        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                                        .placeholder(R.drawable.ic_category)
                                        .into(serviceDetailBinding.imgService)
                                serviceDetailBinding.imgAddFavorite.bringToFront()
                                serviceDetailBinding.rBar.bringToFront()
                                if (response.data!!.cart.equals("false")) {
                                    serviceDetailBinding.AddCart.setText(getString(R.string.add_to_cart))
                                } else {
                                    serviceDetailBinding.AddCart.setText(getString(R.string.remove_to_cart))
                                }
                                if (response.data!!.favorite.equals("false")) {
                                    serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_unfavorite)
                                } else {
                                    serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_favorite)
                                }
                                // serviceDetailBinding.servicesViewModel=response.body
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }

                    }
                })

        servicesViewModel.addRemoveCartRes().observe(this,
                Observer<CommonModel> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                serviceDetailBinding.AddCart.isEnabled = true

                                if (isCart.equals("false")) {
                                    serviceDetailBinding.AddCart.setText(getString(R.string.add_to_cart))
                                } else {
                                    serviceDetailBinding.commonToolBar.imgRight.visibility = View.VISIBLE
                                    serviceDetailBinding.AddCart.setText(getString(R.string.remove_to_cart))
                                    SharedPrefClass().putObject(
                                            this,
                                            GlobalConstants.isCartAdded,
                                            "true"
                                    )
                                    /* val intent = Intent(this, CartListActivity::class.java)
                                     startActivity(intent)*/
                                    serviceDetailBinding.llSlots.visibility = View.GONE
                                }

                                //servicesViewModel.getServices(serviceObject)
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }

                    }
                })

        servicesViewModel.addRemovefavRes().observe(this,
                Observer<CommonModel> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                if (isfav.equals("false")) {
                                    isfav = "true"
                                    serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_favorite)
                                } else {
                                    isfav = "false"
                                    serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_unfavorite)
                                }
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }

                    }
                })

        servicesViewModel.getTimeSlotsRes().observe(this,
                Observer<TimeSlotsResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                if (quantityCount != 0) {
                                    /* slotsList.clear()
                                     slotsList.addAll(response.data!!)
                                     serviceDetailBinding.rvSlots.visibility = View.VISIBLE
                                     serviceDetailBinding.tvNoRecord.visibility = View.GONE
                                     serviceDetailBinding.tvTimeSlots.visibility = View.VISIBLE
                                     serviceDetailBinding.btnSubmit.visibility = View.VISIBLE
                                     initRecyclerView()*/
                                }

                            }
                            else -> {
                                /*message?.let {
                                    UtilsFunctions.showToastError(it)
                                    serviceDetailBinding.tvNoRecord.setText(message)
                                }
                                serviceDetailBinding.btnSubmit.visibility = View.GONE
                                serviceDetailBinding.rvSlots.visibility = View.GONE
                                serviceDetailBinding.tvTimeSlots.visibility = View.GONE
                                serviceDetailBinding.tvNoRecord.visibility = View.VISIBLE*/

                            }
                        }

                    }
                })
        servicesViewModel.getDateSlotsRes().observe(this,
                Observer<DateSlotsResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                dateList.clear()
                                dateList.addAll(response.data!!)
                                /* serviceDetailBinding.rvDate.visibility = View.VISIBLE
                                 serviceDetailBinding.tvDateRecord.visibility = View.GONE
                                 // serviceDetailBinding.btnSubmit.visibility = View.VISIBLE
                                 initDateRecyclerView()*/
                            }
                            else -> {
                                /*message?.let {
                                    UtilsFunctions.showToastError(it)
                                    //  serviceDetailBinding.tvNoRecord.setText(message)
                                }*/
                                //serviceDetailBinding.rvDate.visibility = View.GONE
                                serviceDetailBinding.tvSelectdateMsg.visibility = View.GONE
                                // serviceDetailBinding.tvDateRecord.visibility = View.VISIBLE

                            }
                        }

                    }
                })

        servicesViewModel.isClick().observe(
                this, Observer<String>(function =
        fun(it: String?) {
            when (it) {
                "img_right" -> {
                    val intent = Intent(this, CartListActivity::class.java)
                    startActivity(intent)
                }
                "AddCart" -> {
                    if (isCart.equals("false")) {
                        if (TextUtils.isEmpty(addressType) || addressType.equals("null")) {
                            confirmationDialog = mDialogClass.setDefaultDialog(
                                    this,
                                    this,
                                    "Address Type",
                                    getString(R.string.select_address_type)
                            )
                            confirmationDialog?.show()
                        } else {
                            showCartInfoLayout()
                        }

                    } else {
                        //remove from cart
                        callAddRemoveCartApi()
                    }
                }
                "img_cross" -> {
                    serviceDetailBinding.AddCart.isEnabled = true
                    serviceDetailBinding.llSlots.visibility = View.GONE
                }
                "imgMinus" -> {
                    if (quantityCount > 0) {
                        quantityCount--
                        price = quantityCount * priceAmount.toInt()
                        serviceDetailBinding.tvTotalPrice.setText(currency + price.toString())
                        //callGetTimeSlotsApi()
                    }
                    if (quantityCount == 0) {
                        serviceDetailBinding.tvTotalPrice.setText("0")
                        serviceDetailBinding.tvTimeSlots.visibility = View.GONE
                        // serviceDetailBinding.btnSubmit.isEnabled = false
                        // serviceDetailBinding.btnSubmit.visibility = View.GONE
                        serviceDetailBinding.rvSlots.visibility = View.GONE
                    }
                    serviceDetailBinding.tvQuantity.setText(quantityCount.toString())
                }
                "imgPlus" -> {
                    if (quantityCount <= 5) {
                        quantityCount++
                        // serviceDetailBinding.btnSubmit.isEnabled = false
                        serviceDetailBinding.tvQuantity.setText(quantityCount.toString())
                        //   serviceDetailBinding.btnSubmit.visibility = View.VISIBLE
                        //callGetTimeSlotsApi()
                        price = quantityCount * priceAmount.toInt()
                        serviceDetailBinding.tvTotalPrice.setText(currency + price.toString())
                    }


                }
                "btnSubmit" -> {
                    if (quantityCount == 0) {
                        showToastError(getString(R.string.select_quantity_msg))
                    } else {
                        callAddRemoveCartApi()
                    }

                }
                "img_add_favorite" -> {
                    addRemovefav()
                }
            }

        })
        )

    }

    private fun showCartInfoLayout() {
        var i = 0
        for (item in dateList) {

            dateList[i].selected = "false"
            i++
        }
        if (addressType.equals(getString(R.string.home))) {
            serviceDetailBinding.radioGroup.check(R.id.rd_home)
        } else {
            serviceDetailBinding.radioGroup.check(R.id.rd_shop)
        }

        serviceDetailBinding.radioGroup.isEnabled = false
        //  servCaticeDetailBinding.radioGroup.rdHome.isEnabled = false
        serviceDetailBinding.radioGroup.getChildAt(0).isEnabled = false
        serviceDetailBinding.radioGroup.getChildAt(1).isEnabled = false

        selectedTime = ""
        serviceDetailBinding.tvTotalPrice.setText("0")
        dateSlotsAdapter?.notifyDataSetChanged()
        serviceDetailBinding.llSlots.visibility = View.VISIBLE
        //serviceDetailBinding.btnSubmit.visibility = View.GONE
        serviceDetailBinding.rvSlots.visibility = View.GONE
        serviceDetailBinding.tvTimeSlots.visibility = View.GONE
        var animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        animation.setDuration(500)
        serviceDetailBinding.llSlots.setAnimation(animation)
        serviceDetailBinding.llSlots.animate()
        animation.start()
        quantityCount = 0
        selectedDate = ""
        serviceDetailBinding.tvQuantity.setText(quantityCount.toString())
        serviceDetailBinding.AddCart.isEnabled = false

    }

    private fun callAddRemoveCartApi() {

        if (serviceDetailBinding.AddCart.getText().toString().equals(getString(R.string.add_to_cart))) {
            isCart = "true"
        } else {
            isCart = "false"
        }
        var cartObject = JsonObject()
        cartObject.addProperty(
                "serviceId", serviceId
        )
        cartObject.addProperty(
                "status", isCart
        )
        cartObject.addProperty(
                "date", selectedDate
        )
        cartObject.addProperty(
                "servicestatus", selectedAddressType
        )
        cartObject.addProperty(
                "price", price
        )
        cartObject.addProperty(
                "timeSlotId", selectedTime
        )
        cartObject.addProperty(
                "quantity", quantityCount
        )

        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.addRemoveCart(cartObject)
            startProgressDialog()
        }
    }

    private fun callGetTimeSlotsApi() {
        price = quantityCount * priceAmount.toInt()
        serviceDetailBinding.tvTotalPrice.setText(currency + price.toString())
        if (!TextUtils.isEmpty(selectedDate) && quantityCount != 0) {
            var cartObject = JsonObject()
            cartObject.addProperty(
                    "serviceId", serviceId
            )
            cartObject.addProperty(
                    "quantity", quantityCount
            )
            servicesViewModel.getTimeSlot(cartObject)
        }
    }

    fun addRemovefav() {
        var isCart = "false"
        var favObject = JsonObject()

        if (isfav.equals("false")) {
            isCart = "true"
        } else {
            isCart = "false"
        }
        favObject.addProperty(
                "status", isCart
        )
        favObject.addProperty(
                "serviceId", serviceId
        )
        if (UtilsFunctions.isNetworkConnected()) {
            servicesViewModel.addRemoveFav(favObject)
            startProgressDialog()
        }
    }

    /*private fun initRecyclerView() {
        timeSlotsAdapter = TimeSlotsListAdapter(this, slotsList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        serviceDetailBinding.rvSlots.layoutManager = linearLayoutManager
        serviceDetailBinding.rvSlots.adapter = timeSlotsAdapter
        serviceDetailBinding.rvSlots.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

    private fun initDateRecyclerView() {
        // serviceDetailBinding.rvDate.visibility=View.GONE
        dateSlotsAdapter = DateListAdapter(this, dateList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        serviceDetailBinding.rvDate.layoutManager = linearLayoutManager
        serviceDetailBinding.rvDate.adapter = dateSlotsAdapter
        serviceDetailBinding.rvDate.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }*/

    fun selectTimeSlot(position: Int) {
        var i = 0
        for (item in slotsList) {

            slotsList[i].selected = "false"
            i++
        }
        slotsList[position].selected = "true"
        //selectedTime = slotsList[position].timing!!
        selectedTime = slotsList[position].id!!
        timeSlotsAdapter?.notifyDataSetChanged()

    }

    fun selectDatelot(position: Int) {
        if (quantityCount == 0) {
            showToastError(getString(R.string.select_quantity_msg))
        } else {
            var i = 0
            for (item in dateList) {

                dateList[i].selected = "false"
                i++
            }
            dateList[position].selected = "true"
            dateSlotsAdapter?.notifyDataSetChanged()
            selectedDate = dateList[position].date!!
            callGetTimeSlotsApi()
        }
    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "Address Type" -> {
                confirmationDialog?.dismiss()

                addressType = SharedPrefClass().getPrefValue(
                        MyApplication.instance,
                        GlobalConstants.SelectedAddressType
                ).toString()

                var isAddressAdded = SharedPrefClass().getPrefValue(
                        MyApplication.instance,
                        GlobalConstants.IsAddressAdded
                ).toString()

                if (addressType.equals("Home")) {
                    if (isAddressAdded.equals("true")) {
                        showCartInfoLayout()
                    } else {
                        addressType = ""
                        SharedPrefClass().putObject(
                                this,
                                GlobalConstants.SelectedAddressType,
                                "null"
                        )
                        showToastError(getString(R.string.add_address_msg))
                    }
                } else {
                    showCartInfoLayout()
                }

                /* if (isAddressAdded.equals("true")) {
                     if (GlobalConstants.IsAddressAdded.equals("true")) {
                         SharedPrefClass().putObject(
                                 this,
                                 GlobalConstants.SelectedAddressType,
                                 "home"
                         )
                         addressType = "home"
                         showCartInfoLayout()
                     } else {
                         SharedPrefClass().putObject(
                                 this,
                                 GlobalConstants.SelectedAddressType,
                                 "null"
                         )
                         // GlobalConstants.SelectedAddresssType = ""
                         showToastError("Please add address in Address Management Section")
                     }
                 } else {
                     SharedPrefClass().putObject(
                             this,
                             GlobalConstants.SelectedAddressType,
                             "shop"
                     )
                     addressType = "shop"
                     showCartInfoLayout()
                 }*/

            }

        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            "Address Type" -> confirmationDialog?.dismiss()

        }
    }

}
