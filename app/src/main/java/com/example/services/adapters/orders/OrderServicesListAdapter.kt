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
import com.example.services.databinding.OrderServiceItemBinding
import com.example.services.model.cart.CartListResponse
import com.example.services.model.orders.OrdersListResponse
import com.example.services.utils.BaseActivity
import com.example.services.views.orders.OrdersListActivity
import kotlinx.android.synthetic.main.trending_service_item.view.*

class OrderServicesListAdapter(
        context: BaseActivity,
        addressList: ArrayList<OrdersListResponse.OrderServices>?,
        var activity: Context
) :
        RecyclerView.Adapter<OrderServicesListAdapter.ViewHolder>() {
    private val mContext: BaseActivity
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<OrdersListResponse.OrderServices>?

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.order_service_item,
                parent,
                false
        ) as OrderServiceItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvServiceName.text = addressList!![position].ServiceName
        holder.binding!!.tvQuantity.setText(mContext.resources.getString(R.string.quantity) + ": " + addressList!![position].quantity)
        holder.binding!!.tvTime.setText(": " + addressList!![position].TimeSlot)
        holder.binding!!.tvDate.setText(": " + addressList!![position].date)

        Glide.with(mContext)
                .load(addressList!![position].icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(R.drawable.ic_category)
                .into(holder.binding!!.imgService!!)

    }


    override fun getItemCount(): Int {
        return addressList!!.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: OrderServiceItemBinding?,
            mContext: BaseActivity,
            addressList: ArrayList<OrdersListResponse.OrderServices>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}