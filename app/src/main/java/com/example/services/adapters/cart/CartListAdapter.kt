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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.services.R
import com.example.services.databinding.CartItemBinding
import com.example.services.model.address.AddressListResponse
import com.example.services.model.cart.CartListResponse
import com.example.services.model.services.Body
import com.example.services.views.cart.CartListActivity
import com.example.services.views.subcategories.ServicesListActivity

class CartListAdapter(
        context: CartListActivity,
        addressList: ArrayList<CartListResponse.Body>,
        var activity: Context
) :
        RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    private val mContext: CartListActivity
    private var viewHolder: ViewHolder? = null
    private var addressList: ArrayList<CartListResponse.Body>

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cart_item,
                parent,
                false
        ) as CartItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvCatName.text = addressList[position].service?.name
        holder.binding!!.tvQuantity.setText(mContext.resources.getString(R.string.quantity) + ": " + addressList[position].quantity)
        holder.binding!!.tvOfferPrice.setText(
                addressList[position].service?.currency.toString() + " " + addressList[position].price.toString()
        )
        //holder.binding!!.rBar.setRating(addressList[position].rating?.toFloat())
        Glide.with(mContext)
                .load(addressList[position].service?.icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(R.drawable.ic_category)
                .into(holder.binding.imgCart)
        //img_cat
        holder.binding!!.imgRemove.setOnClickListener {
            mContext.addRemoveToCart(position/*, holder.binding!!.imgCart.getText().toString()*/)
        }
        if (addressList[position].service?.timeSlotStatus.equals("false")) {
            holder.binding.tvNoSlotAvailable.visibility = View.VISIBLE
            holder.binding.serviceItem.setBackgroundColor(mContext.resources.getColor(R.color.colorGrey1))
        } else {
            holder.binding.tvNoSlotAvailable.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: CartItemBinding?,
            mContext: CartListActivity,
            addressList: ArrayList<CartListResponse.Body>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}