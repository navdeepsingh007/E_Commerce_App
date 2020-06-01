package com.example.ecommerce.viewmodels.promocode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.address.AddressResponse
import com.example.ecommerce.model.promocode.ApplyPromoCodeResponse
import com.example.ecommerce.model.promocode.PromoCodeListResponse
import com.example.ecommerce.repositories.promocode.PromoCodeRepository
import com.example.ecommerce.viewmodels.BaseViewModel
import com.google.gson.JsonObject

class PromoCodeViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var addressDetail = MutableLiveData<AddressResponse>()
    private var deleteAddress = MutableLiveData<CommonModel>()

    private var promocodeList = MutableLiveData<PromoCodeListResponse>()
    private var applyPromoCode = MutableLiveData<ApplyPromoCodeResponse>()
    private var removePromoCode = MutableLiveData<CommonModel>()

    private var promoCodeRepository = PromoCodeRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            promocodeList = promoCodeRepository.promoCodeList()
            applyPromoCode = promoCodeRepository.applyPromoCode(null)
            removePromoCode = promoCodeRepository.removePromoCode(null)
        }

    }

    fun getPromoListRes(): LiveData<PromoCodeListResponse> {
        return promocodeList
    }

    fun getApplyPromoRes(): LiveData<ApplyPromoCodeResponse> {
        return applyPromoCode
    }

    fun getRemovePromoRes(): LiveData<CommonModel> {
        return removePromoCode
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

    fun removePromoCode(code: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            var codeObject = JsonObject()
            codeObject.addProperty(
                    "promoCode", code
            )
            removePromoCode = promoCodeRepository.removePromoCode(codeObject)
            mIsUpdating.postValue(true)
        }
    }

    fun applyPromoCode(code: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            var codeObject = JsonObject()
            codeObject.addProperty(
                    "promoCode", code
            )
            applyPromoCode = promoCodeRepository.applyPromoCode(codeObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getPromoList() {
        if (UtilsFunctions.isNetworkConnected()) {
            promocodeList = promoCodeRepository.promoCodeList()
            mIsUpdating.postValue(true)
        }

    }

}