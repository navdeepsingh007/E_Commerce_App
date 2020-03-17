package com.example.fleet.model.vehicle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VehicleListResponse {
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
        @SerializedName("vehicle_id")
        @Expose
        var vehicle_id : Int? = null
        @SerializedName("user_id")
        @Expose
        var user_id : Int? = null
        @SerializedName("vehicle_name")
        @Expose
        var vehicle_name : String? = null
        @SerializedName("vehicle_type")
        @Expose
        var vehicle_type : String? = null
        @SerializedName("vehicle_model")
        @Expose
        var vehicle_model : String? = null
        @SerializedName("vehicle_manufacture")
        @Expose
        var vehicle_manufacture : String? = null
        @SerializedName("color")
        @Expose
        var color : String? = null
        @SerializedName("vehicle_image")
        @Expose
        var vehicle_image : String? = null
        @SerializedName("vehicle_group")
        @Expose
        var vehicle_group : String? = null
        @SerializedName("reg_num")
        @Expose
        var reg_num : String? = null
        @SerializedName("engine_num")
        @Expose
        var engine_num : String? = null
        @SerializedName("chassis_num")
        @Expose
        var chassis_num : Int? = null
        @SerializedName("fuel_type")
        @Expose
        var fuel_type : String? = null
        @SerializedName("fuel_measure")
        @Expose
        var fuel_measure : String? = null
        @SerializedName("track")
        @Expose
        var track : String? = null
        @SerializedName("meter")
        @Expose
        var meter : String? = null
        @SerializedName("assigned_driver")
        @Expose
        var assigned_driver : String? = null
        @SerializedName("created_at")
        @Expose
        var created_at : String? = null
        @SerializedName("updated_at")
        @Expose
        var updated_at : String? = null
    }
}
