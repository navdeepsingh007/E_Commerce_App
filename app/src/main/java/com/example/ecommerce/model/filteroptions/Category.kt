package com.example.ecommerce.model.filteroptions

data class Category(
    var optionList: MutableMap<String, Boolean> = mutableMapOf(),
    val items: ArrayList<CatOptions> = arrayListOf()
//    val id: String? = "",
//    val name: String? = "",
//    val isSelected: Boolean = false
) {
    data class CatOptions(
        val id: String? = "",
        val name: String? = "",
        val isSelected: Boolean = false
    )
}