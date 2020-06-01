package com.example.ecommerce.model.fav

data class AddRemoveFavResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val companyId: String? = "",
        val createdAt: String? = "",
        val id: String? = "",
        val serviceId: String? = "",
        val updatedAt: String? = "",
        val userId: String? = ""
    )
}