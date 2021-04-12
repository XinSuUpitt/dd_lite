package com.suxin.ddlite.model

class RestaurantListResponse(val num_results: Int,
                             val is_first_time_user: Boolean,
                             val sort_order: String,
                             val next_offset: Double,
                             val show_list_as_pickup: Boolean,
                             val stores: List<RestaurantModel>)