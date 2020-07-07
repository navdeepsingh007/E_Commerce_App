package com.example.ecommerce.model.orders

import com.example.ecommerce.model.fav.FavouriteListResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrdersDetailNewResponse {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("body")
    @Expose
    var data: Body? = null

    inner class Body {
        @SerializedName("orderNo")
        @Expose
        var orderNo: String? = null
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("progressStatus")
        @Expose
        var progressStatus: String? = null
        @SerializedName("trackStatus")
        @Expose
        var trackStatus: String? = null
        @SerializedName("cancellationReason")
        @Expose
        var cancellationReason: String? = null
        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null
        @SerializedName("updatedAt")
        @Expose
        var updatedAt: String? = null
        @SerializedName("addressId")
        @Expose
        var addressId: String? = null
        @SerializedName("serviceDateTime")
        @Expose
        var serviceDateTime: String? = null
        @SerializedName("orderPrice")
        @Expose
        var orderPrice: String? = null
        @SerializedName("serviceCharges")
        @Expose
        var serviceCharges: String? = null
        @SerializedName("offerPrice")
        @Expose
        var offerPrice: String? = null
        @SerializedName("promoCode")
        @Expose
        var promoCode: String? = null
        @SerializedName("totalOrderPrice")
        @Expose
        var totalOrderPrice: String? = null
        @SerializedName("userId")
        @Expose
        var userId: String? = null
        @SerializedName("companyId")
        @Expose
        var companyId: String? = null
        @SerializedName("suborders")
        @Expose
        var suborders: ArrayList<Suborders>? = null
        @SerializedName("assignedEmployees")
        @Expose
        var assignedEmployees: ArrayList<AssignedEmployees>? = null
    }
    inner class Suborders {
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("serviceId")
        @Expose
        var serviceId: String? = null
        @SerializedName("quantity")
        @Expose
        var quantity: String? = null
        @SerializedName("color")
        @Expose
        var color: String? = null
        @SerializedName("size")
        @Expose
        var size: String? = null
        @SerializedName("serviceCharges")
        @Expose
        var serviceCharges: Int? = null
        @SerializedName("progressStatus")
        @Expose
        var progressStatus: Int? = null
        @SerializedName("cancellationReason")
        @Expose
        var cancellationReason: String? = null
        @SerializedName("otherReason")
        @Expose
        var otherReason: String? = null
        @SerializedName("product")
        @Expose
        var product: Product? = null
        @SerializedName("cancelReason")
        @Expose
        var cancelReason: String? = null
        @SerializedName("address")
        @Expose
        var address: Address? = null
        @SerializedName("company")
        @Expose
        var company: Company? = null

    }

    inner class AssignedEmployees {
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("jobStatus")
        @Expose
        var jobStatus: String? = null
        @SerializedName("employee")
        @Expose
        var employee: AssignedEmployees? = null

    }

    inner class Product {
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("thumbnail")
        @Expose
        var thumbnail: String? = null
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("price")
        @Expose
        var price: String? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("duration")
        @Expose
        var duration: String? = null
        @SerializedName("offer")
        @Expose
        var offer: Int? = null
        @SerializedName("offerName")
        @Expose
        var offerName: String? = null
        @SerializedName("brandName")
        @Expose
        var brandName: String? = null
        @SerializedName("productSpecifications")
        @Expose
        var productSpecifications: ArrayList<ProductSpecification>? = null
    }

    inner class ProductSpecification {
        @SerializedName("productImages")
        @Expose
        var productImages: ArrayList<String>? = null
        @SerializedName("stockQunatity")
        @Expose
        var stockQunatity: ArrayList<StockQunatity>? = null
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("productColor")
        @Expose
        var productColor: String? = null
    }

    inner class StockQunatity {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("size")
        @Expose
        var size: String? = null
        @SerializedName("stock")
        @Expose
        var stock: String? = null
        @SerializedName("price")
        @Expose
        var price: String? = null
        @SerializedName("originalPrice")
        @Expose
        var originalPrice: String? = null

    }

    inner class Address {
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("addressName")
        @Expose
        var addressName: String? = null
        @SerializedName("addressType")
        @Expose
        var addressType: String? = null
        @SerializedName("houseNo")
        @Expose
        var houseNo: String? = null
        @SerializedName("latitude")
        @Expose
        var latitude: String? = null
        @SerializedName("longitude")
        @Expose
        var longitude: String? = null
        @SerializedName("town")
        @Expose
        var town: String? = null
        @SerializedName("landmark")
        @Expose
        var landmark: String? = null
        @SerializedName("city")
        @Expose
        var city: String? = null
    }

    inner class Company {
        @SerializedName("logo1")
        @Expose
        var logo1: String? = null
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("companyName")
        @Expose
        var companyName: String? = null
        @SerializedName("document")
        @Expose
        var document: Document? = null
    }

    inner class Document {
        @SerializedName("aboutus")
        @Expose
        var aboutus: String? = null
    }
}
