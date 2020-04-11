package com.example.services.model.services

data class ServicesListResponse (

		val code : Int,
		val message : String,
		val services : List<Services>,
		val subCategory : List<SubCategory>
)