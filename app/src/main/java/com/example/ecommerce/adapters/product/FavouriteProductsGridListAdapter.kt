package com.example.ecommerce.adapters.product

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.views.product.FavDeleteListener
import com.example.ecommerce.views.product.FavouriteProductsActivity
import com.example.ecommerce.views.product.ProductDetailsActivity

class FavouriteProductsGridListAdapter(
    val context: FragmentActivity,
    val productsList: ArrayList<FavouriteListResponse.Body>,
    var activity: Context
) :
    ArrayAdapter<FavouriteProductsGridListAdapter.ItemHolder>(activity, R.layout.category_item) {

//    private var viewHolder: ItemHolder? = null
//    private var categoriesList: ArrayList<com.example.ecommerce.viewmodels.home.Services>

//    init {
//        this.categoriesList = productsList
//    }

    override fun getCount(): Int {
        return productsList.size
//        return if (this.categoriesList != null) this.categoriesList.size else 0
    }

    fun updateGridList(list: ArrayList<FavouriteListResponse.Body>) {
        this.productsList.clear()
        this.productsList.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ItemHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.row_favourite_products_grid, null)
            holder = ItemHolder()
            holder.icon = convertView!!.findViewById(R.id.ivProduct)
            holder.delete = convertView!!.findViewById(R.id.ivDelete)
            holder.name = convertView!!.findViewById(R.id.tvProductName)
            holder.rating = convertView!!.findViewById(R.id.rbProductRating)
            holder.price = convertView!!.findViewById(R.id.tvProductPrice)
            holder.offPrice = convertView!!.findViewById(R.id.tvProductOff)
            holder.offPercentage = convertView!!.findViewById(R.id.tvProductPercentageOff)
            holder.topLayout = convertView.findViewById(R.id.topLayout)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemHolder
        }

//        holder.topLayout!!.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + categoriesList[position].colorCode.trim()))/*mContext.getResources().getColorStateList(R.color.colorOrange)*/)

        val response = productsList[position]

        holder.name!!.text = response.product?.name
        // Show three dots at end of textview
        holder.name!!.ellipsize = TextUtils.TruncateAt.END
        holder.name!!.maxEms = 8
        holder.name!!.setSingleLine(true)

        holder.price!!.text = response.product?.originalPrice
        holder.offPrice!!.text = response.product?.price
        holder.offPercentage!!.text = response.product?.offer.toString()
//        holder.rating!!.rating = response.product?.rat

        // Strike through off price
        val price = response.product?.price ?: ""
        if (!price.equals(response.product?.originalPrice)) {
            holder.price?.text = response.product?.originalPrice ?: ""
            if (holder.offPrice != null)
                holder.offPrice?.setPaintFlags(holder.offPrice!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            val percentOff = "${response.product?.offer}% off"
            holder.offPercentage?.text = percentOff

            holder.offPercentage?.visibility = View.VISIBLE
            holder.offPrice?.visibility = View.VISIBLE
        } else {
            holder.offPercentage?.visibility = View.INVISIBLE
            holder.offPrice?.visibility = View.INVISIBLE
        }


//        holder.topLayout?.setOnClickListener { onProductClick() }

        Glide.with(context)
            .load(response.product?.icon)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.no_image)
            .into(holder.icon!!)

        holder.delete?.setOnClickListener {
            val favProducts = activity

            if (favProducts is FavDeleteListener)
                (activity as FavouriteProductsActivity).onProductDelete(response.id ?: "")
        }

        return convertView
    }

    private fun onProductClick() {
//        val intent = Intent(context, ProductDetailsActivity::class.java)
//        intent.putExtra(ExtrasConstants.SERVICE_ID, response.id)
//        context.startActivity(intent)
    }

    class ItemHolder {
        var name: TextView? = null
        var price: TextView? = null
        var rating: RatingBar? = null
        var offPrice: TextView? = null
        var offPercentage: TextView? = null
        var icon: ImageView? = null
        var delete: ImageView? = null
        var topLayout: LinearLayout? = null
    }

}