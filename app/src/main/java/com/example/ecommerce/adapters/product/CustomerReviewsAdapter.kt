package com.example.ecommerce.adapters.product

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ecommerce.R
import com.example.ecommerce.application.MyApplication
import com.example.ecommerce.databinding.RowCustomerReviewsBinding


class CustomerReviewsAdapter(
    val context: Context
//    val flashProductsList: ArrayList<ServicesResponse.TrendingServices>,
//    val bannerUrls: ArrayList<String>,
//    var delegate: ServicesFragment.IAdapter
) : RecyclerView.Adapter<CustomerReviewsAdapter.TrendingServicesVH>() {
    private val TAG = "CustomerReviewsAdapter"

    override fun getItemCount(): Int {
        return 5
//        return flashProductsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingServicesVH {
        val binding = DataBindingUtil.inflate<RowCustomerReviewsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_customer_reviews,
            parent,
            false
        )
        return TrendingServicesVH(binding)
    }

    override fun onBindViewHolder(holder: TrendingServicesVH, position: Int) {

//        holder.binding.tvServiceName.text = trends.name

        holder.binding.tvProfileName.text = "James Lawson"
        holder.binding.tvCustReview.text = MyApplication.instance.getString(R.string.customer_review)
//        holder.binding.tvCustReviewDate.text = "$534.33"
        holder.binding.custRating.rating = 4f

        // Product Pic
//        Glide.with(context)
//            .load("")
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.no_image_available)
//            .into(holder.binding.ivReviewedProductPic)

        // Customer Pic
//        Glide.with(context)
//            .load("")
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.no_image_available)
//            .into(holder.binding.ivCustomerPic)

        holder.binding.root.setOnClickListener {
//            context.startActivity(Intent(context, ProductDetailsActivity::class.java))

//            val intent = Intent(context, ProductDetailsActivity::class.java)
//            intent.putExtra(ExtrasConstants.SERVICE_ID, response.id)
//            context.startActivity(intent)
        }
    }

    inner class TrendingServicesVH(val binding: RowCustomerReviewsBinding) :
        RecyclerView.ViewHolder(binding.root)
}