package com.example.fleet.model.vehicle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServicesListResponse {
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
        @SerializedName("service_id")
        @Expose
        var service_id : Int? = null
        @SerializedName("service_date")
        @Expose
        var service_date : String? = null
        @SerializedName("odometer")
        @Expose
        var odometer : String? = null
        @SerializedName("vehicle_id")
        @Expose
        var vehicle_id : String? = null
        @SerializedName("vendor_id")
        @Expose
        var vendor_id : String? = null
        @SerializedName("comments")
        @Expose
        var comments : String? = null
        @SerializedName("labor_price")
        @Expose
        var labor_price : String? = null
        @SerializedName("parts_price")
        @Expose
        var parts_price : String? = null
        @SerializedName("tax")
        @Expose
        var tax : String? = null
        @SerializedName("total_price")
        @Expose
        var total_price : String? = null
        @SerializedName("invoice_number")
        @Expose
        var invoice_number : String? = null
        @SerializedName("invoice_image")
        @Expose
        var invoice_image : String? = null
        @SerializedName("created_at")
        @Expose
        var created_at : String? = null
        @SerializedName("updated_at")
        @Expose
        var updated_at : String? = null
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
        @SerializedName("service_for")
        @Expose
        var service_for : Int? = null


    }
}
