package com.uniongoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.databinding.SubcategoriesItemBinding
import com.example.ecommerce.views.subcategories.SubCategoriesActivity

class SubCategoriesListAdapter(
    context : SubCategoriesActivity,
    addressList : ArrayList<com.example.ecommerce.model.subcategories.Subcategories>,
    var activity : Context
) :
    RecyclerView.Adapter<SubCategoriesListAdapter.ViewHolder>() {
    private val mContext : SubCategoriesActivity
    private var viewHolder : ViewHolder? = null
    private var addressList : ArrayList<com.example.ecommerce.model.subcategories.Subcategories>

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.subcategories_item,
            parent,
            false
        ) as SubcategoriesItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder : ViewHolder, position : Int) {
        viewHolder = holder
        holder.binding!!.tvCatName.text = addressList[position].name
       // holder.binding!!.rBar.setRating(addressList[position].)
        Glide.with(mContext)
            .load(addressList[position].thumbnail)
            //.apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.ic_category)
            .into(holder.binding.imgCat)
        holder.binding!!.catItem.setOnClickListener {
            mContext.callServicesActivity(position)
        }

    }

    override fun getItemCount() : Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v : View, val viewType : Int, //These are the general elements in the RecyclerView
        val binding : SubcategoriesItemBinding?,
        mContext : SubCategoriesActivity,
        addressList : ArrayList<com.example.ecommerce.model.subcategories.Subcategories>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}