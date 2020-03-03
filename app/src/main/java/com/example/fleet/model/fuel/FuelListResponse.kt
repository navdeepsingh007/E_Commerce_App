package com.example.fleet.model.fuel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FuelListResponse {
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
        @SerializedName("fuel_id")
        @Expose
        var fuel_id : Int? = null
        @SerializedName("vehicle_id")
        @Expose
        var vehicle_id : Int? = null
        @SerializedName("vendor_id")
        @Expose
        var vendor_id : Int? = null
        @SerializedName("invoice_number")
        @Expose
        var invoice_number : String? = null
        @SerializedName("invoice_Image")
        @Expose
        var invoice_Image : String? = null
        @SerializedName("entry_date")
        @Expose
        var entry_date : String? = null
        @SerializedName("odometer")
        @Expose
        var odometer : String? = null
        @SerializedName("partial_fuelup")
        @Expose
        var partial_fuelup : String? = null
        @SerializedName("price")
        @Expose
        var price : String? = null
        @SerializedName("createdAt")
        @Expose
        var createdAt : String? = null
        @SerializedName("updatedAt")
        @Expose
        var updatedAt : String? = null
        @SerializedName("vendor_name")
        @Expose
        var vendor_name : String? = null
        @SerializedName("vendor_Type")
        @Expose
        var vendor_Type : String? = null
        @SerializedName("vendor_email")
        @Expose
        var vendor_email : String? = null
        @SerializedName("vehicle_name")
        @Expose
        var vehicle_name : String? = null
        @SerializedName("vehicle_type")
        @Expose
        var vehicle_type : String? = null
        @SerializedName("vehicle_model")
        @Expose
        var vehicle_model : String? = null
        @SerializedName("vehicle_group")
        @Expose
        var vehicle_group : String? = null
        @SerializedName("fuel_type")
        @Expose
        var fuel_type : String? = null
        @SerializedName("fuel_measure")
        @Expose
        var fuel_measure : String? = null
    }
}
