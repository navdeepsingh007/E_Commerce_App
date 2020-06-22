package com.example.ecommerce.model.homenew

data class HomeResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val categories: ArrayList<Category>? = arrayListOf(),
        val recommended: ArrayList<Recommended>? = arrayListOf(),
        val sales: ArrayList<Sale>? = arrayListOf(),
        val currency: String ?= ""
    )

    data class Category(
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class Recommended(
        val category: CategoryRecommended? = CategoryRecommended(),
        val categoryId: String? = "",
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val originalPrice: String? = "",
        val price: String? = "",
        val rating: Double? = 0.0,
        val thumbnail: String? = ""
    )

    data class Sale(
        val cart: String? = "",
        val favourite: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val originalPrice: String? = "",
        val price: String? = "",
        val productSpecifications: ArrayList<ProductSpecification>? = arrayListOf(),
        val rating: Double? = 0.0,
        val thumbnail: String? = "",
        val validUpto: String? = ""
    )

    data class CategoryRecommended(
        val id: String? = "",
        val name: String? = ""
    )

    data class ProductSpecification(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: ArrayList<StockQunatity>? = arrayListOf()
    )

    data class StockQunatity(
        val id: Int? = 0,
        val size: String? = "",
        val stock: String? = ""
    )
}