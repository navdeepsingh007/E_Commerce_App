package com.example.ecommerce.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressListResponse {
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
        @SerializedName("houseNo")
        @Expose
        var houseNo : String? = null
        @SerializedName("default")
        @Expose
        var default : String? = null
        @SerializedName("addressType")
        @Expose
        var addressType : String? = null
        @SerializedName("town")
        @Expose
        var town : String? = null
        @SerializedName("landmark")
        @Expose
        var landmark : String? = null
        @SerializedName("status")
        @Expose
        var status : String? = null
        @SerializedName("addressName")
        @Expose
        var addressName : String? = null
        @SerializedName("latitude")
        @Expose
        var latitude : String? = null
        @SerializedName("longitude")
        @Expose
        var longitude : String? = null
        @SerializedName("city")
        @Expose
        var city : String? = null
        @SerializedName("companyId")
        @Expose
        var companyId : String? = null
        @SerializedName("userId")
        @Expose
        var userId : String? = null
        @SerializedName("createdAt")
        @Expose
        var createdAt : String? = null
        @SerializedName("updatedAt")
        @Expose
        var updatedAt : String? = null

    }
}
