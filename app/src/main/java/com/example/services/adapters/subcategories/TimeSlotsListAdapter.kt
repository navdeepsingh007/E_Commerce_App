package com.uniongoods.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.services.R
import com.example.services.databinding.TimeItemBinding
import com.example.services.model.services.Body
import com.example.services.model.services.TimeSlotsResponse
import com.example.services.views.subcategories.ServiceDetailActivity
import com.example.services.views.subcategories.ServicesListActivity

class TimeSlotsListAdapter(
    context : ServiceDetailActivity,
    addressList : ArrayList<TimeSlotsResponse.Body>,
    var activity : Context
) :
    RecyclerView.Adapter<TimeSlotsListAdapter.ViewHolder>() {
    private val mContext : ServiceDetailActivity
    private var viewHolder : ViewHolder? = null
    private var addressList : ArrayList<TimeSlotsResponse.Body>

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.time_item,
            parent,
            false
        ) as TimeItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder : ViewHolder, position : Int) {
        viewHolder = holder
        holder.binding!!.tvCatName.text = addressList[position].timing
        if(addressList[position].selected.equals("true")){
            // holder.binding.topLay.setBackgroundResource(R.drawable.btn_bg_shape_colored)
            holder.binding.tvCatName.setBackgroundColor(mContext.resources.getColor(R.color.btnBackground))
            holder.binding.tvCatName.setTextColor(mContext.resources.getColor(R.color.colorWhite))
        }else{
            // holder.binding.topLay.setBackgroundResource(R.drawable.shape_round_corner)
            holder.binding.tvCatName.setTextColor(mContext.resources.getColor(R.color.colorBlack))
            holder.binding.tvCatName.setBackgroundColor(mContext.resources.getColor(R.color.btnBackgroundWhite))
        }
        holder.binding!!.tvCatName.setOnClickListener {
            mContext.selectTimeSlot(position)
        }
    }

    override fun getItemCount() : Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v : View, val viewType : Int, //These are the general elements in the RecyclerView
        val binding : TimeItemBinding?,
        mContext : ServiceDetailActivity,
        addressList : ArrayList<TimeSlotsResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}