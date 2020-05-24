package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

class OrderDto(
    val id: Int,
    val customerId: Int,
    val total: BigDecimal,
    val lineItems: List<LineItemDto> = ArrayList()
)