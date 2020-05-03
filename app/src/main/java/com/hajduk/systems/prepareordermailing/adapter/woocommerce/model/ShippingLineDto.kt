package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class ShippingLineDto(
    val id: Int,
    val metaData: List<MetaDataDto>,
    val methodId: String,
    val methodTitle: String,
    val taxes: List<TaxDto>,
    val total: BigDecimal,
    val totalTax: BigDecimal
)