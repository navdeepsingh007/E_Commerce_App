package com.example.services.model.orders

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrdersListResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: ArrayList<Body>? = null


    inner class Body {
        @SerializedName("orderServices")
        @Expose
        var orderServices:  ArrayList<OrderServices>? = null

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("addressId")
        @Expose
        var addressId: String? = null
        @SerializedName("progressStatus")
        @Expose
        var progressStatus: String? = null
        @SerializedName("created_at")
        @Expose
        var created_at: String? = null
        @SerializedName("TotalPayment")
        @Expose
        var TotalPayment: String? = null
    }


    inner class OrderServices {
        @SerializedName("orderId")
        @Expose
        var orderId: String? = null
        @SerializedName("serviceId")
        @Expose
        var serviceId: String? = null
        @SerializedName("date")
        @Expose
        var date: String? = null
        @SerializedName("timeslotId")
        @Expose
        var timeslotId: String? = null
        @SerializedName("price")
        @Expose
        var price: String? = null
        @SerializedName("quantity")
        @Expose
        var quantity: String? = null
        @SerializedName("ServiceName")
        @Expose
        var ServiceName: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("TimeSlot")
        @Expose
        var TimeSlot: String? = null

    }
}
