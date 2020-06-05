package com.example.ecommerce.views.product

import android.app.Dialog
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
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.databinding.ActivityFavouriteProductsBinding
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.utils.DialogClass
import com.example.ecommerce.utils.DialogssInterface
import com.example.ecommerce.viewmodels.favorite.FavoriteViewModel

class FavouriteProductsActivity : BaseActivity(), FavDeleteListener, DialogssInterface {
    private lateinit var binding: ActivityFavouriteProductsBinding
    lateinit var favoriteViewModel: FavoriteViewModel
    private val DELETE_FAV_PRODUCT = "Delete from Favourites"
    private var deleteProductId = ""
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    private var adapter: FavouriteProductsGridListAdapter? = null
    private val favProductsList = ArrayList<FavouriteListResponse.Body>()

    override fun getLayoutId(): Int {
        return R.layout.activity_favourite_products
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityFavouriteProductsBinding
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        startProgressDialog()
        initToolbar()
        initFavoutiteProductsObserver()
        removeFavouriteObserver()

        setFavouriteProductsGrid()
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

    private fun initFavoutiteProductsObserver() {
        favoriteViewModel.getFavListRes().observe(this,
            Observer<FavouriteListResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    if (response.code == 200) {
                        if (response.body != null) {

                            favProductsList.clear()
                            favProductsList.addAll(response.body)
                            setFavouriteProductsGrid()

                            val favAdapter = adapter
//                            if (favAdapter != null) {
//                                favAdapter?.productsList?.clear()
//                                favAdapter?.productsList?.addAll(response.body)
//                                favAdapter?.notifyDataSetChanged()
//                            }


//                            favAdapter?.updateGridList(response.body)
                        }
                    } else {
                        UtilsFunctions.showToastError(message)
                    }
                }
            })
    }

    private fun setFavouriteProductsGrid() {
        adapter = FavouriteProductsGridListAdapter(
            this,
            favProductsList,
            this
        )
        binding.gvFavoutiteProducts.adapter = adapter
        if (favProductsList.size > 0) {
            binding.gvFavoutiteProducts.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
        } else {
            binding.gvFavoutiteProducts.visibility = View.GONE
            binding.tvNoRecord.visibility = View.VISIBLE
        }
    }


    private fun removeFavouriteObserver() {
        favoriteViewModel.removefavResponse().observe(this,
            Observer<AddRemoveFavResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            UtilsFunctions.showToastSuccess(message)

//                            val favAdapter = adapter
//                            if (favAdapter != null) {
//                                favAdapter.notifyDataSetChanged()
//                            }

                            favoriteViewModel.getFavList()
                        }
                        else -> message?.let {
                            UtilsFunctions.showToastError(it)
                        }
                    }
                }
            })
    }

    override fun onProductDelete(favId: String) {
        deleteProductId = favId
        confirmationDialog = mDialogClass.setDefaultDialog(
            this,
            this,
            DELETE_FAV_PRODUCT,
            getString(R.string.warning_delete_fav)
        )
        confirmationDialog?.show()
    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        when (mKey) {
            DELETE_FAV_PRODUCT -> {
                startProgressDialog()
                confirmationDialog?.dismiss()
                if (UtilsFunctions.isNetworkConnected()) {
                    startProgressDialog()

                    favoriteViewModel.removeFav(deleteProductId)
                }
            }
        }
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        when (mKey) {
            DELETE_FAV_PRODUCT -> confirmationDialog?.dismiss()
        }
    }
}

interface FavDeleteListener {
    fun onProductDelete(favId: String)
}