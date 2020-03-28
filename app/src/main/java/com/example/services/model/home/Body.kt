package com.example.services.viewmodels.home

data class Body(
    val banners: List<Banner>,
    val services: List<Service>,
    val trendingServices: List<TrendingService>
)