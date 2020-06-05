package com.example.ecommerce.adapters.product

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.model.product.ProductListingResponse
import com.example.ecommerce.model.sales.SalesListResponse
import com.example.ecommerce.utils.ExtrasConstants
import com.example.ecommerce.views.product.ProductDetailsActivity

class ProductsListingGridAdapter(
    val context: FragmentActivity,
    val productsList: ArrayList<SalesListResponse.Service>,
    var activity: Context,
    val currency: String
) :
    ArrayAdapter<ProductsListingGridAdapter.ItemHolder>(activity, R.layout.category_item) {

    private var viewHolder: ItemHolder? = null
//    private var categoriesList: ArrayList<Recommended>

    init {
//        this.categoriesList = productsList
    }

    override fun getCount(): Int {
        return productsList.size
        // return 5
//        return if (this.categoriesList != null) this.categoriesList.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ItemHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.row_grid_product, null)
            holder = ItemHolder()
            holder.icon = convertView.findViewById(R.id.ivProduct)
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

        holder.name!!.text = response.name

        val price = "$currency${response.price}"
        holder.price!!.text = price
        holder.rating!!.rating = response.rating?.toFloat() ?: 0f

        // Strike through off price
        if (!response.originalPrice.equals(response.price)) {
            holder.offPrice!!.setPaintFlags(holder.offPrice!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            val percentOff = "${response.offer}% off"

            val originalPrice = "$currency${response.originalPrice}"
            holder.offPrice!!.text = originalPrice
            holder.offPercentage!!.text = percentOff

            holder.offPrice?.visibility = View.VISIBLE
            holder.offPercentage?.visibility = View.VISIBLE
        } else {
            holder.offPrice?.visibility = View.INVISIBLE
            holder.offPrice?.visibility = View.INVISIBLE
        }

        holder.topLayout?.setOnClickListener { onProductClick(response) }

        Glide.with(context)
            .load(response.icon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .placeholder(R.drawable.no_image)
            .into(holder.icon!!)

        return convertView
    }

    private fun onProductClick(response: SalesListResponse.Service) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra(ExtrasConstants.SERVICE_ID, response.id)
        context.startActivity(intent)
    }

    class ItemHolder {
        var name: TextView? = null
        var price: TextView? = null
        var rating: RatingBar? = null
        var offPrice: TextView? = null
        var offPercentage: TextView? = null
        var icon: ImageView? = null
        var topLayout: LinearLayout? = null
    }

}