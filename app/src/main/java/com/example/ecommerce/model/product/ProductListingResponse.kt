package com.example.ecommerce.model.product


data class ProductListingResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val filters: Filters? = Filters(),
        val sales: ArrayList<Sale>? = arrayListOf(),
        val services: ArrayList<Service>? = arrayListOf()
    )

    data class Filters(
        val brands: ArrayList<Brand>? = arrayListOf(),
        val categories: ArrayList<CategoryFilter>? = arrayListOf()
    )

    data class Sale(
        val category: CategorySale? = CategorySale(),
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: String? = "",
        val productSpecifications: ArrayList<ProductSpecification>? = arrayListOf(),
        val thumbnail: String? = "",
        val validUpto: String? = ""
    )

    data class Service(
        val cart: String? = "",
        val category: CategoryService? = CategoryService(),
        val createdAt: Int? = 0,
        val description: String? = "",
        val favourite: String? = "",
        val favourites: ArrayList<Any>? = arrayListOf(),
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val originalPrice: String? = "",
        val price: String? = "",
        val productSpecifications: ArrayList<ProductSpecificationX>? = arrayListOf(),
        val rating: Int? = 0,
        val thumbnail: String? = "",
        val type: String? = "",
        val validUpto: String? = ""
    )

    data class Brand(
        val categories: ArrayList<Category>? = arrayListOf(),
        val companyName: String? = "",
        val id: String? = ""
    )

    data class CategoryFilter(
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class Category(
        val id: String? = ""
    )

    data class ProductSpecification(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: String? = ""
    )

    data class CategorySale(
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class CategoryService(
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )

    data class ProductSpecificationX(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: String? = ""
    )
}