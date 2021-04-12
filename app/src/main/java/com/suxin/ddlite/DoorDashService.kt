package com.suxin.ddlite

import com.suxin.ddlite.model.RestaurantDetailModel
import com.suxin.ddlite.model.RestaurantListResponse
import com.suxin.ddlite.model.RestaurantModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DoorDashService {
    @GET("/v1/store_feed/")
    fun getRestaurantListData(
        @Query("lat") latitude: String,
        @Query("lng") longitude: String,
        @Query("offset") offset: String,
        @Query("limit") limit: String
    ): Observable<RestaurantListResponse>

    @GET("/v2/restaurant/{restaurant_id}")
    fun getRestaurantDetailData(
        @Path("restaurant_id") restaurantId: String
    ): Observable<RestaurantDetailModel>
}