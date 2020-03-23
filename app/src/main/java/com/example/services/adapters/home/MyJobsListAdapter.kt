package com.uniongoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.databinding.CategoryItemBiniding
import com.example.services.model.home.JobsResponse
import com.example.services.views.home.HomeFragment
import kotlin.collections.ArrayList

class MyJobsListAdapter(
    context : HomeFragment,
    addressList : ArrayList<JobsResponse.Data>,
    var activity : Context
) :
    RecyclerView.Adapter<MyJobsListAdapter.ViewHolder>() {
    private val mContext : HomeFragment
    private var viewHolder : ViewHolder? = null
    private var jobsList : ArrayList<JobsResponse.Data>

    init {
        this.mContext = context
        this.jobsList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_item,
            parent,
            false
        ) as CategoryItemBiniding
        return ViewHolder(binding.root, viewType, binding, mContext, jobsList)
    }

    override fun onBindViewHolder(@NonNull holder : ViewHolder, position : Int) {
        viewHolder = holder
       // holder.binding!!.tvFromLocationName.text = jobsList[position].from_location

    }

    override fun getItemCount() : Int {
        return 5//jobsList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v : View, val viewType : Int, //These are the general elements in the RecyclerView
        val binding : CategoryItemBinding?,
        mContext : HomeFragment,
        addressList : ArrayList<JobsResponse.Data>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}