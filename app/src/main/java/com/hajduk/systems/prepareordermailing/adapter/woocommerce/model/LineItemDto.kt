package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class LineItemDto(
    val id: Int,
    val name: String,
    val productId: Int,
    val variationId: Int,
    val quantity: Int,
    val taxClass: String,
    val subtotal: BigDecimal,
    val subtotalTax: BigDecimal,
    val total: BigDecimal,
    val totalTax: BigDecimal,
    val taxes: List<TaxDto>,
    val metaData: List<MetaDataDto>,
    val price: BigDecimal,
    val sku: String
)