package com.example.ecommerce.adapters.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowProductCategoriesBinding
import com.example.ecommerce.model.homenew.HomeResponse


class ProductCategoriesAdapter(
    val context: Context,
    val categoryList: ArrayList<HomeResponse.Category>
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<ProductCategoriesAdapter.TrendingServicesVH>() {
    private val TAG = "CategoriesAdapter"

    override fun getItemCount(): Int {
//        return 5
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowProductCategoriesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_product_categories,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {
        val response = categoryList[position]

        holder.binding.tvCategoryName.text = response.name

        Glide.with(context)
            .load(response.thumbnail)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .into(holder.binding.ivCategory)

        holder.binding.root.setOnClickListener {
//            onTrendingServicesClick(position)
        }
    }

    fun onTrendingServicesClick(position: Int) {

    }

    inner class TrendingServicesVH(val binding: RowProductCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)
}