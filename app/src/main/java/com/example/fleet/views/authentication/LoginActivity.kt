package com.example.fleet.views.authentication

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.FirebaseFunctions
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivityLoginBinding
import com.example.fleet.model.CommonModel
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.gson.JsonObject

class LoginActivity : BaseActivity() {
    private lateinit var activityLoginbinding : ActivityLoginBinding
    private lateinit var loginViewModel : LoginViewModel
    override fun getLayoutId() : Int {
        return R.layout.activity_login
    }

    private val mJsonObject = JsonObject()

    override fun initViews() {
        activityLoginbinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activityLoginbinding.loginViewModel = loginViewModel
        loginViewModel.checkEmailExistence().observe(this,
            Observer<CommonModel> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            val intent = Intent(this, LoginPasswordActivity::class.java)
                            intent.putExtra("data", mJsonObject.toString())
                            startActivity(intent)

                        }
                        response.code == 204 -> {
                            FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                        }
                        else -> showToastError(message)
                    }

                }
            })


        loginViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                val phone = activityLoginbinding.etPhoneNo.text.toString()
                when (it) {
                    "btn_login" -> {
                        when {
                            TextUtils.isEmpty(phone) -> run {
                                activityLoginbinding.etPhoneNo.requestFocus()
                                activityLoginbinding.etPhoneNo.error = getString(R.string.empty) + " " + getString(
                                    R.string.phone_number
                                )
                            }
                            else -> {
                                mJsonObject.addProperty("phone_number", phone)
                                mJsonObject.addProperty(
                                    "notify_id",
                                    SharedPrefClass().getPrefValue(
                                        MyApplication.instance,
                                        GlobalConstants.NOTIFICATION_TOKEN
                                    ) as String
                                )
                                val versionName = MyApplication.instance.packageManager
                                    .getPackageInfo(MyApplication.instance.packageName, 0).versionName
                                val androidId = UtilsFunctions.getAndroidID()

                                mJsonObject.addProperty("device_type", GlobalConstants.PLATFORM)
                                mJsonObject.addProperty(
                                    "country_code",
                                    "+" + activityLoginbinding.btnCcp.selectedCountryCode
                                )
                                mJsonObject.addProperty("device_id", androidId)
                                mJsonObject.addProperty("app-version", versionName)
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
                                val mSmsBroadcastReceiver = SMSBroadcastReciever()
                                //set google api client for hint request
                              //  mSmsBroadcastReceiver.setOnOtpListeners(baseContext)

                                val intentFilter = IntentFilter()
                                intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                                LocalBroadcastManager.getInstance(this).registerReceiver(mSmsBroadcastReceiver, intentFilter)



                            }
                        }
                    }
                }

            })
        )


        loginViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

    }

}