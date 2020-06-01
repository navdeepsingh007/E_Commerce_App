package com.example.ecommerce.model.promocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PromoCodeListResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("body")
    @Expose
    var data : ArrayList<Body>? = null

    inner class Body {
        @SerializedName("id")
        @Expose
        var id : String? = null
        @SerializedName("name")
        @Expose
        var name : String? = null
        @SerializedName("code")
        @Expose
        var code : String? = null
        @SerializedName("discount")
        @Expose
        var discount : String? = null
        @SerializedName("description")
        @Expose
        var description : String? = null
        @SerializedName("icon")
        @Expose
        var icon : String? = null
    }
}
