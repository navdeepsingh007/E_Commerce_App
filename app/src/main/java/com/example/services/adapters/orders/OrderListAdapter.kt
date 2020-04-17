package com.uniongoods.adapters

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
        holder.binding!!.tvOrderOn.text = Utils(mContext).getDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                addressList[position].serviceDateTime,
                "HH:mm yyyy-MM-dd"
        )
        holder.binding!!.tvServiceOn.text = Utils(mContext).getDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                addressList[position].serviceDateTime,
                "HH:mm yyyy-MM-dd"
        )

        holder.binding!!.tvTotal.setText(addressList[position].totalOrderPrice)
////0-Pending/Not Confirmed, 1-> Confirmed , 2->Cancelled , 3->Processing,4//cancelled by company, 5->Completed
        if (addressList[position].progressStatus.equals("0")) {
            holder.binding!!.tvCancel.setText("Pending"/*addressList[position].progressStatus*/)
            holder.binding!!.tvCancel.isEnabled = true
        } else if (addressList[position].progressStatus.equals("1")) {
            holder.binding!!.tvCancel.setText("Confirmed"/*addressList[position].progressStatus*/)
            holder.binding!!.tvCancel.isEnabled = false
        } else if (addressList[position].progressStatus.equals("2")) {
            holder.binding!!.tvCancel.setText("Cancelled"/*addressList[position].progressStatus*/)
            holder.binding!!.tvCancel.isEnabled = false
        } else if (addressList[position].progressStatus.equals("3")) {
            holder.binding!!.tvCancel.setText("Processing"/*addressList[position].progressStatus*/)
            holder.binding!!.tvCancel.isEnabled = false
        } else if (addressList[position].progressStatus.equals("4")) {
            holder.binding!!.tvCancel.setText("Cancelled by company"/*addressList[position].progressStatus*/)
            holder.binding!!.tvCancel.isEnabled = false
        } else if (addressList[position].progressStatus.equals("5")) {
            holder.binding!!.tvCancel.setText("Completed"/*addressList[position].progressStatus*/)
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
        val orderListAdapter = OrderServicesListAdapter(mContext, addressList[position].suborders, mContext)
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