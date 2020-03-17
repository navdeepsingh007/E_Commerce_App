package com.example.fleet.model.notificaitons

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationsListResponse {
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
        @SerializedName("notification_id")
        @Expose
        var notification_id : Int? = null
        @SerializedName("notification_title")
        @Expose
        var notification_title : String? = null
        @SerializedName("notification_description")
        @Expose
        var notification_description : String? = null
        @SerializedName("user_id")
        @Expose
        var user_id : String? = null
        @SerializedName("user_type")
        @Expose
        var user_type : String? = null
        @SerializedName("created_at")
        @Expose
        var created_at : String? = null
        @SerializedName("updated_at")
        @Expose
        var updated_at : String? = null
    }
}
