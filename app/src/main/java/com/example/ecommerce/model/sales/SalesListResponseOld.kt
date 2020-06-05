package com.example.ecommerce.model.sales

data class SalesListResponseOld(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val currency: String? = "",
        val filters: Filters? = Filters(),
        val services: ArrayList<Service>? = arrayListOf()
    )

    data class Filters(
        val categories: ArrayList<Category>? = arrayListOf()
    )

    data class Service(
        val category: CategoryService? = CategoryService(),
        val createdAt: Int? = 0,
        val favourite: String? = "",
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

    data class Category(
        val id: String? = "",
        val name: String? = ""
    )

    data class CategoryService(
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class ProductSpecification(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: String? = ""
    )
}