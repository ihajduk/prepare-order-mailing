package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

data class CustomerDto(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String
)