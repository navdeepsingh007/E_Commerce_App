package com.example.services.model.services

data class Services (

	val id : String,
	var name : String,
	var description : String,
	var price : Int,
	var icon : String,
	var thumbnail : String,
	var type : String,
	var rating : Int,
	var favorite : String,
	var cart : String
)