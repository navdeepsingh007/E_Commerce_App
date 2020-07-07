package com.uniongoods.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.CancelReasonItemBinding
import com.example.ecommerce.model.orders.ReasonListResponse
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.views.orders.cancelorder.CancelOrderActivity
import java.util.*


class CancelOrderReasonAdapter(
    context: BaseActivity,
    addressList: ArrayList<ReasonListResponse.Body>,
    activity: CancelOrderActivity?
) :
    RecyclerView.Adapter<CancelOrderReasonAdapter.ViewHolder>() {
    private val mContext: BaseActivity
    private val orderListActivity: CancelOrderActivity?
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<ReasonListResponse.Body>
    private var radioButton: RadioButton? = null
    private var selectedPosition: Int? = null

    init {
        this.mContext = context
        this.addressList = addressList
        orderListActivity = activity
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cancel_reason_item,
            parent,
            false
        ) as CancelReasonItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvReasonTitle.text = addressList.get(position).reason

        holder.binding!!.rbReason.setOnCheckedChangeListener { buttonView, isChecked ->
            if (holder.binding!!.rbReason.isPressed) {
                selectedPosition = position
                orderListActivity!!.handleRadioButton()
            }
           if(radioButton != null){
               radioButton!!.isChecked = false
           }
            radioButton = holder.binding!!.rbReason
        }
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

        return calendar.time
    }

    fun getSelectedReason(): String{
        if (selectedPosition!=null){
         return   addressList!!.get(selectedPosition!!).reason!!
        }else{
           return ""
        }
    }

    fun setData(reasonList: ArrayList<ReasonListResponse.Body>){
        addressList = reasonList
        notifyDataSetChanged()
    }

    fun handleListRadioButton(){
        if(radioButton != null){
            radioButton!!.isChecked = false
        }
    }

    override fun getItemCount(): Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v: View, val viewType: Int, //These are the general elements in the RecyclerView
        val binding: CancelReasonItemBinding?,
        mContext: BaseActivity,
        addressList: ArrayList<ReasonListResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}