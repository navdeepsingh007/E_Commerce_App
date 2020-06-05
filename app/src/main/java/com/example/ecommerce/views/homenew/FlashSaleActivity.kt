package com.example.ecommerce.views.homenew

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.adapters.product.FilterProductsAdapter
import com.example.ecommerce.adapters.product.ProductsGridListAdapter
import com.example.ecommerce.adapters.product.ProductsListingGridAdapter
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.databinding.ActivityFlashSaleBinding
import com.example.ecommerce.model.filteroptions.Brand
import com.example.ecommerce.model.filteroptions.Category
import com.example.ecommerce.model.filteroptions.Price
import com.example.ecommerce.model.filteroptions.Rating
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.model.sale.SalesListInput
import com.example.ecommerce.model.sales.SalesListResponse
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.viewmodels.product.ProductListingVM
import com.google.android.material.chip.Chip
import com.uniongoods.adapters.SalesBannerAdapter


class FlashSaleActivity : BaseActivity() {
    private lateinit var binding: ActivityFlashSaleBinding
    private lateinit var adapter: FilterProductsAdapter
    private lateinit var productListingVM: ProductListingVM

    private val salesListing = ArrayList<SalesListResponse.Service>()

    // Filters count
    private var categoryCount = 0
    private var brandCount = 0
    private var ratingCount = 0
    private var priceCount = 0

    // Filter Menu
    private val CATEGORY = "Category"
    private val BRAND = "Brand"
    private val RATING = "Rating"
    private val PRICE = "Price"

    // Filter menu options
    val category = Category()


    override fun getLayoutId(): Int {
        return R.layout.activity_flash_sale
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityFlashSaleBinding
        productListingVM = ViewModelProvider(this).get(ProductListingVM::class.java)

        initToolbar()
        initFlashProductsGrid("")
        initFilterListAdapter()

//        productListingVM.listingResponse()
        isLoadingObserver()
        initProductListingObserver()
        productListingVM.salesListResponse(SalesListInput())
    }

    private fun initProductListingObserver() {
        productListingVM.getSalesListing().observe(this,
            Observer<SalesListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (response.body?.services != null) {
//                                initFlashProductsGrid(response.body.services)

                                salesListing.clear()
                                salesListing.addAll(response.body.services)
                                initFlashProductsGrid(response.body.currency ?: "")

                                // Category Items
                                category.items.clear()
                                if (response.body.filters?.categories != null) {
                                    for (item in response.body.filters?.categories) {
                                        category.optionList.put(item.name ?: "", false)

                                        category.items.add(
                                            Category.CatOptions(
                                                id = item.id,
                                                name = item.name,
                                                isSelected = false
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }

    private fun initToolbar() {
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.flash_sale)
        val headingView = binding.commonToolBar.imgToolbarText
        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun initFlashProductsGrid(currency: String) {
        val adapter = ProductsListingGridAdapter(
            this,
            salesListing,
            this,
            currency
        )
        binding.gvFlashProducts.adapter = adapter
        if (salesListing.size > 0) {
            binding.gvFlashProducts.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
        } else {
            binding.gvFlashProducts.visibility = View.GONE
            binding.tvNoRecord.visibility = View.VISIBLE
        }
    }

    private fun initFilterListAdapter() {
        // CATEGORY

//        chipsCategoryOptions.add("All")
//        chipsCategoryOptions.add("Men's Sneakers")
//        chipsCategoryOptions.add("Shoes")
//        chipsCategoryOptions.add("Women's ethnic wear")
//        chipsCategoryOptions.add("Boy's flip flops")
//        chipsCategoryOptions.add("Girl's fashion sandals")
//        chipsCategoryOptions.add("Jeans")

//        val categoryList = HashMap<String, Boolean>()
//        for (pos in 0..chipsCategoryOptions.size - 1) {
//            categoryList.put(chipsCategoryOptions[pos], false)
//        }
//        val category = Category(categoryList)

        // BRAND
        val chipsBrandOptions = ArrayList<String>()
        chipsBrandOptions.add("All")
        chipsBrandOptions.add("Puma")
        chipsBrandOptions.add("Nike")
        chipsBrandOptions.add("UCB")

        val brandList = HashMap<String, Boolean>()
        for (pos in 0..chipsBrandOptions.size - 1) {
            brandList.put(chipsBrandOptions[pos], false)
        }
        val brand = Brand(brandList)

        // RATING
        val chipsRatingOptions = ArrayList<String>()
        chipsRatingOptions.add("All")
        chipsRatingOptions.add("5 Star")
        chipsRatingOptions.add("4 Star")
        chipsRatingOptions.add("3 Star")

        val ratingList = HashMap<String, Boolean>()
        for (pos in 0..chipsRatingOptions.size - 1) {
            ratingList.put(chipsRatingOptions[pos], false)
        }
        val rating = Rating(ratingList)

        // PRICE
        val chipsPriceOptions = ArrayList<String>()
        chipsPriceOptions.add("All")
        chipsPriceOptions.add("High to Low")
        chipsPriceOptions.add("Low to High")

        val priceList = HashMap<String, Boolean>()
        for (pos in 0..chipsPriceOptions.size - 1) {
            priceList.put(chipsPriceOptions[pos], false)
        }
        val price = Price(priceList)

        adapter = FilterProductsAdapter(this,
            object : FilterCategorySelectListener {
                override fun onCategorySelected(position: Int, option: String) {
                    showFilterOptionsAndSetBackgroundAlpha()

                    if (option.equals(CATEGORY)) {

                        initFilterChips(option, category)
                        binding.tvFilterOptionHeading.text = option
                    } else if (option.equals(BRAND)) {

                        initFilterChips(option, brand)
                        binding.tvFilterOptionHeading.text = option
                    } else if (option.equals(RATING)) {

                        initFilterChips(option, rating)
                        binding.tvFilterOptionHeading.text = option
                    } else if (option.equals(PRICE)) {

                        initFilterChips(option, price)
                        binding.tvFilterOptionHeading.text = option
                    }
                }
            }
        )
        binding.rvFilterProducts.adapter = adapter

        binding.rvFilterProducts.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        binding.tvDone.setOnClickListener {
            hideFilterOptionsAndRemoveBackgroundAlpha()
        }
    }

    private fun initFilterChips(title: String, chipsData: Any) {
//        binding.cgFilterChips
        var options = mutableMapOf<String, Boolean>()
        var catItems = ArrayList<Category.CatOptions>()

        if (title.equals(CATEGORY)) {
//            options.putAll((chipsData as Category).optionList)
            options = (chipsData as Category).optionList
            catItems.addAll((chipsData as Category).items)
        } else if (title.equals(BRAND)) {
            options = (chipsData as Brand).optionList
        } else if (title.equals(RATING)) {
            options = (chipsData as Rating).optionList
        } else if (title.equals(PRICE)) {
            options = (chipsData as Price).optionList
        }

        // Reset all chips
        binding.cgFilterChips.removeAllViews()

//        for ((key, value) in map) {
//            println("$key = $value")
//        }

        for ((key, value) in options) {
            val chip = layoutInflater.inflate(
                R.layout.single_chip_layout,
                binding.cgFilterChips,
                false
            ) as Chip

            chip.text = key

            // If already selected chip, show status
//            chip.isChecked = value

            // Change background color of selected chip
            if (value) {
                chip.chipStrokeColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))
                chip.isChecked = value

                chip.chipBackgroundColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orangeTint2))
            }

            // necessary to get single selection working
            chip.isClickable = true
            chip.isCheckable = true
            binding.cgFilterChips.addView(chip)
        }

        binding.cgFilterChips.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip = binding.cgFilterChips.findViewById(checkedId)

            if (chip != null) {
//                UtilsFunctions.showToastSuccess(chip.text.toString())

                val selectedChip = chip.text.toString()

                val selectedOptionName = ArrayList<String>()

                // Save selected chip flag - true
                for (entry in options.entries) {
                    if (entry.key.equals(selectedChip)) {
                        entry.setValue(true)

                        selectedOptionName.add(entry.key)
                    }
                }

                updateFilterCount(title, options)

                hideFilterOptionsAndRemoveBackgroundAlpha()

                // Update filter products Grid
                val selectedCats = ArrayList<String>()
                if (title.equals(CATEGORY)) {
                    // Get id's of selected items
                    for (item in catItems) {
                        for (i in 0..selectedOptionName.size-1)
                            if (item.name.equals(selectedOptionName[i])) {
                                selectedCats.add(item.id!!)
                            }
                    }

                    productListingVM.salesListResponse(SalesListInput(catArray = selectedCats))
                }
            }
        }
    }

    private fun updateFilterCount(title: String, options: MutableMap<String, Boolean>) {
        if (title.equals(CATEGORY)) {
            categoryCount = 0
            for (entry in options.entries) {
                // If any chip selected update count
                if (entry.value) {
                    categoryCount++
                }
            }
        } else if (title.equals(BRAND)) {
            brandCount = 0
            for (entry in options.entries) {
                // If any chip selected update count
                if (entry.value) {
                    brandCount++
                }
            }
        } else if (title.equals(RATING)) {
            ratingCount = 0
            for (entry in options.entries) {
                // If any chip selected update count
                if (entry.value) {
                    ratingCount++
                }
            }
        } else if (title.equals(PRICE)) {
            priceCount = 0
            for (entry in options.entries) {
                // If any chip selected update count
                if (entry.value) {
                    priceCount++
                }
            }
        }

        adapter.updateFilterCounter(categoryCount, brandCount, ratingCount, priceCount)
        adapter.notifyDataSetChanged()
    }


/*    private fun initFilterChips(chipsOptions: ArrayList<String>) {
//        binding.cgFilterChips

        // Reset all chips
        binding.cgFilterChips.removeAllViews()

        for (index in chipsOptions.indices) {
            val chip = layoutInflater.inflate(
                R.layout.single_chip_layout,
                binding.cgFilterChips,
                false
            ) as Chip

            chip.text = chipsOptions[index]

            // necessary to get single selection working
            chip.isClickable = true
            chip.isCheckable = true
            binding.cgFilterChips.addView(chip)
        }

        binding.cgFilterChips.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip = binding.cgFilterChips.findViewById(checkedId)


            if (chip != null) {
//                UtilsFunctions.showToastSuccess(chip.text.toString())

                // Save selected chip flag - true
            }
        }
    }*/

    private fun showFilterOptionsAndSetBackgroundAlpha() {
        binding.llFilterOptions.visibility = View.VISIBLE
        binding.ivBackgroundAlpha.visibility = View.VISIBLE
    }

    private fun hideFilterOptionsAndRemoveBackgroundAlpha() {
        binding.llFilterOptions.visibility = View.GONE
        binding.ivBackgroundAlpha.visibility = View.GONE
    }

    fun isLoadingObserver() {
        productListingVM.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })
    }

    interface FilterCategorySelectListener {
        fun onCategorySelected(position: Int, option: String)
    }

    fun setViewpager(banners: ArrayList<String>) {
        val adapter = SalesBannerAdapter(this, banners, this)
        binding.offersViewpager.adapter = adapter
    }
}