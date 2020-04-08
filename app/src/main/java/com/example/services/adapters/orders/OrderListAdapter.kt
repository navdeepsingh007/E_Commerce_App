package com.uniongoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.databinding.OrderItemBinding
import com.example.services.model.orders.OrdersListResponse
import com.example.services.utils.BaseActivity
import com.example.services.utils.Utils
import com.example.services.views.orders.OrdersListActivity

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
        holder.binding!!.tvOrderOn.text =  Utils(mContext).getDate(
                "MM/dd/yyyy HH:mm:ss",
                addressList[position].created_at,
                "HH:mm yyyy-MM-dd"
        )//addressList[position].created_at

        holder.binding!!.tvTotal.setText(addressList[position].TotalPayment)

        if (addressList[position].progressStatus.equals("Cancelled")) {
            holder.binding!!.tvCancel.setText(addressList[position].progressStatus)
            holder.binding!!.tvCancel.isEnabled = false
        }
        holder.binding!!.tvCancel.setOnClickListener {
            if (orderListActivity != null)
                orderListActivity.cancelOrder(position)
        }
        if (orderListActivity == null) {
            holder.binding!!.tvCancel.visibility = View.GONE
        } else {
            holder.binding!!.tvCancel.visibility = View.VISIBLE
        }
        val orderListAdapter = OrderServicesListAdapter(mContext, addressList[position].orderServices, mContext)
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        holder.binding!!.rvOrderService.layoutManager = linearLayoutManager
        holder.binding!!.rvOrderService.adapter = orderListAdapter
        holder.binding!!.rvOrderService.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }
        })

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