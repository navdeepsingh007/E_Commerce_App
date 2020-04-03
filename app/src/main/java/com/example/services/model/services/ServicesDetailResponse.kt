package com.example.services.model.services

import com.example.services.model.LoginResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServicesDetailResponse {
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("body")
    @Expose
    var data : Body? = null

    inner class Body {
        @SerializedName("id")
        @Expose
        var id : String? = null
		@SerializedName("currency")
		@Expose
		var currency : String? = null
        @SerializedName("name")
        @Expose
        var name : String? = null
        @SerializedName("description")
        @Expose
        var description : String? = null
		@SerializedName("price")
		@Expose
		var price : String? = null
		@SerializedName("icon")
		@Expose
		var icon : String? = null
		@SerializedName("thumbnail")
		@Expose
		var thumbnail : String? = null
		@SerializedName("type")
		@Expose
		var type : String? = null
		@SerializedName("rating")
		@Expose
		var rating : Int? = null
		@SerializedName("favorite")
		@Expose
		var favorite : String? = null
		@SerializedName("cart")
		@Expose
		var cart : String? = null
	}
}