package com.suxin.ddlite

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class DoorDashRetrofitManager {

    companion object {
        private val TAG = DoorDashRetrofitManager::class.java.simpleName
        private const val SERVER_URL = "https://api.doordash.com"
    }

    private var doorDashService: DoorDashService? = null

    fun getDoorDashService(): DoorDashService? {
        if (doorDashService == null) {
            synchronized(DoorDashService::class.java) {
                if (doorDashService == null) {
                    val restAdapter: Retrofit = Retrofit.Builder()
                        .baseUrl(SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                    doorDashService = restAdapter.create(DoorDashService::class.java)
                }
            }
        }
        return doorDashService
    }
}