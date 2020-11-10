package com.example.ecommerce.viewmodels.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.fav.AddRemoveFavResponse
import com.example.ecommerce.model.fav.FavouriteListResponse
import com.example.ecommerce.repositories.favorite.FavoriteRepository
import com.example.ecommerce.viewmodels.BaseViewModel

class FavoriteViewModel : BaseViewModel() {
    private var favList = MutableLiveData<FavouriteListResponse>()
    private var favoriteRepository = FavoriteRepository()
    private var favRes = MutableLiveData<AddRemoveFavResponse>()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            favList = favoriteRepository.favoriteList()
            favRes = favoriteRepository.removeFav("")
        }
    }

    fun getFavListRes(): LiveData<FavouriteListResponse> {
        return favList
    }


    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    /* fun addRemoveCart(mJsonObject : JsonObject) {
         if (UtilsFunctions.isNetworkConnected()) {
             addressDetail = cartRepository.addAddress(mJsonObject)
             mIsUpdating.postValue(true)
         }
     }*/

    fun getFavList() {
        if (UtilsFunctions.isNetworkConnected()) {
            favList = favoriteRepository.favoriteList()
            mIsUpdating.postValue(true)
        }
    }

    fun removefavResponse(): LiveData<AddRemoveFavResponse> {
        return favRes
    }

    fun removeFav(mJsonObject: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            favRes = favoriteRepository.removeFav(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }
}