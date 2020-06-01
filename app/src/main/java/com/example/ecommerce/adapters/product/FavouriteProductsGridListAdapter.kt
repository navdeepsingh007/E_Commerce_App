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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerce.R
import com.example.ecommerce.views.product.ProductDetailsActivity

class FavouriteProductsGridListAdapter(
    val context: FragmentActivity,
    productsList: ArrayList<com.example.ecommerce.viewmodels.home.Services>,
    var activity: Context
) :
    ArrayAdapter<FavouriteProductsGridListAdapter.ItemHolder>(activity, R.layout.category_item) {

    private var viewHolder: ItemHolder? = null
    private var categoriesList: ArrayList<com.example.ecommerce.viewmodels.home.Services>

    init {
        this.categoriesList = productsList
    }

    override fun getCount(): Int {
        return 6
//        return if (this.categoriesList != null) this.categoriesList.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ItemHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.row_favourite_products_grid, null)
            holder = ItemHolder()
            holder.icon = convertView!!.findViewById(R.id.ivProduct)
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

        holder.name!!.text = "Nike Air Max 270"
        holder.price!!.text = "$299.43"
        holder.offPrice!!.text = "$534.33"
        holder.offPercentage!!.text = "24% off"
        holder.rating!!.rating = 4f

        // Strike through off price
        holder.offPrice!!.setPaintFlags(holder.offPrice!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

//        holder.topLayout?.setOnClickListener { onProductClick() }

//        Glide.with(context)
//            .load("")
//            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
//            .placeholder(R.drawable.ic_category)
//            .into(holder.icon!!)

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
        var topLayout: LinearLayout? = null
    }

}