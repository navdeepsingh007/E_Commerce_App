package com.example.services.model.services

data class Body (

	val id : String,
	val name : String,
	val description : String,
	val price : Int,
	val icon : String,
	val thumbnail : String,
	val type : String,
	val rating : Int,
	var favorite : String
)