package com.example.ecommerce.views.product

import android.graphics.Typeface
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.product.FavouriteProductsGridListAdapter
import com.example.ecommerce.databinding.ActivityFlashSaleBinding
import com.example.ecommerce.utils.BaseActivity
import com.example.ecommerce.adapters.product.ProductsGridListAdapter
import com.example.ecommerce.databinding.ActivityFavouriteProductsBinding
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.viewmodels.favorite.FavoriteViewModel

class FavouriteProductsActivity: BaseActivity() {
    private lateinit var binding: ActivityFavouriteProductsBinding
    lateinit var favoriteViewModel: FavoriteViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_favourite_products
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityFavouriteProductsBinding
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        initToolbar()
        initFavouriteProductsGrid()

        initFavoutiteProductsObserver()
    }

    private fun initToolbar() {
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.favourite_product)
        val headingView = binding.commonToolBar.imgToolbarText
        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun initFavouriteProductsGrid() {
        val favouriteList = ArrayList<com.example.ecommerce.viewmodels.home.Services>()
        val adapter = FavouriteProductsGridListAdapter(
            this,
            favouriteList,
            this
        )
        binding.gvFavoutiteProducts.adapter = adapter
    }

    private fun initFavoutiteProductsObserver() {
        favoriteViewModel.getFavListRes().observe(this,
            Observer<FavouriteListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                        if (response.code == 200) {

                        } else {

                        }

/*                       when {
                            response.code == 200 -> {
                            cartList.addAll(response.body!!)
                            favoriteBinding.rvFavorite.visibility = View.VISIBLE
                            favoriteBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                            favoriteBinding.rvFavorite.visibility = View.GONE
                            favoriteBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                        }*/

                }
            })
    }
}