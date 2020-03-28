package com.example.services.model.services

data class ServicesListResponse (

	val code : Int,
	val message : String,
	val body : List<Body>
)