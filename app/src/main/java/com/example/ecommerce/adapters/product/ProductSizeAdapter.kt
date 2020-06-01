package com.example.ecommerce.adapters.product

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowFlashSaleProductBinding
import com.example.ecommerce.databinding.RowProductSelectSizeBinding
import com.example.ecommerce.model.product.ProductSize


class ProductSizeAdapter(
    val context: Context,
    val sizeList: ArrayList<ProductSize>
//    val flashProductsList: ArrayList<ServicesResponse.TrendingServices>,
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<ProductSizeAdapter.TrendingServicesVH>() {
    private val TAG = "ProductSizeAdapter"
    private var selectedPos = 0

    override fun getItemCount(): Int {
        return 4
//        return flashProductsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowProductSelectSizeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_product_select_size,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {
        if (sizeList[position].selected) {
            holder.binding.topLayout.visibility = View.VISIBLE
        } else {
            holder.binding.topLayout.visibility = View.INVISIBLE
        }

        holder.binding.rlSize.setOnClickListener {
            onProductSizeSelected(position)
        }
    }

    fun onProductSizeSelected(position: Int) {
        for (pos in 0..sizeList.size - 1) {
            sizeList[pos].selected = false
        }
        sizeList[position].selected = true

        notifyDataSetChanged()
    }

    inner class TrendingServicesVH(val binding: RowProductSelectSizeBinding) :
        RecyclerView.ViewHolder(binding.root)
}