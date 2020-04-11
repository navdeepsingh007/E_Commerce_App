package com.uniongoods.adapters

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
import com.example.services.R
import com.example.services.databinding.PromoCodeItemBinding
import com.example.services.model.promocode.PromoCodeListResponse
import com.example.services.views.promocode.PromoCodeActivity

class PromoCodeListAdapter(
    context : PromoCodeActivity,
    addressList : ArrayList<PromoCodeListResponse.Body>,
    var activity : Context
) :
    RecyclerView.Adapter<PromoCodeListAdapter.ViewHolder>() {
    private val mContext : PromoCodeActivity
    private var viewHolder : ViewHolder? = null
    private var addressList : ArrayList<PromoCodeListResponse.Body>

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.promo_code_item,
            parent,
            false
        ) as PromoCodeItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder : ViewHolder, position : Int) {
        viewHolder = holder
        holder.binding!!.tvPromo.text = addressList[position].name
        holder.binding!!.tvPromoCode.text = addressList[position].code

        holder.binding!!.tvPromoDesc.setText(
            addressList[position].description
        )
         //holder.binding!!.rBar.setRating(addressList[position].rating?.toFloat())
         Glide.with(mContext)
             .load(addressList[position].icon)
             .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
             .placeholder(R.drawable.ic_category)
             .into(holder.binding.imgPromo)

        holder.binding!!.btnApply.setOnClickListener {
            mContext.callApplyCouponApi(addressList[position].code!!)
        }



    }

    override fun getItemCount() : Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
                v : View, val viewType : Int, //These are the general elements in the RecyclerView
                val binding : PromoCodeItemBinding?,
                mContext : PromoCodeActivity,
                addressList : ArrayList<PromoCodeListResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}