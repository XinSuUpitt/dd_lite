package com.suxin.ddlite.view.main

import com.suxin.ddlite.model.RestaurantModel

interface RestaurantClickListener {
    fun onItemClicked(restaurant: RestaurantModel)
}