package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class TaxDto(
    val id: Int,
    val total: BigDecimal,
    val subtotal: BigDecimal
)