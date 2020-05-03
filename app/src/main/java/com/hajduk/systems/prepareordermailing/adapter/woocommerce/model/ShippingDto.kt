package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

data class ShippingDto(
    val firstName: String,
    val lastName: String,
    val company: String,
    val address1: String,
    val address2: String,
    val city: String,
    val state: String,
    val postcode: String,
    val country: String
)