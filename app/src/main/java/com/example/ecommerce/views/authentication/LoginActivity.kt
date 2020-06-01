package com.example.ecommerce.views.authentication

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.text.TextUtils
import android.view.View
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.FirebaseFunctions
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.ExtrasConstants
import com.example.ecommerce.viewmodels.LoginViewModel
import com.example.ecommerce.views.home.DashboardActivity
import com.example.ecommerce.views.home.LandingMainActivity
import com.google.gson.JsonObject

class LoginActivity : BaseActivity() {
    private lateinit var activityLoginbinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private val mJsonObject = JsonObject()

    override fun initViews() {
        activityLoginbinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activityLoginbinding.loginViewModel = loginViewModel
        activityLoginbinding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.login)
        activityLoginbinding.commonToolBar.toolbar.visibility = View.INVISIBLE
        // activityLoginbinding.imgArrow.bringToFront()
        loginViewModel.checkEmailExistence().observe(this,
            Observer<LoginResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
//                            FirebaseFunctions.sendOTP("login", mJsonObject, this)
                            mJsonObject.addProperty("phoneNumber", response.data?.phoneNumber)
                            mJsonObject.addProperty("countryCode", response.data?.countryCode)

                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.ACCESS_TOKEN,
                                response.data!!.sessionToken
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.USERID,
                                response.data!!.id
                            )
                            SharedPrefClass().putObject(
                                this,
                                getString(R.string.first_name),
                                response.data!!.firstName + " " + response.data!!.lastName
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.isCartAdded,
                                "false"
                            )

                            /* SharedPrefClass().putObject(
                                     this,
                                     GlobalConstants.IsAddressAdded,
                                     response.data!!.isAddressAdded
                             )*/

                            SharedPrefClass().putObject(
                                this,
                                getString(R.string.key_phone),
                                response.data!!.phoneNumber
                            )
                            SharedPrefClass().putObject(
                                this,
                                getString(R.string.key_country_code),
                                response.data!!.countryCode
                            )
                            SharedPrefClass().putObject(
                                this,
                                getString(R.string.key_country_code),
                                response.data!!.countryCode
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.USER_IAMGE,
                                response.data!!.image
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.PREF_COMPANY_ID,
                                ExtrasConstants.COMPANY_ID
                            )
                            SharedPrefClass().putObject(
                                MyApplication.instance.applicationContext,
                                "isLogin",
                                true
                            )

                            /* val intent = Intent(this, LandingMainActivity::class.java)
                             intent.putExtra("from","splash")
                             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                             intent.putExtra("data", mJsonObject.toString())
                             startActivity(intent)
                             finish()*/

//                            startActivity(Intent(this, DashboardActivity::class.java))

                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> showToastError(message)
                    }

                }
            })


        loginViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                val phone = activityLoginbinding.etPhoneNo.text.toString()
                when (it) {
                    "btn_login" -> {
                        when {
                            TextUtils.isEmpty(phone) -> run {
                                activityLoginbinding.etPhoneNo.requestFocus()
                                activityLoginbinding.etPhoneNo.error =
                                    getString(R.string.empty) + " " + getString(
                                        R.string.phone_number
                                    )
                            }
                            else -> {
                                mJsonObject.addProperty("phoneNumber", phone)
                                mJsonObject.addProperty(
                                    "deviceToken",
                                    SharedPrefClass().getPrefValue(
                                        MyApplication.instance,
                                        GlobalConstants.NOTIFICATION_TOKEN
                                    ) as String
                                )
                                val versionName = MyApplication.instance.packageManager
                                    .getPackageInfo(MyApplication.instance.packageName, 0)
                                    .versionName
                                val androidId = UtilsFunctions.getAndroidID()

                                mJsonObject.addProperty("platform", GlobalConstants.PLATFORM)
                                mJsonObject.addProperty(
                                    "countryCode",
                                    "+" + activityLoginbinding.btnCcp.selectedCountryCode
                                )
                                mJsonObject.addProperty(
                                    "companyId",
                                    "25cbf58b-46ba-4ba2-b25d-8f8f653e9f13"
                                )
//                                mJsonObject.addProperty("device_id", androidId)
//                                mJsonObject.addProperty("app-version", versionName)
                                loginViewModel.checkPhoneExistence(mJsonObject)

                                SharedPrefClass().putObject(
                                    applicationContext,
                                    getString(R.string.key_phone),
                                    phone
                                )

                                SharedPrefClass().putObject(
                                    applicationContext,
                                    getString(R.string.key_country_code),
                                    "+" + activityLoginbinding.btnCcp.selectedCountryCode
                                )
                                //  FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                                /*    val mSmsBroadcastReceiver = SMSBroadcastReciever()
                                    //set google api client for hint request
                                    //  mSmsBroadcastReceiver.setOnOtpListeners(baseContext)
                                    val intentFilter = IntentFilter()
                                    intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                                    LocalBroadcastManager.getInstance(this)
                                        .registerReceiver(mSmsBroadcastReceiver, intentFilter)*/

                            }
                        }
                    }
                }

            })
        )


        loginViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

    }

}