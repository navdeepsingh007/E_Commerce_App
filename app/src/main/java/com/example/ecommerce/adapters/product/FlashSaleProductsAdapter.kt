package com.example.ecommerce.adapters.product

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowFlashSaleProductBinding
import com.example.ecommerce.model.homenew.HomeResponse
import com.example.ecommerce.utils.ExtrasConstants
import com.example.ecommerce.views.product.ProductDetailsActivity


class FlashSaleProductsAdapter(
    val context: Context,
    val flashSaleList: ArrayList<HomeResponse.Sale>
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<FlashSaleProductsAdapter.TrendingServicesVH>() {
    private val TAG = "FlashSaleProdAdapter"

    override fun getItemCount(): Int {
//        return 5
        return flashSaleList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowFlashSaleProductBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_flash_sale_product,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {
        val response = flashSaleList[position]

        holder.binding.tvProductName.text = response.name ?: ""
        holder.binding.tvProductPrice.text = response.price ?: ""


        // Strike through off price

        if (!response.price.equals(response.originalPrice)) {
            holder.binding.tvProductOff.text = response.originalPrice ?: ""
            holder.binding.tvProductOff.setPaintFlags(holder.binding.tvProductOff.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            val percentOff = "${response.offer}% off"
            holder.binding.tvProductPercentageOff.text = percentOff

            holder.binding.tvProductPercentageOff.visibility = View.VISIBLE
            holder.binding.tvProductOff.visibility = View.VISIBLE
        } else {
            holder.binding.tvProductPercentageOff.visibility = View.INVISIBLE
            holder.binding.tvProductOff.visibility = View.INVISIBLE
        }

        Glide.with(context)
            .load(response.icon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .into(holder.binding.ivProduct)

        holder.binding.root.setOnClickListener {
//            onTrendingServicesClick(position)
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(ExtrasConstants.SERVICE_ID, response.id)
            context.startActivity(intent)
        }
    }

    fun onTrendingServicesClick(position: Int) {

    }

    inner class TrendingServicesVH(val binding: RowFlashSaleProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}