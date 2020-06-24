package com.uniongoods.adapters

import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.databinding.OrderItemBinding
import com.example.ecommerce.model.orders.OrdersListResponse
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.utils.Utils
import com.example.ecommerce.views.orders.OrderDetailActivity
import com.example.ecommerce.views.orders.OrdersListActivity
import com.example.ecommerce.views.orders.cancelorder.CancelOrderActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.row_categories.*
import java.util.*
import kotlin.collections.ArrayList

class OrderListAdapter(
    context: BaseActivity,
    addressList: ArrayList<OrdersListResponse.Body>,
    activity: OrdersListActivity?
) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    private val mContext: BaseActivity
    private val orderListActivity: OrdersListActivity?
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<OrdersListResponse.Body>

    init {
        this.mContext = context
        this.addressList = addressList
        orderListActivity = activity
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.order_item,
            parent,
            false
        ) as OrderItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvCatName.text =  addressList.get(position).suborders!!.get(0).service!!.name
        holder.binding!!.tvVendor.text = addressList.get(position).suborders!!.get(0).service!!.brandName
        if (!TextUtils.isEmpty(addressList.get(position).suborders!!.get(0).color))
        holder.binding!!.tvColorValue.setBackgroundColor(Color.parseColor(addressList.get(position).suborders!!.get(0).color))
        holder.binding.tvSizeValue.text = addressList.get(position).suborders!!.get(0).size
        holder.binding.tvUnitValue.text = addressList.get(position).suborders!!.get(0).quantity
        holder.binding.tvOfferPrice.text = SharedPrefClass().getPrefValue(mContext,GlobalConstants.CurrencyPreference).toString()+addressList.get(position).totalOrderPrice
        holder.binding!!.tvDateValue.text = Utils(mContext).getDate(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            addressList[position].serviceDateTime,
            "EEE MMM dd, yyyy hh:mm aa"
        )
        Glide.with(mContext).load(addressList.get(position).suborders!!.get(0).service!!.icon).
        into(holder.binding!!.imgCart)

         holder.binding!!.rlOrderItem.setOnClickListener{
              val intent = Intent(orderListActivity, OrderDetailActivity::class.java)
            orderListActivity!!.startActivity(intent)
        }

        holder.binding!!.tvCancel.setOnClickListener {
            val intent = Intent(orderListActivity, CancelOrderActivity::class.java)
            intent.putExtra("orderId", addressList.get(position).id)
            orderListActivity!!.startActivity(intent)
        }
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

        return calendar.time
    }

    override fun getItemCount(): Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v: View, val viewType: Int, //These are the general elements in the RecyclerView
        val binding: OrderItemBinding?,
        mContext: BaseActivity,
        addressList: ArrayList<OrdersListResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}