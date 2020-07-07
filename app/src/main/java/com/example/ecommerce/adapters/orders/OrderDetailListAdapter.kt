package com.uniongoods.adapters

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.OrderListDetailItemBinding
import com.example.ecommerce.model.orders.OrdersDetailNewResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.Utils
import com.example.ecommerce.views.orders.OrderDetailActivity
import java.util.*
import kotlin.collections.ArrayList

class OrderDetailListAdapter(
    context: BaseActivity,
    addressList: ArrayList<OrdersDetailNewResponse.Suborders>,
    activity: OrderDetailActivity
) :
    RecyclerView.Adapter<OrderDetailListAdapter.ViewHolder>() {
    private val mContext: BaseActivity
    private val orderDetailListActivity: OrderDetailActivity?
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<OrdersDetailNewResponse.Suborders>? = null

    init {
        this.mContext = context
        this.addressList = addressList
        orderDetailListActivity = activity
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.order_list_detail_item,
            parent,
            false
        ) as OrderListDetailItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvCatName.text =  addressList!!.get(position)!!.product!!.name
        holder.binding!!.tvVendor.text = addressList!!.get(position)!!.product!!.brandName
        if (!TextUtils.isEmpty(addressList!!.get(position)!!.color))
            holder.binding!!.tvColorValue.setBackgroundColor(Color.parseColor(addressList!!.get(position)!!.color))
        holder.binding.tvSizeValue.text = addressList!!.get(position)!!.size
        holder.binding.tvUnitValue.text = addressList!!.get(position)!!.quantity
        holder.binding.tvOfferPrice.text = SharedPrefClass().getPrefValue(mContext, GlobalConstants.CurrencyPreference).toString()+addressList!!.get(position).product!!.price
        Glide.with(mContext).load(addressList!!.get(position).product!!.icon).
        into(holder.binding!!.imgCart)
        if (addressList!![position].progressStatus == 4){
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepPick)
        }else if(addressList!![position].progressStatus == 5){
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepPick)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepShipped)
            holder.binding.viewStepPick.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
        } else if(addressList!![position].progressStatus == 6){
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepPick)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepShipped)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepArrived)
            holder.binding.viewStepPick.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
            holder.binding.viewStepShipped.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
        }
        else if(addressList!![position].progressStatus == 7){
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepPick)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepShipped)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepArrived)
            Glide.with(mContext).load(R.drawable.ic_check_status).
            into(holder.binding.imgStepDelivered)
            holder.binding.viewStepPick.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
            holder.binding.viewStepShipped.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
            holder.binding.viewStepArrived.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
        } else {
            Glide.with(mContext).load(R.drawable.ic_uncheck_status).
            into(holder.binding.imgStepPick)
            Glide.with(mContext).load(R.drawable.ic_uncheck_status).
            into(holder.binding.imgStepShipped)
            Glide.with(mContext).load(R.drawable.ic_uncheck_status).
            into(holder.binding.imgStepArrived)
            Glide.with(mContext).load(R.drawable.ic_uncheck_status).
            into(holder.binding.imgStepDelivered)
            holder.binding.viewStepPick.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGrey0))
            holder.binding.viewStepShipped.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGrey0))
            holder.binding.viewStepArrived.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGrey0))
        }

    }

    fun setData( orderDetailList: ArrayList<OrdersDetailNewResponse.Suborders>){
        addressList = orderDetailList
        notifyDataSetChanged()
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

        return calendar.time
    }

    override fun getItemCount(): Int {
        return addressList!!.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v: View, val viewType: Int, //These are the general elements in the RecyclerView
        val binding: OrderListDetailItemBinding?,
        mContext: BaseActivity,
        addressList: ArrayList<OrdersDetailNewResponse.Suborders>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}