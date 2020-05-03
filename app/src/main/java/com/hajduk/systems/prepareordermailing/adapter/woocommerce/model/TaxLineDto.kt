package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class TaxLineDto(
    val id: Int,
    val rateCode: String,
    val rateId: Int,
    val label: String,
    val compound: Boolean,
    val taxTotal: BigDecimal,
    val shippingTaxTotal: BigDecimal,
    val metaData: List<MetaDataDto>
)