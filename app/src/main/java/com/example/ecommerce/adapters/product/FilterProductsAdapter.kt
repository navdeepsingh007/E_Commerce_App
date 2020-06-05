package com.example.ecommerce.adapters.product

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowFilterMenuBinding
import com.example.ecommerce.views.homenew.FlashSaleActivity


class FilterProductsAdapter(
    val context: Context,
    val delegate: FlashSaleActivity.FilterCategorySelectListener
//    val flashProductsList: ArrayList<ServicesResponse.TrendingServices>,
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<FilterProductsAdapter.TrendingServicesVH>() {
    private val TAG = "FilterProductsAdapter"
    private val filterList = ArrayList<String>()
    private var categoryCount = 0
    private var brandCount = 0
    private var ratingCount = 0
    private var priceCount = 0

    // Filter options
    private val CATEGORY = "Category"
    private val BRAND = "Brand"
    private val RATING = "Rating"
    private val PRICE = "Price"

    init {
        filterList.add(CATEGORY)
        filterList.add(BRAND)
        filterList.add(RATING)
        filterList.add(PRICE)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowFilterMenuBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_filter_menu,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {
        holder.binding.tvFilterOption.text = filterList[position]

        holder.binding.root.setOnClickListener {
            delegate.onCategorySelected(position, filterList[position])
        }

        // Update count
        if (position == 0) {
            val count = "($categoryCount)"
            visibleCounterAccToCount(holder, count, categoryCount)
        } else if (position == 1) {
            val count = "($brandCount)"
            visibleCounterAccToCount(holder, count, brandCount)
        } else if (position == 2) {
            val count = "($ratingCount)"
            visibleCounterAccToCount(holder, count, ratingCount)
        } else if (position == 3) {
            val count = "($priceCount)"
            visibleCounterAccToCount(holder, count, priceCount)
        }
    }

    private fun visibleCounterAccToCount(
        holder: TrendingServicesVH,
        count: String,
        countType: Int
    ) {
        if (countType > 0) {
            holder.binding.tvFilterCount.text = count
            holder.binding.tvFilterCount.visibility = View.VISIBLE

            // Set background color
            holder.binding.rlFilterMenu.setBackgroundResource(R.drawable.rectangle_orange_stroke_transparent)

            holder.binding.rlFilterMenuItem.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.orangeTint2
                )
            )

//            holder.binding.rlFilterMenuItem.background.setColorFilter(
//                ContextCompat.getColor(
//                    context,
//                    R.color.orangeTint2
//                ),
//                android.graphics.PorterDuff.Mode.SRC_IN
//            )

/*            holder.binding.rlFilterMenu.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.red
                ),
//                    android.graphics.PorterDuff.Mode.SRC_IN
                android.graphics.PorterDuff.Mode.SRC_IN
            )*/
        } else {
            holder.binding.tvFilterCount.visibility = View.GONE

            // Set background color
            holder.binding.rlFilterMenu.setBackgroundResource(R.drawable.rectangle_grey_stroke_transparent)

//            holder.binding.rlFilterMenu.setBackgroundColor(
//                ContextCompat.getColor(
//                    context,
//                    R.color.orangeTint
//                )
//            )
        }
    }

    fun updateFilterCounter(
        categoryCount: Int,
        brandCount: Int,
        ratingCount: Int,
        priceCount: Int
    ) {
        this.categoryCount = categoryCount
        this.brandCount = brandCount
        this.ratingCount = ratingCount
        this.priceCount = priceCount
    }

    inner class TrendingServicesVH(val binding: RowFilterMenuBinding) :
        RecyclerView.ViewHolder(binding.root)
}