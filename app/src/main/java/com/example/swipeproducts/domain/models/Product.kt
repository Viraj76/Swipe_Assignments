package com.example.swipeproducts.domain.models

data class ProductsDTOItem(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)