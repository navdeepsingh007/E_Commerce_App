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
        val category: CategoryRecommended? = CategoryRecommended(),
        val categoryId: String? = "",
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: String? = "",
        val originalPrice: String? = "",
        val price: Int? = 0,
        val thumbnail: String? = ""
    )

    data class Sale(
        val cart: String? = "",
        val favourite: String? = "",
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val originalPrice: String? = "",
        val price: String? = "",
        val productSpecifications: ArrayList<ProductSpecification>? = arrayListOf(),
        val rating: Int? = 0,
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
        val stockQunatity: String? = ""
    )
}