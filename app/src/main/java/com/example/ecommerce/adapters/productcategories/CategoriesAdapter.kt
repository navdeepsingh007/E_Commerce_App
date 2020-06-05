package com.example.ecommerce.adapters.productcategories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowCategoriesBinding
import com.example.ecommerce.model.productcateories.CategoriesResponse
import com.example.ecommerce.model.productcateories.ParentCategoriesResponse


class CategoriesAdapter(
    val context: Context,
    val categoriesList: ArrayList<ParentCategoriesResponse.Service>
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_categories,
            parent,
            false
        ) as RowCategoriesBinding
        return ViewHolder(binding.root, viewType, binding, context, categoriesList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val result = categoriesList[position]

        val categoryName = result.name
        holder.binding.tvCategoryName.text = categoryName

        Glide.with(context)
            .load(result.icon)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.no_image)
            .into(holder.binding.ivCategory)
//
//        holder.binding.tvNotificationName.text = result.notificationTitle
//        holder.binding.tvAssignedDate.text = Utils(context).getDate(
//            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
//            result.createdAt,
//            "dd MMM yyyy, hh:mm a"
//        )
//        holder.binding.tvNotificationDetails.text = result.notificationDescription
    }

    override fun getItemCount(): Int {
        return categoriesList.count()
    }

    inner class ViewHolder
        (
        v: View, val viewType: Int,
        val binding: RowCategoriesBinding,
        context: Context,
        categoriesList: ArrayList<ParentCategoriesResponse.Service>
    ) : RecyclerView.ViewHolder(v)
}
