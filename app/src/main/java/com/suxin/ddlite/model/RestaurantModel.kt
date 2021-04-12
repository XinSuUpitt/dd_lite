package com.suxin.ddlite.model

class RestaurantModel(val id: String,
                      val name: String,
                      val description: String,
                      val cover_img_url: String,
                      val header_img_url: String,
                      val location: RestaurantLocation? = null,
                      val delivery_fee: Double? = null,
                      val num_ratings: Double? = null,
                      val average_rating: Double? = null,
                      val next_close_time: String? = null,
                      val next_open_time: String? = null,
                      val distance_from_consumer: Double? = null)