package com.example.ecommerce.views.address

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.model.address.AddressListResponse
import com.example.ecommerce.databinding.ActivityAddressListBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.utils.DialogssInterface
import com.example.ecommerce.viewmodels.address.AddressViewModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.AddressListAdapter

class AddressListActivity : BaseActivity(), DialogssInterface {
    lateinit var addressBinding: ActivityAddressListBinding
    lateinit var addressViewModel: AddressViewModel
    private var addressesList = ArrayList<AddressListResponse.Body>()
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    var deletedAddressId = ""
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    var updateAddressObject = JsonObject()

    override fun getLayoutId(): Int {
        return R.layout.activity_address_list
    }

    override fun initViews() {
        addressBinding = viewDataBinding as ActivityAddressListBinding
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)

        addressBinding.addressViewModel = addressViewModel
        addressBinding.commonToolBar.imgRight.visibility = View.GONE
        addressBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        addressBinding.commonToolBar.imgToolbarText.text =
                resources.getString(R.string.locations)
        addressBinding.tvAddAddress.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GlobalConstants.COLOR_CODE))/*mContext.getResources().getColorStateList(R.color.colorOrange)*/)

        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
        }
        addressViewModel.getAddressList().observe(this,
                Observer<AddressListResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                addressesList.addAll(response.data!!)
                                addressBinding.rvAddresses.visibility = View.VISIBLE
                                addressBinding.tvNoRecord.visibility = View.GONE
                                initRecyclerView()
                                SharedPrefClass().putObject(
                                        this,
                                        GlobalConstants.IsAddressAdded,
                                        "true"
                                )
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                                addressBinding.rvAddresses.visibility = View.GONE
                                addressBinding.tvNoRecord.visibility = View.VISIBLE
                                SharedPrefClass().putObject(
                                        this,
                                        GlobalConstants.IsAddressAdded,
                                        "false"
                                )

                            }
                        }

                    }
                })

        addressViewModel.getAddress().observe(this,
                Observer<AddressResponse> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        addressesList.clear()
                        addressViewModel.addressList()
                        when {
                            response.code == 200 -> {
                                showToastSuccess(message)
                                // finish()
                            }
                            else -> {
                                showToastError(message)
                            }
                        }

                    }
                })
        addressViewModel.deleteAddressRes().observe(this,
                Observer<CommonModel> { response ->
                    stopProgressDialog()
                    if (response != null) {
                        val message = response.message
                        when {
                            response.code == 200 -> {
                                addressesList.clear()
                                addressViewModel.addressList()
                            }
                            else -> message?.let {
                                UtilsFunctions.showToastError(it)
                            }
                        }

                    }
                })

        addressViewModel.isClick().observe(
                this, Observer<String>(function =
        fun(it: String?) {
            when (it) {
                "tv_add_address" -> {
                    if (UtilsFunctions.isNetworkConnected()) {
                        val intent = Intent(this, AddAddressActivity::class.java)
                        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
                    }

                    //startActivity(intent)
                }
            }
        })
        )
    }

    private fun initRecyclerView() {
        val myJobsListAdapter = AddressListAdapter(this, addressesList, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        addressBinding.rvAddresses.layoutManager = linearLayoutManager
        addressBinding.rvAddresses.adapter = myJobsListAdapter
        addressBinding.rvAddresses.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })
    }

    fun deleteAddress(id: String?) {
        deletedAddressId = id!!
        confirmationDialog = mDialogClass.setDefaultDialog(
                this,
                this,
                "Delete Address",
                getString(R.string.warning_delete_address)
        )
        confirmationDialog?.show()
    }

    fun makeDefaultAddress(position: Int) {
        updateAddressObject.addProperty(
                "addressName", addressesList[position].addressName
        )
        updateAddressObject.addProperty(
                "city", addressesList[position].city
        )
        updateAddressObject.addProperty(
                "default", "1"
        )
        updateAddressObject.addProperty(
                "latitude", addressesList[position].latitude
        )
        updateAddressObject.addProperty(
                "longitude", addressesList[position].longitude
        )
        updateAddressObject.addProperty(
                "addressType", addressesList[position].addressType
        )
        updateAddressObject.addProperty(
                "houseNo", addressesList[position].houseNo
        )
        updateAddressObject.addProperty(
                "addressId", addressesList[position].id
        )

        confirmationDialog = mDialogClass.setDefaultDialog(
                this,
                this,
                "Default Address",
                getString(R.string.warning_default_address)
        )
        confirmationDialog?.show()

    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "Delete Address" -> {
                startProgressDialog()
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    startProgressDialog()
                    addressViewModel.deleteAddress(deletedAddressId)
                }

            }
            "Default Address" -> {
                //startProgressDialog()
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    startProgressDialog()
                    addressViewModel.updateAddressDetail(updateAddressObject)
                }

                // Save default address
                SharedPrefClass().putObject(
                    this,
                    GlobalConstants.PREF_DELIVERY_ADDRESS,
                    updateAddressObject.get("addressName").toString()
                )
                SharedPrefClass().putObject(
                    this,
                    GlobalConstants.PREF_DELIVERY_ADDRESS_ID,
                    updateAddressObject.get("addressId").toString()
                )
            }
        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            "Delete Address" -> confirmationDialog?.dismiss()
            "Default Address" -> {
                confirmationDialog?.dismiss()
                //  addressViewModel.addressList()
            }
        }
    }

    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addressesList.clear()
                addressViewModel.addressList()

            }
        }
    }

}
