package com.example.ecommerce.views

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivitySplashBinding
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.socket.TrackingActivity
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.views.authentication.LoginActivity
import com.example.ecommerce.views.home.LandingMainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import java.util.*

class SplashActivity : BaseActivity() {
    private var mActivitySplashBinding: ActivitySplashBinding? = null
    private var sharedPrefClass: SharedPrefClass? = null
    private var mContext: Context? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViews() {
        mContext = this
        mActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        sharedPrefClass = SharedPrefClass()
        val token: String? = "sd"

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                SharedPrefClass().putObject(
                    MyApplication.instance,
                    GlobalConstants.NOTIFICATION_TOKEN,
                    token
                )

            })
        /* if (token != null) {
             sharedPrefClass!!.putObject(
                 applicationContext,
                 GlobalConstants.NOTIFICATION_TOKEN,
                 token
             )
         }*/

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkScreenType()
                }
            }
        }, 3000)
    }

    private fun checkScreenType() {
        var login = ""
        if (checkObjectNull(
                SharedPrefClass().getPrefValue(
                    MyApplication.instance,
                    "isLogin"
                )
            )
        )
            login = sharedPrefClass!!.getPrefValue(this, "isLogin").toString()
        var jobId = sharedPrefClass!!.getPrefValue(this, GlobalConstants.JOBID).toString()
        val intent = if (login == "true") {
            if (!jobId.equals("null") && !jobId.equals("0")) {
                var mJsonObjectStartJob = JsonObject()
                var destLat =
                    sharedPrefClass!!.getPrefValue(this, GlobalConstants.DEST_LAT).toString()
                var destLong =
                    sharedPrefClass!!.getPrefValue(this, GlobalConstants.DEST_LONG).toString()
                mJsonObjectStartJob.addProperty(
                    "jobId", jobId
                )
                mJsonObjectStartJob.addProperty(
                    "dest_lat", destLat
                )
                mJsonObjectStartJob.addProperty(
                    "dest_long", destLong
                )
                mJsonObjectStartJob.addProperty(
                    "trackOrStart", "Track"
                )
                val intent = Intent(this, TrackingActivity::class.java)
                intent.putExtra("data", mJsonObjectStartJob.toString())
                //startActivity(intent)
            } else {
                val intent = Intent(this, LandingMainActivity::class.java)
                intent.putExtra("from", "splash")

                // Intent(this, PaymentActivity::class.java)

                // val intent = Intent(this, AddRatingReviewsListActivity::class.java)
                // intent.putExtra("orderId", "6ad3c62c-17dd-4bb1-97c5-e757cd2f4ff3")
                // intent.putExtra("orderId", "34e4e73b-0b1c-4aba-ad86-7b7cf2f769a5")

//34e4e73b-0b1c-4aba-ad86-7b7cf2f769a5
                // startActivity(intent)
                //startActivityForResult(intent1, 200)
            }

        } else {
            Intent(this, LoginActivity::class.java)
            //Intent(this, DashboardActivity::class.java)

        }

        startActivity(intent)
        finish()
    }

}
