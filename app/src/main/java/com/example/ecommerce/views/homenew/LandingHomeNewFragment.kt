package com.example.ecommerce.views.homenew

import android.content.Intent
import android.os.Handler
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.adapters.homenew.AutoScrollPagerAdapter
import com.example.ecommerce.adapters.product.FlashSaleProductsAdapter
import com.example.ecommerce.adapters.product.ProductCategoriesAdapter
import com.example.ecommerce.databinding.FragmentHomeNewLandingBinding
import com.example.ecommerce.utils.BaseFragment
import com.example.ecommerce.views.category.ProductCategoriesActivity
import com.example.ecommerce.adapters.product.ProductsGridListAdapter
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.common.UtilsFunctions.showToastError
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.viewmodels.homenew.HomeVM
import com.example.ecommerce.views.cart.CartListActivity
import java.util.*
import kotlin.collections.ArrayList

class LandingHomeNewFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeNewLandingBinding
    private lateinit var homeVM: HomeVM
    private lateinit var context: FragmentActivity
    private val AUTO_SCROLL_THRESHOLD_IN_MILLI: Long = 3000

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home_new_landing
    }

    override fun initView() {
        binding = viewDataBinding as FragmentHomeNewLandingBinding
        homeVM = ViewModelProvider(this).get(HomeVM::class.java)

        context = activity!!

//        initProductsGrid()
//        initFlashSaleAdapter()
//        initProductCategoriesAdapter()
        initHomeResponseObserver()

//        setupDashboardBanner()
        clickListeners()
        baseActivity.startProgressDialog()
    }

    private fun initHomeResponseObserver() {
        homeVM.getHomeResponse().observe(this,
            Observer<HomeResponse> { response ->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            val currency = response.body?.currency ?: ""
                            SharedPrefClass().putObject(baseActivity,GlobalConstants.CurrencyPreference,currency)
                            if (response.body?.recommended != null) {
                                initProductsGrid(response.body.recommended, currency)
                            }
                            if (response.body?.categories != null) {
                                initProductCategoriesAdapter(response.body.categories)
                            }
                            if (response.body?.sales != null) {
                                initFlashSaleAdapter(response.body.sales, currency)

                                setupDashboardBanner(response.body.sales)
                            }
                        }
                        else -> message?.let {
                            showToastError(it)
                        }
                    }
                }
            })
    }

    private fun initProductsGrid(recommendedList: ArrayList<HomeResponse.Recommended>, currency: String) {
        if (!recommendedList.isEmpty()) {
            val adapter = ProductsGridListAdapter(
                context,
                recommendedList,
                activity!!,
                currency
            )
            binding.gvProducts.adapter = adapter
        }
    }

    private fun initFlashSaleAdapter(flashSaleList: ArrayList<HomeResponse.Sale>, currency: String) {
        if (!flashSaleList.isEmpty()) {
            binding.rvFlashSale.adapter =
                FlashSaleProductsAdapter(
                    context,
                    flashSaleList,
                    currency
                )

            binding.rvFlashSale.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    private fun initProductCategoriesAdapter(categoriesList: ArrayList<HomeResponse.Category>) {
        if (!categoriesList.isEmpty()) {
            binding.rvCategory.adapter =
                ProductCategoriesAdapter(
                    context,
                    categoriesList
                )

            binding.rvCategory.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }


    /**
     *  ViewPager advertisement
     */
    private fun setupDashboardBanner(salesBanners: ArrayList<HomeResponse.Sale>) {
//        val banners = servicesResponse.body!!.banners!!

        var bannerUrls = ArrayList<String>()
        for (item in salesBanners) {
            bannerUrls.add(item.thumbnail ?: "")
        }

//        bannerUrls.add("https://www.circleone.in/images/products_gallery_images/PVC-Banner.jpg")
//        bannerUrls.add("https://image.freepik.com/free-psd/online-shopping-with-discount_23-2148536749.jpg")

        val autoScrollPagerAdapter = AutoScrollPagerAdapter(fragmentManager, bannerUrls)
        binding.vpBanner.adapter = autoScrollPagerAdapter
        binding.tabs.setupWithViewPager(binding.vpBanner)
        // start auto scroll
        binding.vpBanner.startAutoScroll()
        // set auto scroll time in mili
        binding.vpBanner.interval = AUTO_SCROLL_THRESHOLD_IN_MILLI
        // enable recycling using true
        binding.vpBanner.setCycle(true)

        timeViewPagerManually(bannerUrls.size)
    }

    fun timeViewPagerManually(totalPages: Int) {

//        var currentPage = 0
        var currentPage = binding.vpBanner.currentItem

        val handler = Handler()
        val update = Runnable {
            if (currentPage == totalPages) {
                currentPage = 0
            }
            binding.vpBanner?.let { it.setCurrentItem(currentPage++, true) }
        }


        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 100, AUTO_SCROLL_THRESHOLD_IN_MILLI)
    }

    private fun clickListeners() {
        binding.tvMoreCategory.setOnClickListener {
            startActivity(Intent(context, ProductCategoriesActivity::class.java))
        }
        binding.tvSeeMore.setOnClickListener {
            startActivity(Intent(context, FlashSaleActivity::class.java))
        }
        binding.cartIcon.setOnClickListener{
            startActivity(Intent(context, CartListActivity::class.java))
        }
    }

    private fun clearAllFilters() {

    }
}