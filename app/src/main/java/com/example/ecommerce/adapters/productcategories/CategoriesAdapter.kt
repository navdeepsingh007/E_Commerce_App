package com.example.ecommerce.adapters.productcategories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.RowCategoriesBinding
import com.example.ecommerce.model.productcateories.CategoriesResponse


class CategoriesAdapter(
    val context: Context,
    val categoriesList: ArrayList<CategoriesResponse.Body>
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
//        val result = categoriesList[position]
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
//        return categoriesList.count()
        return 20
    }

    inner class ViewHolder
        (
        v: View, val viewType: Int,
        val binding: RowCategoriesBinding,
        context: Context,
        categoriesList: ArrayList<CategoriesResponse.Body>
    ) : RecyclerView.ViewHolder(v)
}
