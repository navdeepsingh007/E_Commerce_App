package com.example.ecommerce.model.orders

import com.example.ecommerce.model.fav.FavouriteListResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReasonListResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("body")
    @Expose
    var data: ArrayList<Body>? = null

    inner class Body {
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("reason")
        @Expose
        var reason: String? = null
        @SerializedName("status")
        @Expose
        var status: Int? = null
        @SerializedName("type")
        @Expose
        var type: Int? = null
        @SerializedName("companyId")
        @Expose
        var companyId: String? = null
        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null
    }
}
