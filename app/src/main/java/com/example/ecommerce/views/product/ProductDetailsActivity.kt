package com.example.ecommerce.views.product

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.adapters.homenew.AutoScrollPagerAdapter
import com.example.ecommerce.adapters.product.FlashSaleProductsAdapter
import com.example.ecommerce.adapters.product.ProductColorAdapter
import com.example.ecommerce.adapters.product.ProductSizeAdapter
import com.example.ecommerce.adapters.product.RecommendedProductsAdapter
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityProductDetailBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.model.product.BottomDialogFragment
import com.example.ecommerce.model.product.ProductColor
import com.example.ecommerce.model.product.ProductSize
import com.example.ecommerce.model.productdetail.ProductDetailResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.ExtrasConstants
import com.example.ecommerce.viewmodels.product.FavouriteProductVM
import com.example.ecommerce.viewmodels.product.ProductDetailsVM
import com.example.ecommerce.views.cart.CartListActivity
import com.example.ecommerce.views.home.LandingMainActivity
import com.example.ecommerce.views.ratingreviews.ReviewsListActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.ProductBannerAdapter
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailsActivity : BaseActivity(), BottomDialogFragment.BottomSheetCloseListener,
    ProductChangeListener {


    private lateinit var binding: ActivityProductDetailBinding
    private var searchList = ArrayList<ProductDetailResponse.Body>()
    private lateinit var productDetailsVM: ProductDetailsVM
    private lateinit var favouritesVM: FavouriteProductVM
    private var isFavoutite: Boolean = false
    private lateinit var serviceId: String
    private lateinit var addressId: String
    private var favProductId: String = ""
    private var mColorProductId: String = ""
    private val AUTO_SCROLL_THRESHOLD_IN_MILLI: Long = 3000
    private var dtlResponse: ProductDetailResponse.Body? = null
    private var sizeAdaper: ProductSizeAdapter? = null
    private var bannerAdapter: ProductBannerAdapter? = null
    private var pagerAdapter: AutoScrollPagerAdapter? = null
    var quantityCount = 0
    var price = 0
    var priceAmount = 0
    var estimateDelivery = ""
    var serviceCharges = ""
    var selectedColor = ""
    var selectedSize = ""
    var companyId = ""

//    private val SERVICE_ID = "05619abf-589d-4f4c-98c6-3d3a07d77ba4"

    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityProductDetailBinding
        productDetailsVM = ViewModelProvider(this).get(ProductDetailsVM::class.java)
        favouritesVM = ViewModelProvider(this).get(FavouriteProductVM::class.java)
        binding.commonToolBar.imgRight.setImageResource(R.drawable.ic_cart)
        binding.commonToolBar.imgRight.visibility = View.VISIBLE
        getExtras()
        startProgressDialog()
        productDetailsVM.initProductDetails(serviceId, addressId)
//        initProductSizeAdapter()
//        initProductColorAdapter()
//        initSuggestedProductsAdapter()

//        setProductDetails()

        initProductDetailsObserver()
        addRemoveFavouriteObserver()
        addRemoveCartObserver()
        setAddress()
        addProductToCart()
        initToolbar()
        clickObserver()
        /*binding.commonToolBar.imgRight.setOnClickListener() {
            val intent = Intent(this, CartListActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun getExtras() {
        serviceId = intent.getStringExtra(ExtrasConstants.SERVICE_ID)

        val defaultDeliveryAddress = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.PREF_DELIVERY_ADDRESS_ID
        )
        var address = defaultDeliveryAddress?.toString() ?: ""
        addressId = address.replace("\"", "");
        //now result is "resultCode"
    }

    private fun initToolbar() {
        // -------- Temporary ---
        // Product name static
//        binding.commonToolBar.imgToolbarText.text =
//            resources.getString(R.string.product_name)

//        binding.commonToolBar.imgToolbarText.text = dtlResponse?.name ?: ""

        // Show three dots at end of textview
        binding.commonToolBar.imgToolbarText.ellipsize = TextUtils.TruncateAt.END
        binding.commonToolBar.imgToolbarText.maxEms = 12
        binding.commonToolBar.imgToolbarText.setSingleLine(true)

        val headingView = binding.commonToolBar.imgToolbarText
        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun initProductSizeAdapter(sizeList: ArrayList<ProductDetailResponse.StockQunatity>) {
//        val sizeList = ArrayList<ProductSize>()
//        sizeList.add(ProductSize(true))
//        sizeList.add(ProductSize())
//        sizeList.add(ProductSize())
//        sizeList.add(ProductSize())

        val sizeSelectionList = ArrayList<ProductSize>()
        for (item in sizeList) {
            sizeSelectionList.add(ProductSize())
        }


        sizeAdaper = ProductSizeAdapter(this, sizeSelectionList, sizeList)
        binding.rvSize.adapter = sizeAdaper

        binding.rvSize.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as ProductSizeAdapter).notifyDataSetChanged()
    }

    private fun initProductColorAdapter(colorList: ArrayList<ProductDetailResponse.ProductSpecification>) {
        val colorSelectionList = ArrayList<ProductColor>()
        for (item in colorList) {
            colorSelectionList.add(ProductColor())
        }
//        colorSelectionList.add(ProductColor(true))
//        colorSelectionList.add(ProductColor())
//        colorSelectionList.add(ProductColor())
//        colorSelectionList.add(ProductColor())

        binding.rvColor.adapter = ProductColorAdapter(this, colorSelectionList, colorList)

        binding.rvColor.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as FlashSaleProductsAdapter).notifyDataSetChanged()
    }

    private fun initSuggestedProductsAdapter(
        recommendedList: ArrayList<ProductDetailResponse.Recommended>,
        currency: String
    ) {
        binding.rvSuggestedProducts.adapter =
            RecommendedProductsAdapter(this, recommendedList, currency)

        binding.rvSuggestedProducts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as FlashSaleProductsAdapter).notifyDataSetChanged()
    }

    private fun clickObserver() {
        productDetailsVM.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    "img_right" -> {
                        val intent = Intent(this, CartListActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
        )

    }

    private fun initProductDetailsObserver() {
        productDetailsVM.getProductDetails().observe(this,
            Observer<ProductDetailResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (response.body != null) {
                                setProductDetails(response.body)
                            }

//                            if (response.body?.services != null) {
//                                initFlashProductsGrid(response.body.services)
//                            }
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }

    private fun addRemoveFavouriteObserver() {
        favouritesVM.addRemovefavRes().observe(this,
            Observer<AddRemoveFavResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (!isFavoutite) {
                                binding.ivFavourite.setImageResource(R.drawable.favoutite_red)
                                isFavoutite = true

                                if (response.body != null) {
                                    favProductId = response.body.id ?: ""
                                }
                            } else {
                                binding.ivFavourite.setImageResource(R.drawable.favourite)
                                isFavoutite = false
                            }

//                            if (isfav.equals("false")) {
//                                isfav = "true"
//                                serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_favorite)
//                            } else {
//                                isfav = "false"
//                                serviceDetailBinding.imgAddFavorite.setImageResource(R.drawable.ic_unfavorite)
//                            }
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }

    private fun addRemoveCartObserver() {
        favouritesVM.addRemoveCartRes().observe(this,
            Observer<CommonModel> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            showToastSuccess("product added to cart successfully")
                            startActivity(Intent(this, CartListActivity::class.java))
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }


    private fun addProductToCart() {


        binding.addProductToCart.setOnClickListener() {
            showCartInfoLayout()
        }

        binding.imgCross.setOnClickListener() {

            binding.addProductToCart.isEnabled = true
            binding.llSlots.visibility = View.GONE
        }

        binding.imgPlus.setOnClickListener() {
            if (quantityCount <= 5) {
                quantityCount++
                binding.tvQuantity.setText(quantityCount.toString())
                price = quantityCount * priceAmount.toInt()
                binding.tvTotalPrice.setText(GlobalConstants.Currency + " " + price.toString())
            }
        }
        binding.imgMinus.setOnClickListener() {

            if (quantityCount > 0) {
                quantityCount--
                price = quantityCount * priceAmount.toInt()
                binding.tvTotalPrice.setText(GlobalConstants.Currency + " " + price.toString())
                //callGetTimeSlotsApi()
            }
            if (quantityCount == 0) {
                binding.tvTotalPrice.setText("0")

            }
            binding.tvQuantity.setText(quantityCount.toString())
        }

        binding.btnSubmit.setOnClickListener() {
            if (quantityCount == 0) {
                showToastError(getString(R.string.select_quantity_msg))
            } else {
                callAddRemoveCartApi()
            }
        }

        binding.tvSeeMore.setOnClickListener{
            val intent = Intent(this, ReviewsListActivity::class.java)
            intent.putExtra("serviceId", serviceId)
            startActivity(intent)

        }    }

    private fun callAddRemoveCartApi() {
        var cartObject = JsonObject()
        cartObject.addProperty(
            "serviceId", serviceId
        )
        cartObject.addProperty(
            "addressId", addressId
        )
        cartObject.addProperty(
            "orderPrice", priceAmount
        )
        cartObject.addProperty(
            "orderTotalPrice", price
        )
        cartObject.addProperty(
            "quantity", quantityCount
        )
        cartObject.addProperty(
            "serviceCharges", serviceCharges
        )
        cartObject.addProperty(
            "estimateDelivery", estimateDelivery
        )
        cartObject.addProperty(
            "color", selectedColor
        )
        cartObject.addProperty(
            "size", selectedSize
        )
        cartObject.addProperty(
            "companyId", companyId
        )

        if (UtilsFunctions.isNetworkConnected()) {
            favouritesVM.addCart(cartObject)
            startProgressDialog()
        }
    }

    private fun showCartInfoLayout() {
        binding.tvTotalPrice.setText("0")
        binding.llSlots.visibility = View.VISIBLE
        //serviceDetailBinding.btnSubmit.visibility = View.GONE
        var animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        animation.setDuration(500)
        binding.llSlots.setAnimation(animation)
        binding.llSlots.animate()
        animation.start()
        quantityCount = 0
        binding.tvQuantity.setText(quantityCount.toString())
        binding.addProductToCart.isEnabled = false

    }

    private fun setAddress() {
        binding.llSelectAddress.setOnClickListener {
            val bottomDialogFragment: BottomDialogFragment =
                BottomDialogFragment.newInstance()
            bottomDialogFragment.show(
                supportFragmentManager,
                "bottom_dialog_frag"
            )
        }

        setDeliveryAddress()
    }

    override fun onBottomSheetClose(dialog: DialogInterface) {
        setDeliveryAddress()
    }


    /**
     *  Set product banners
     */
    private fun setupDashboardBanner(bannerUrls: ArrayList<String>) {
//        val banners = servicesResponse.body!!.banners!!

//        var bannerUrls = ArrayList<String>()
//        bannerUrls.add("https://www.circleone.in/images/products_gallery_images/PVC-Banner.jpg")
//        bannerUrls.add("https://image.freepik.com/free-psd/online-shopping-with-discount_23-2148536749.jpg")

        pagerAdapter = AutoScrollPagerAdapter(supportFragmentManager, bannerUrls)
        binding.vpBanner.adapter = pagerAdapter
        binding.tabs.setupWithViewPager(binding.vpBanner)
        // start auto scroll
        binding.vpBanner.startAutoScroll()
        // set auto scroll time in mili
        binding.vpBanner.interval = AUTO_SCROLL_THRESHOLD_IN_MILLI
        // enable recycling using true
        binding.vpBanner.setCycle(true)

//        timeViewPagerManually(bannerUrls.size)
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


    private fun setDeliveryAddress() {
        val defaultDeliveryAddress = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.PREF_DELIVERY_ADDRESS
        )

        if (defaultDeliveryAddress != null) {
            binding.tvDeliveryAddress.text = defaultDeliveryAddress.toString()
        } else {
            binding.tvDeliveryAddress.text = "Select Delivery Address"
        }

        // Show three dots at end of textview
        binding.tvDeliveryAddress.ellipsize = TextUtils.TruncateAt.END
        binding.tvDeliveryAddress.maxEms = 15
        binding.tvDeliveryAddress.setSingleLine(true)
    }

    private fun setProductDetails(response: ProductDetailResponse.Body) {
        dtlResponse = response

        // toolbar title
        binding.commonToolBar.imgToolbarText.text = response.name ?: ""
        binding.webViewDescription.loadData(
            response.company!!.document!!.aboutus,
            "text/html; charset=utf-8",
            "UTF-8"
        )

        binding.tvProductName.text = response.name ?: ""
        binding.tvProductDetail.text = response.description ?: ""
        binding.tvSellerName.text = response.company.companyName
        Glide.with(this)
            .load(response.company.logo1)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.no_image)
            .into(binding.ivSellerPic)
        val price = "${response.currency}${response.price}"
        binding.tvProductPrice.text = price
        priceAmount = response.price!!.toInt()
        if (estimateDelivery != null && !estimateDelivery.equals("")) {
            estimateDelivery = response.estimatDelivery!!
        } else {
            //TODO for testing remove after api bug fix
            estimateDelivery = "12"
        }
        serviceCharges = response.shipment!!
        companyId = response.companyId!!
        // Product isFavourite or not
        if (response.favourite != null) {
            if (!response.favourite.isEmpty()) {
                binding.ivFavourite.setImageResource(R.drawable.favoutite_red)
                isFavoutite = true
            }
        }

        if (!response.price.toString().equals(response.originalPrice)) {
            val percentOff = "${response.offer}% off"
            binding.tvProductPercentageOff.text = percentOff

            val offPrice = "${response.currency}${response.originalPrice}"
            binding.tvProductOff.text = offPrice
            binding.tvProductOff.setPaintFlags(binding.tvProductOff.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            binding.tvProductPercentageOff.visibility = View.VISIBLE
            binding.tvProductOff.visibility = View.VISIBLE
        } else {
            binding.tvProductPercentageOff.visibility = View.INVISIBLE
            binding.tvProductOff.visibility = View.INVISIBLE
        }

//        binding.rbProductRating.rating = 4f
//        binding.rbOverallRating.rating = 4f
//        binding.custRating.rating = 4f

       /* binding.tvSeeMore.setOnClickListener {
            startActivity(Intent(this, CustomerAllReviewsActivity::class.java))
        }*/

        binding.ivFavourite.setOnClickListener {
            val companyId = SharedPrefClass().getPrefValue(
                MyApplication.instance,
                GlobalConstants.COMPANY_ID
            ).toString()

            var favObject = JsonObject()
            favObject.addProperty(
                "serviceId", serviceId
            )
            favObject.addProperty(
                "companyId", ExtrasConstants.COMPANY_ID
            )
            if (!isFavoutite) {
/*                binding.ivFavourite.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    ),
//                    android.graphics.PorterDuff.Mode.SRC_IN
                    android.graphics.PorterDuff.Mode.SRC_IN
                )*/

                favouritesVM.addFav(favObject)
                startProgressDialog()
            } else {
                favouritesVM.removeFav(favProductId)
                startProgressDialog()
            }
        }

        // Product images
//        Glide.with(this)
//            .load(response.icon)
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
//            .placeholder(R.drawable.no_image)
//            .into(binding.vpBanner)

        // Product detail and specifications
        binding.tvProductDetail.text = response.description
        binding.tvTotalReviews.text = "(${response.ratingCount} Reviews)"

        if (response.productSpecifications != null && response.productSpecifications.size > 0) {
            initProductColorAdapter(response.productSpecifications)
            val sizeStock = response.productSpecifications[0].stockQunatity!!
            initProductSizeAdapter(sizeStock)
            val banners = response.productSpecifications[0].productImages!!
            setViewpager(banners)
            selectedColor = response.productSpecifications[0].productColor!!
            selectedSize = response.productSpecifications[0].stockQunatity!![0].size!!
//            setupDashboardBanner(response.productSpecifications[0].productImages!!)
        } else {
            // Hide size, color, customer review and title and set Imageadd

            binding.ivProductImage.visibility = View.VISIBLE
            binding.offersViewpager.visibility = View.GONE
            binding.tvTitleSelectSize.visibility = View.GONE
            binding.tvTitleSelectColor.visibility = View.GONE
            binding.rlProductRating.visibility = View.GONE
            binding.llOverAllRating.visibility = View.GONE
            binding.rlReviewTitle.visibility = View.GONE

            Glide.with(this)
                .load(response.icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(R.drawable.no_image)
                .into(binding.ivProductImage)
        }

        initSuggestedProductsAdapter(response.recommended!!, response.currency ?: "")

        binding.tvVendorName.text = "by ${response.company?.companyName}"

        // Ratings
        binding.rbOverallRating.rating = response.rating?.toFloat() ?: 0f
        binding.rbProductRating.rating = response.rating?.toFloat() ?: 0f
        binding.tvAvgRatings.text = response.rating?.toString()

        // Customer Review
        if (response.ratings != null) {
            binding.rlProductRating.visibility = View.VISIBLE
            val custName = "${response.ratings.user?.firstName} ${response.ratings.user?.lastName}"
            binding.tvProfileName.text = custName
            var rating = String.format("%.2f", response.ratings.rating?.toFloat())
            binding.custRating.rating = rating.toFloat()
            binding.tvCustReview.text = response.ratings.review
            Glide.with(this)
                .load(response.ratings.user?.image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(R.drawable.no_image)
                .into(binding.ivCustomerPic)

            val reviewImages = response?.ratings.reviewImages
            if (reviewImages != null && reviewImages.size > 0) {
                Glide.with(this)
                    .load(reviewImages[0])
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .placeholder(R.drawable.no_image)
                    .into(binding.ivReviewedProductPic)

                binding.ivReviewedProductPic.visibility = View.VISIBLE
            } else {
                binding.ivReviewedProductPic.visibility = View.GONE
            }
            binding.tvCustReviewDate.text = "${response.ratings?.createdAt}"
//        binding.ivReviewedProductPic.text = "${response.ratings?.createdAt}"
        } else {
            binding.rlProductRating.visibility = View.GONE
        }
    }

    override fun onSizeChange(sizeId: Int) {
        startProgressDialog()

        val response = dtlResponse

        if (response != null) {
            for (spec in response!!.productSpecifications!!) {
                if (spec.id.equals(mColorProductId)) {

                    for (stock in spec!!.stockQunatity!!) {

                        if (stock.id!!.equals(sizeId)) {
                            val price = "${response.currency}${stock.originalPrice}"
                            binding.tvProductPrice.text = price
                            selectedSize = stock.size!!
                            priceAmount = stock.originalPrice!!.toInt()
                            if (!stock.price.equals(stock.originalPrice)) {
                                val percentOff = "${response.offer}% off"
                                binding.tvProductPercentageOff.text = percentOff

                                val offPrice = "${response.currency}${stock.price}"
                                binding.tvProductOff.text = offPrice
                                binding.tvProductOff.setPaintFlags(binding.tvProductOff.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                                binding.tvProductPercentageOff.visibility = View.VISIBLE
                                binding.tvProductOff.visibility = View.VISIBLE
                            } else {
                                binding.tvProductPercentageOff.visibility = View.INVISIBLE
                                binding.tvProductOff.visibility = View.INVISIBLE
                            }
                            break
                        }

                    }


                }

            }
        }
        stopProgressDialog()

    }

    override fun onColorChange(productId: String) {
        // show progress while changing product color
        startProgressDialog()
        mColorProductId = productId

        val response = dtlResponse

        if (response != null) {
            for (spec in response!!.productSpecifications!!) {
                if (spec.id.equals(productId)) {
                    if (sizeAdaper != null) {
                        selectedColor = spec.productColor!!
                        val adapter = sizeAdaper
                        adapter!!.allSizeList.clear()

//                        for (i in 0..spec.stockQunatity!!.size - 1) {
//                            // If no color with empty stock
//                            if (!spec.stockQunatity[i].stock.equals("0")) {
//
//                            }
//                        }

                        // Size change notify adapter
                        adapter.allSizeList.addAll(spec.stockQunatity!!)
                        adapter.selectFirstSize(0)
                        // adapter.notifyDataSetChanged()
//                        binding.vpBanner.setBackgroundColor(Color.parseColor(spec.productColor!!))
                    }
                    if (bannerAdapter != null) {
                        // Images change notify adapter
                        val adapter = bannerAdapter
                        adapter!!.offersList.clear()
                        adapter.offersList.addAll(spec.productImages!!)

                        adapter.notifyDataSetChanged()
                    }
/*                    if (pagerAdapter != null) {
                        // Images change notify adapter
                        val adapter = pagerAdapter
                        adapter!!.bannerUrls.clear()
                        adapter.bannerUrls.addAll(spec.productImages!!)

                        adapter.notifyDataSetChanged()
                    }*/
                    break
                }
            }
        }
        stopProgressDialog()
    }

    fun setViewpager(banners: ArrayList<String>) {
//        val v = ArrayList<String>()
//        v.add("http://51.79.40.224:9074/services/icons/blue1.jpg")
//        v.add("http://51.79.40.224:9074/services/icons/blue2.jpg")

        bannerAdapter = ProductBannerAdapter(this, banners, this)
        binding.offersViewpager.adapter = bannerAdapter
    }
}

interface ProductChangeListener {
    fun onColorChange(productId: String)
    fun onSizeChange(sizeId: Int)
}