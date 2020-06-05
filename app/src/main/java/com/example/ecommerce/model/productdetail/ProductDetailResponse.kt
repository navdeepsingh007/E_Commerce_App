package com.example.ecommerce.model.productdetail

data class ProductDetailResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {
    data class Body(
        val cart: Any? = Any(),
        val category: Category? = Category(),
        val categoryId: String? = "",
        val company: Company? = Company(),
        val companyId: String? = "",
        val currency: String? = "",
        val ratingCount: Int? = 0,
        val description: String? = "",
        val estimatDelivery: String? = "",
        val favourite: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val offerName: String? = "",
        val originalPrice: String? = "",
        val price: String? = "",
        val productSpecifications: ArrayList<ProductSpecification>? = arrayListOf(),
        val rating: Int? = 0,
        val ratings: Ratings? = Ratings(),
        val recommended: ArrayList<Recommended>? = arrayListOf(),
        val shipment: String? = "",
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
        val address1LatLong: String? = "",
        val companyName: String? = "",
        val document: Document? = Document(),
        val id: String? = "",
        val logo1: String? = ""
    )

    data class ProductSpecification(
        val id: String? = "",
        val productColor: String? = "",
        val productImages: ArrayList<String>? = arrayListOf(),
        val stockQunatity: ArrayList<StockQunatity>? = arrayListOf()
    )

    data class Ratings(
        val createdAt: String? = "",
        val rating: String? = "",
        val review: String? = "",
        val reviewImages: ArrayList<String>? = arrayListOf(),
        val user: User? = User()
    )

    data class Recommended(
        val category: CategoryRecommended? = CategoryRecommended(),
        val categoryId: String? = "",
        val description: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val offer: Int? = 0,
        val offerName: String? = "",
        val originalPrice: String? = "",
        val price: String? = "",
        val rating: Int? = 0,
        val thumbnail: String? = ""
    )

    data class Document(
        val aboutus: String? = ""
    )

    data class StockQunatity(
        val id: Int? = 0,
        val size: String? = "",
        val stock: String? = ""
    )

    data class User(
        val firstName: String? = "",
        val id: String? = "",
        val image: String? = "",
        val lastName: String? = ""
    )

    data class CategoryRecommended(
        val id: String? = "",
        val name: String? = ""
    )
}