package com.suxin.ddlite.model

class Address(
    val id: String,
    val city: String,
    val state: String,
    val street: String,
    val country: String,
    val zip_code: String,
    val printable_address: String,
    val lat: Double,
    val lng: Double
)