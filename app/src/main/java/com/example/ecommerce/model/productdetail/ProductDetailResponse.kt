package com.example.ecommerce.model.productdetail

data class ProductDetailResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val cart: String? = "",
        val carts: ArrayList<Any>? = arrayListOf(),
        val category: Category? = Category(),
        val company: Company? = Company(),
        val description: String? = "",
        val favourite: String? = "",
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: String? = "",
        val originalPrice: String? = "",
        val price: Int? = 0,
        val productSpecifications: ArrayList<Any>? = arrayListOf(),
        val rating: Int? = 0,
        val thumbnail: String? = "",
        val type: String? = ""
    )

    data class Category(
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class Company(
        val companyName: String? = "",
        val document: Document? = Document(),
        val id: String? = "",
        val logo1: String? = ""
    )

    data class Document(
        val aboutus: String? = ""
    )
}