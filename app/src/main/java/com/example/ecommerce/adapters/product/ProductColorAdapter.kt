package com.example.ecommerce.adapters.product

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.databinding.RowFlashSaleProductBinding
import com.example.ecommerce.databinding.RowProductSelectColorBinding
import com.example.ecommerce.model.product.ProductColor
import com.example.ecommerce.model.productdetail.ProductDetailResponse
import com.example.ecommerce.views.product.ProductChangeListener
import com.example.ecommerce.views.product.ProductDetailsActivity


class ProductColorAdapter(
    val context: Context,
    val selectedColors: ArrayList<ProductColor>,
    val allColors: ArrayList<ProductDetailResponse.ProductSpecification>
//    val flashProductsList: ArrayList<ServicesResponse.TrendingServices>,
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<ProductColorAdapter.TrendingServicesVH>() {
    private val TAG = "ProductColorAdapter"

    override fun getItemCount(): Int {
        return allColors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowProductSelectColorBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_product_select_color,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {

        val response = allColors[position]
        if (response.productColor != null) {
//            holder.binding.ivProductColor.setImageResource(Color.parseColor(response.productColor!!))
//            holder.binding.ivProductColor.setImageResource(R.color.orangeTint2)

//            holder.binding.ivProductColor.setColorFilter(Color.parseColor(response.productColor), PorterDuff.Mode.SRC_ATOP)

            holder.binding.ivProductColor.background.setColorFilter(
                Color.parseColor(response.productColor),
                PorterDuff.Mode.SRC
            )
        }

        if (selectedColors[position].isSelected) {
            holder.binding.ivProductColorSelect.visibility = ViewGroup.VISIBLE
        } else {
            holder.binding.ivProductColorSelect.visibility = ViewGroup.INVISIBLE

        }

//        Glide.with(context)
//            .load("")
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.green)
//            .into(holder.binding.ivProductColor)

        holder.binding.root.setOnClickListener {
            onProductColorSelected(holder, position)

            // productId of selected color
            val productDtl = (context as ProductDetailsActivity)
            if (productDtl is ProductChangeListener) {
                (productDtl as ProductChangeListener).onColorChange(response.id!!)
            }
        }
    }

    fun onProductColorSelected(holder: TrendingServicesVH, position: Int) {
        for (pos in 0..selectedColors.size - 1) {
            selectedColors[pos].isSelected = false
        }
        selectedColors[position].isSelected = true

        notifyDataSetChanged()
//        val totalItems = itemCount
//        for (pos in 0..totalItems - 1) {
//            if (pos == position) {
//                holder.binding.ivProductColor.borderWidth = 5
//                holder.binding.ivProductColor.borderColor =
//                    context.resources.getColor(R.color.colorPrimary)
//            } else {
//                holder.binding.ivProductColor.borderWidth = 0
//                holder.binding.ivProductColor.borderColor = 0
//            }
//        }
    }

    inner class TrendingServicesVH(val binding: RowProductSelectColorBinding) :
        RecyclerView.ViewHolder(binding.root)
}