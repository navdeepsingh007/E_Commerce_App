package com.example.ecommerce.model.fav

data class FavouriteListResponse(
    val body: ArrayList<Body>? = arrayListOf(),
    val code: Int? = 0,
    val message: String? = ""
) {
    data class Body(
        val cartCategoryCompany: String? = "",
        val cartCategoryType: String? = "",
        val companyId: String? = "",
        val createdAt: Int? = 0,
        val id: String? = "",
        val product: Product? = Product(),
        val serviceId: String? = "",
        val updatedAt: Int? = 0,
        val userId: String? = ""
    )

    data class Product(
        val category: Category? = Category(),
        val createdAt: Int? = 0,
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val originalPrice: String? = "",
        val price: String? = "",
        val productSpecifications: ArrayList<Any>? = arrayListOf(),
        val status: Int? = 0,
        val thumbnail: String? = ""
    )

    data class Category(
        val companyId: String? = "",
        val id: String? = "",
        val name: String? = ""
    )
}