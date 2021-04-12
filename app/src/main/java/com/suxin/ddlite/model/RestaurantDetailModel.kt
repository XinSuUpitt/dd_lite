package com.suxin.ddlite.model

class RestaurantDetailModel(
    val id: String,
    val name: String,
    val phone_number: String,
    val header_image_url: String?,
    val cover_image_url: String?,
    val address: Address,
    val average_rating: Double,
    val status: String
)