package com.hajduk.systems.prepareordermailing.domain

import java.math.BigDecimal

data class Order(
    val id: Int,
    val total: BigDecimal,
    val customerId: Int,
    val items: List<String>
)