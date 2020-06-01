package com.example.ecommerce.views.authentication

import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ecommerce.R
import com.example.ecommerce.common.FirebaseFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityPasswordBinding
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.viewmodels.LoginPasswordVModel
import com.example.ecommerce.views.home.DashboardActivity
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject

class LoginPasswordActivity : BaseActivity() {
    private lateinit var binding : ActivityPasswordBinding
    private lateinit var loginPasswordVModel : LoginPasswordVModel
    override fun getLayoutId() : Int {
        return R.layout.activity_password
    }

    private var mJsonObject = JSONObject()

    override fun initViews() {
        binding = viewDataBinding as ActivityPasswordBinding
        loginPasswordVModel = ViewModelProviders.of(this).get(LoginPasswordVModel::class.java)
        binding.passViewModel = loginPasswordVModel
        try {
            mJsonObject = JSONObject(intent.extras?.get("data")?.toString())

        } catch (e : JSONException) {
            e.printStackTrace()
        }



        loginPasswordVModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                when (it) {
                    "btn_login" -> {
                        val password = binding.etPassword.text.toString()
                        when {
                            TextUtils.isEmpty(password) -> run {
                                binding.etPassword.requestFocus()
                                binding.etPassword.error = getString(R.string.empty) + " " + getString(
                                    R.string.password
                                )
                            }
                            else -> {
                                mJsonObject.put("password", password)
                                loginPasswordVModel.login(mJsonObject)
                            }
                        }
                    }
                    "tv_forgot" -> {
                        val mJsonObject1 = JsonObject()
                        mJsonObject1.addProperty("country_code", mJsonObject.get("country_code").toString())
                        mJsonObject1.addProperty(
                            getString(R.string.key_phone),
                            mJsonObject.get(getString(R.string.key_phone)).toString()
                        )

                        FirebaseFunctions.sendOTP("forgot", mJsonObject1, this)
                    }
                    "tv_signup" -> {
                        val mJsonObject1 = JsonObject()
                        mJsonObject1.addProperty("country_code", mJsonObject.get("country_code").toString())
                        mJsonObject1.addProperty(
                            getString(R.string.key_phone),
                            mJsonObject.get(getString(R.string.key_phone)).toString()
                        )


                        SharedPrefClass().putObject(
                            applicationContext,
                            getString(R.string.key_phone),
                            mJsonObject.get(getString(R.string.key_phone)).toString()
                        )
                        FirebaseFunctions.sendOTP("signup", mJsonObject1, this)
                    }
                }

            })
        )



        loginPasswordVModel.getLoginResponse().observe(this,
            Observer<LoginResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message!!

                    when {
                        response.code == 200 -> {
                            showToastSuccess(message)
                            saveSharedData(response)
                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                        }
                        else -> showToastError(message)
                    }

                }
            })

        loginPasswordVModel.isLoading().observe(this, Observer<Boolean> { aBoolean->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

    }

    private fun saveSharedData(loginResponse : LoginResponse) {
       /* SharedPrefClass().putObject(
            this,
            GlobalConstants.ACCESS_TOKEN,
            loginResponse.data!!.jwtToken
        )*/
        SharedPrefClass().putObject(
            this,
            GlobalConstants.USERID,
            loginResponse.data!!.id
        )

        SharedPrefClass().putObject(
            this, GlobalConstants.USERDATA,
            loginResponse.data!!
        )


        SharedPrefClass().putObject(
            this,
            "isLogin",
            true
        )

    }

}