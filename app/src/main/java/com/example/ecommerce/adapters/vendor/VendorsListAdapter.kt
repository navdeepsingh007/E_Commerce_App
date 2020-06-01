package com.uniongoods.adapters

import android.content.Intent
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
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.VendorItemBinding
import com.example.ecommerce.model.vendor.VendorListResponse
import com.example.ecommerce.views.home.DashboardActivity
import com.example.ecommerce.views.vendor.VendorsListActivity

class VendorsListAdapter(
    context: VendorsListActivity,
    addressList: ArrayList<VendorListResponse.Body>
    //  var activity: Context
) :
    RecyclerView.Adapter<VendorsListAdapter.ViewHolder>() {
    private val mContext: VendorsListActivity
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<VendorListResponse.Body>

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.vendor_item,
            parent,
            false
        ) as VendorItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder

        holder.binding!!.tvVendorName.text = addressList[position].companyName

        // holder.binding.imgFavourite.setImageResource(R.drawable.ic_delete)
        //holder.binding!!.rBar.setRating(addressList[position].rating?.toFloat())
        Glide.with(mContext)
            .load(addressList[position].logo1)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.ic_category)
            .into(holder.binding.tvVendorImage)


        holder.binding!!.tvVendorImage.setOnClickListener {
            // mContext.callServiceDetail(addressList[position].service?.id!!)
            /*SharedPrefClass().putObject(
                mContext,
                GlobalConstants.COMPANY_ID,
                addressList[position].id

            )*/
            GlobalConstants.COMPANY_ID = addressList[position].id.toString()
            val intent = Intent(mContext, DashboardActivity::class.java)
            mContext.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v: View, val viewType: Int, //These are the general elements in the RecyclerView
        val binding: VendorItemBinding?,
        mContext: VendorsListActivity,
        addressList: ArrayList<VendorListResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}