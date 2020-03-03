package com.example.fleet.model.vendor

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VendorListResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("data")
    @Expose
    var data : ArrayList<Data>? = null

    inner class Data {
        @SerializedName("vendor_id")
        @Expose
        var vendor_id : Int? = null
        @SerializedName("vendor_name")
        @Expose
        var vendor_name : String? = null
        @SerializedName("vendor_type")
        @Expose
        var vendor_type : String? = null
        @SerializedName("vcontact_fname")
        @Expose
        var vcontact_fname : String? = null
        @SerializedName("vcontact_lname")
        @Expose
        var vcontact_lname : String? = null
        @SerializedName("vcontact_no")
        @Expose
        var vcontact_no : String? = null
        @SerializedName("vendor_email")
        @Expose
        var vendor_email : String? = null
        @SerializedName("vendor_website")
        @Expose
        var vendor_website : String? = null
        @SerializedName("vendor_address")
        @Expose
        var vendor_address : String? = null
        @SerializedName("createdAt")
        @Expose
        var createdAt : String? = null
        @SerializedName("updatedAt")
        @Expose
        var updatedAt : String? = null

    }
}
