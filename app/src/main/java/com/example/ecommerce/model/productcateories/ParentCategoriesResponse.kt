package com.example.ecommerce.model.productcateories

data class ParentCategoriesResponse(
    val body: Body? = Body(),
    val code: Int? = 0,
    val message: String? = ""
) {

    data class Body(
        val aboutUsLink: String? = "",
        val banners: ArrayList<Any>? = arrayListOf(),
        val cartCategoryCompany: String? = "",
        val cartCategoryType: String? = "",
        val currency: String? = "",
        val privacyLink: String? = "",
        val services: ArrayList<Service>? = arrayListOf(),
        val termsLink: String? = ""
    )

    data class Service(
        val colorCode: String? = "",
        val icon: String? = "",
        val id: String? = "",
        val name: String? = "",
        val thumbnail: String? = ""
    )
}