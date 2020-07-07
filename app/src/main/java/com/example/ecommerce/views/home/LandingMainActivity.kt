package com.example.ecommerce.views.home

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityLandingBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.utils.DialogssInterface
import com.example.ecommerce.views.address.AddressListActivity
import com.example.ecommerce.views.authentication.LoginActivity
import com.example.ecommerce.views.cart.CartListActivity
import com.example.ecommerce.views.favorite.FavoriteListActivity
import com.example.ecommerce.views.homenew.LandingHomeNewFragment
import com.example.ecommerce.views.homenew.SearchProductsActivity
import com.example.ecommerce.views.notifications.NotificationsListActivity
import com.example.ecommerce.views.orders.OrdersHistoryListActivity
import com.example.ecommerce.views.orders.OrdersListActivity
import com.example.ecommerce.views.product.FavouriteProductsActivity
import com.example.ecommerce.views.profile.ProfileActivity
import com.example.ecommerce.views.ratingreviews.AddRatingReviewsListActivity
import com.example.ecommerce.views.settings.MyAccountsActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class LandingMainActivity : BaseActivity(),
    DialogssInterface {
    var activityLandingBinding: ActivityLandingBinding? = null
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null
    private var confirmationDialog: Dialog? = null
    private var ratingDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    private var dashboardViewModel: DashboardViewModel? = null
    private var removedFrag: String = ""
    var fragment: Fragment? = null
    var isCart = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_landing
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initViews() {
        activityLandingBinding = viewDataBinding as ActivityLandingBinding
        navigationView = activityLandingBinding!!.navView
        navigationView!!.alpha = 0.9f
        drawer = activityLandingBinding!!.drawerLayout
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        activityLandingBinding!!.dashboardViewModel = dashboardViewModel
        // toolBarText = activityDashboardBinding!!.toolbarCommon.imgToolbarText
        // toolBarImage = activityDashboardBinding!!.toolbarCommon.imgRight
        /** Show Rating Dialog here**/
        // checkForRating(0)
        /*****************/
        activityLandingBinding!!.toolbarHome.toolbar.setImageResource(R.drawable.drawer)
        activityLandingBinding!!.toolbarHome.etSearchProducts.setOnClickListener { onSearchClick() }
        activityLandingBinding!!.toolbarHome.ivFavourite.setOnClickListener { onFavouritesClick() }
        activityLandingBinding!!.toolbarHome.ivAlert.setOnClickListener { onNotificationsClick() }

//        activityLandingBinding!!.toolbarCommon.imgRight.visibility = View.GONE
//        activityLandingBinding!!.toolbarCommon.imgToolbarText.setText(resources.getString(R.string.home))
//        activityLandingBinding!!.toolbarCommon.imgRight.setImageResource(R.drawable.ic_cart)
//        activityDashboardBinding!!.toolbarCommon.rlTop.setBackgroundColor(resources.getColor(R.color.orange_transparent))

        val from = intent.extras?.get("from").toString()
        if (!from.equals("splash")) {
            ratingDialog = mDialogClass.setDefaultDialog(
                this,
                this,
                "Rating",
                getString(R.string.warning_rate_order)
            )
            ratingDialog?.show()
        } else {

        }
        val image = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            GlobalConstants.USER_IAMGE
        )
        // ic_profile
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.user)
            .into(activityLandingBinding!!.icProfile)
        val name = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            getString(R.string.first_name)
        )
        if (TextUtils.isEmpty(name.toString()) || name.toString().equals("null")) {
            activityLandingBinding!!.tvName.text = getString(R.string.create_profile)
        } else {
            activityLandingBinding!!.tvName.text = name.toString()
        }

//        fragment = LandingHomeFragment()
        fragment = LandingHomeNewFragment()
        callFragments(fragment, supportFragmentManager, false, "send_data", "")
        dashboardViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    "tv_nav_fav" -> {
                        val intent = Intent(this, FavouriteProductsActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_notification" -> {
                           val intent = Intent(this, NotificationsListActivity::class.java)
                           startActivity(intent)
                    }
                    "tv_nav_address" -> {
                        val intent = Intent(this, AddressListActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_cart" -> {
                        val intent = Intent(this, CartListActivity::class.java)
                        startActivity(intent)
                    }
                    "img_right" -> {
                        val intent = Intent(this, CartListActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_home" -> {
//                        activityLandingBinding!!.toolbarCommon.imgRight.visibility = View.VISIBLE
//                        activityLandingBinding!!.toolbarCommon.imgRight.setImageDrawable(
//                            getDrawable(R.drawable.ic_notifications)
//                        )

//                        val fragment = LandingHomeFragment()
                        val fragment =
                            LandingHomeNewFragment()
                        // activityDashboardBinding!!.toolbarCommon.imgToolbarText.setText("")
                        /*   activityDashboardBinding!!.toolbarCommon.imgToolbarText.text =
                               getString(R.string.home)*/
                        activityLandingBinding!!.drawerLayout.closeDrawers()
                        this.callFragments(
                            fragment,
                            supportFragmentManager,
                            false,
                            "send_data",
                            ""
                        )
                        activityLandingBinding!!.tablayout.getTabAt(0)?.select()
                        activityLandingBinding!!.drawerLayout.closeDrawers()

                    }
                    "tv_nav_order" -> {
                        val intent = Intent(this, OrdersListActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_order_history" -> {
                        val intent = Intent(this, OrdersHistoryListActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_terms" -> {
                        /* val intent = Intent(this, OrdersHistoryListActivity::class.java)
                         startActivity(intent)*/
                    }

                    "ic_profile" -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_account" -> {
                        val intent = Intent(this, MyAccountsActivity::class.java)
                        startActivity(intent)
                    }
                    "img_nav_cancel" -> {
                        activityLandingBinding!!.drawerLayout.closeDrawers()
                    }
                    "tv_nav_logout" -> {
                        confirmationDialog = mDialogClass.setDefaultDialog(
                            this,
                            this,
                            "logout",
                            "Do you really want to logout?"
                        )
                        confirmationDialog?.show()

                    }
                    "toolbar" -> {
                        val image = SharedPrefClass().getPrefValue(
                            MyApplication.instance.applicationContext,
                            GlobalConstants.USER_IAMGE
                        )
                        // ic_profile
                        Glide.with(this)
                            .load(image)
                            .placeholder(R.drawable.user)
                            .into(activityLandingBinding!!.icProfile)

                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        } else {
                            drawer!!.openDrawer(Gravity.LEFT)
                        }
                        val fragmentType =
                            supportFragmentManager.findFragmentById(R.id.frame_layout)
                        /*when (fragmentType) {
                            is HomeFragment -> {
                                activityDashboardBinding!!.toolbarCommon.imgRight.visibility =
                                    View.GONE
                            }
                        }*/
                    }
                }
            })
        )

        dashboardViewModel!!.getLogoutReposne.observe(this,
            Observer<CommonModel> { logoutResponse ->
                this.stopProgressDialog()

                if (logoutResponse != null) {
                    val message = logoutResponse.message

                    if (logoutResponse.code == 200) {
                        SharedPrefClass().putObject(
                            this,
                            "isLogin",
                            false
                        )
                        SharedPrefClass().putObject(
                            this,
                            GlobalConstants.SelectedAddressType,
                            "null"
                        )
                        SharedPrefClass().putObject(
                            this,
                            GlobalConstants.USER_IAMGE,
                            "null"
                        )
                        showToastSuccess(getString(R.string.logout_msg))
                        val intent1 = Intent(this, LoginActivity::class.java)
                        startActivity(intent1)
                        finish()

                    } else {
                        showToastError(message)
                    }
                }
            })

        dashboardViewModel!!.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                this.startProgressDialog()
            } else {
                this.stopProgressDialog()
            }
        })

        activityLandingBinding!!.tablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var fragment: Fragment? = null
                //   activityDashboardBinding!!.toolbarCommon.imgRight.visibility = View.GONE
                when (tab!!.position) {
                    /* 0 -> fragment = HomeFragment()
                     1 -> fragment = JobRequestsFragment()*/
                }
                callFragments(fragment, supportFragmentManager, false, "send_data", "")

                Handler().postDelayed({
                    setHeadings()
                }, 300)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //var fragment : Fragment? = null
                //Not In use
            }
        })

    }

    /**
     *  Toolbar click listeners
     */
    private fun onSearchClick() {
        startActivity(Intent(this, SearchProductsActivity::class.java))
    }
    private fun onFavouritesClick() {
        startActivity(Intent(this, FavouriteProductsActivity::class.java))
    }
    private fun onNotificationsClick() {

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setHeadings() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (fragment) {
            is HomeFragment -> {
                /* activityDashboardBinding!!.toolbarCommon.imgToolbarText.text =
                     getString(R.string.home)
                 getString(R.string.calendar)*/

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onResumedForFragment() {
        onResume()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setHeadings()
        /*  if (GlobalConstants.selectedCheckedFragment == 100) {
              activityDashboardBinding!!.tablayout.getTabAt(GlobalConstants.selectedFragment)!!.select()
              GlobalConstants.selectedCheckedFragment = 0
          }*/
        activityLandingBinding!!.topBannerLayout.setBackgroundColor(
            (Color.parseColor(
                GlobalConstants.COLOR_CODE
            ))
        )
        activityLandingBinding!!.topBannerLayout.setBackgroundTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    GlobalConstants.COLOR_CODE
                )
            )/*mContext.getResources().getColorStateList(R.color.colorOrange)*/
        )
        isCart = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.isCartAdded
        ).toString()
//        if (isCart.equals("true")) {
//            activityLandingBinding!!.toolbarCommon.imgRight.visibility = View.VISIBLE
//        } else {
//            activityLandingBinding!!.toolbarCommon.imgRight.visibility = View.GONE
//        }

        val image = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            GlobalConstants.USER_IAMGE
        )
        // ic_profile
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.user)
            .into(activityLandingBinding!!.icProfile)

        val name = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            getString(R.string.first_name)
        )
        if (TextUtils.isEmpty(name.toString().trim()) || name.toString().trim().equals("null")) {
            activityLandingBinding!!.tvName.text = getString(R.string.create_profile)
        } else {
            activityLandingBinding!!.tvName.text = name.toString()
        }

    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            "logout" -> {
                confirmationDialog?.dismiss()
                dashboardViewModel!!.callLogoutApi()
                // dashboardViewModel!!.callLogoutApi()

            }
            "Rating" -> {
                ratingDialog?.dismiss()
                // confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    val intent = Intent(this, AddRatingReviewsListActivity::class.java)
                    intent.putExtra("orderId", "6ad3c62c-17dd-4bb1-97c5-e757cd2f4ff3")
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            "logout" -> confirmationDialog?.dismiss()
            "Rating" -> ratingDialog?.dismiss()
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }


}