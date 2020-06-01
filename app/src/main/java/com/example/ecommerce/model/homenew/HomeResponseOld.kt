package com.example.ecommerce.model.homenew

data class HomeResponseOld(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {
    data class Body(
        val categories: ArrayList<Category>? = arrayListOf(),
        val recommended: ArrayList<Recommended>? = arrayListOf(),
        val sales: ArrayList<Sale>? = arrayListOf()
    )

    data class Category(
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class Recommended(
        val cart: String? = "",
        val favourite: String? = "",
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val productSpecifications: ArrayList<ProductSpecificationRecommended>? = arrayListOf(),
        val rating: Double? = 0.0,
        val thumbnail: String? = "",
        val validUpto: String? = ""
    )

    data class Sale(
        val cart: String? = "",
        val favourite: String? = "",
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val productSpecifications: ArrayList<ProductSpecificationSale>? = arrayListOf(),
        val rating: Double? = 0.0,
        val thumbnail: String? = "",
        val validUpto: String? = ""
    )

    data class ProductSpecificationRecommended(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: String? = ""
    )

    data class ProductSpecificationSale(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: String? = ""
    )
}