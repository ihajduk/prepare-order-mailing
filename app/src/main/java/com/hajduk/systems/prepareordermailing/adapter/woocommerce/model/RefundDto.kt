package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class RefundDto(
    val id: String,
    val reason: String,
    val total: BigDecimal
)