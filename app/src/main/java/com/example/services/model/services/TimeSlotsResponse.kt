package com.example.services.model.services

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeSlotsResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("data")
    @Expose
    var data : ArrayList<Body>? = null

    inner class Body {

        @SerializedName("id")
        @Expose
        var id : String? = null
        @SerializedName("timing")
        @Expose
        var timing : String? = null
        @SerializedName("selected")
        @Expose
        var selected : String? = null


    }
}
