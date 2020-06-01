package com.example.ecommerce.viewmodels.home


data class Body(
        val banners: List<Banners>,
        val services: List<Services>,
        val subcat: List<Subcat>,
        val offers: List<Offers>,
        val trending: List<Trending>,
        val cartCategoryType: String,
        val currency: String

)