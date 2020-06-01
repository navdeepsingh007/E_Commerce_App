package com.example.ecommerce.views.product

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.adapters.product.FlashSaleProductsAdapter
import com.example.ecommerce.adapters.product.ProductColorAdapter
import com.example.ecommerce.adapters.product.ProductSizeAdapter
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.ActivityProductDetailBinding
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.model.productdetail.ProductDetailResponse
import com.example.ecommerce.model.product.ProductColor
import com.example.ecommerce.model.product.ProductSize
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.ExtrasConstants
import com.example.ecommerce.viewmodels.product.FavouriteProductVM
import com.example.ecommerce.viewmodels.product.ProductDetailsVM
import com.example.ecommerce.viewmodels.services.ServicesViewModel
import com.google.gson.JsonObject

class ProductDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private var searchList = ArrayList<ProductDetailResponse.Body>()
    private lateinit var productDetailsVM: ProductDetailsVM
    private lateinit var favouritesVM: FavouriteProductVM
    private var isFavoutite: Boolean = false
    private lateinit var serviceId: String
    private var favProductId: String = ""

//    private val SERVICE_ID = "05619abf-589d-4f4c-98c6-3d3a07d77ba4"

    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityProductDetailBinding
        productDetailsVM = ViewModelProvider(this).get(ProductDetailsVM::class.java)
        favouritesVM = ViewModelProvider(this).get(FavouriteProductVM::class.java)

        initToolbar()
        initProductSizeAdapter()
        initProductColorAdapter()
        initSuggestedProductsAdapter()

//        setProductDetails()

        initProductDetailsObserver()
        addRemoveFavouriteObserver()
        getExtras()
    }

    private fun getExtras() {
        serviceId = intent.getStringExtra(ExtrasConstants.SERVICE_ID)
    }

    private fun initToolbar() {
        // -------- Temporary ---
        // Product name static
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.product_name)

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

    private fun initProductSizeAdapter() {
        val sizeList = ArrayList<ProductSize>()
        sizeList.add(ProductSize(true))
        sizeList.add(ProductSize())
        sizeList.add(ProductSize())
        sizeList.add(ProductSize())
        binding.rvSize.adapter = ProductSizeAdapter(this, sizeList)

        binding.rvSize.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as ProductSizeAdapter).notifyDataSetChanged()
    }

    private fun initProductColorAdapter() {
        val colorSelectionList = ArrayList<ProductColor>()
        colorSelectionList.add(ProductColor(true))
        colorSelectionList.add(ProductColor())
        colorSelectionList.add(ProductColor())
        colorSelectionList.add(ProductColor())

        binding.rvColor.adapter = ProductColorAdapter(this, colorSelectionList)

        binding.rvColor.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as FlashSaleProductsAdapter).notifyDataSetChanged()
    }

    private fun initSuggestedProductsAdapter() {
        binding.rvSuggestedProducts.adapter =
            FlashSaleProductsAdapter(this, ArrayList<HomeResponse.Sale>())

        binding.rvSuggestedProducts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
//        binding.rvFlashSale.smoothScrollToPosition(0)
//        (binding.rvFlashSale.adapter as FlashSaleProductsAdapter).notifyDataSetChanged()
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

    private fun setProductDetails(response: ProductDetailResponse.Body) {
        binding.tvProductName.text = response.name ?: ""
        binding.tvProductDetail.text = response.description ?: ""
        binding.tvProductPrice.text = response.price.toString()

        if (!response.price.toString().equals(response.originalPrice)) {
            val percentOff = "${response.offer}% off"
            binding.tvProductPercentageOff.text = percentOff
            binding.tvProductOff.text = response.originalPrice ?: ""
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

        binding.tvSeeMore.setOnClickListener {
            startActivity(Intent(this, CustomerAllReviewsActivity::class.java))
        }

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
    }

    private fun addRemoveFavouriteObserver() {
        favouritesVM.addRemovefavRes().observe(this,
            Observer<AddRemoveFavResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if(!isFavoutite) {
                                binding.ivFavourite.setImageResource(R.drawable.favoutite_red)
                                isFavoutite = false

                                if (response.body != null) {
                                    favProductId = response.body.id ?: ""
                                }
                            } else {
                                binding.ivFavourite.setImageResource(R.drawable.favourite)
                                isFavoutite = true
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
}