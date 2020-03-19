package com.example.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("data")
    @Expose
    var data : Data? = null

    inner class Data {

        @SerializedName("driver_id")
        @Expose
        var driver_id : Int? = null

        @SerializedName("user_id")
        @Expose
        var userId : Int? = null
        @SerializedName("user_type")
        @Expose
        var userType : String? = null
        @SerializedName("profile_image")
        @Expose
        var profile_image : String? = null
        @SerializedName("session_token")
        @Expose
        var session_token : String? = null
        @SerializedName("status")
        @Expose
        var status : String? = null
        @SerializedName("availablity")
        @Expose
        var availablity : String? = null

        @SerializedName("country_code")
        @Expose
        var countryCode : String? = null
        @SerializedName("email")
        @Expose
        var email : String? = null
        @SerializedName("password")
        @Expose
        var password : String? = null
        @SerializedName("phone_number")
        @Expose
        var phoneNumber : String? = null
        @SerializedName("first_name")
        @Expose
        var firstName : String? = null
        @SerializedName("last_name")
        @Expose
        var lastName : String? = null
        @SerializedName("gender")
        @Expose
        var gender : String? = null
        @SerializedName("address")
        @Expose
        var address : String? = null
        @SerializedName("profile_pic")
        @Expose
        var profilePic : String? = null
        @SerializedName("device_type")
        @Expose
        var deviceType : String? = null
        @SerializedName("device_id")
        @Expose
        var deviceId : String? = null
        @SerializedName("jwt_token")
        @Expose
        var jwtToken : String? = null
        @SerializedName("notify_id")
        @Expose
        var notifyId : String? = null
        @SerializedName("created_at")
        @Expose
        var createdAt : String? = null
        @SerializedName("updated_at")
        @Expose
        var updatedAt : String? = null

    }
}
