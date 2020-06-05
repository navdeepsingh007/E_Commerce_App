package com.example.ecommerce.model.sale

data class SalesListInput(
    var brandArray: ArrayList<String> = arrayListOf(),
    var catArray: ArrayList<String> = arrayListOf(),
    var priceRange: PriceRange = PriceRange(),
    var orderByInfo: OrderByInfo = OrderByInfo()
) {

    data class PriceRange(
        var start: String = "",
        var end: String = ""
    )

    data class OrderByInfo(
        var orderby: String = "",
        var orderType: String = ""
    )
}