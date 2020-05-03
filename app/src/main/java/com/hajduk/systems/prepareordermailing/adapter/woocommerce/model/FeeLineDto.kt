package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal

data class FeeLineDto(
    val id: Int,
    val name: String,
    val taxClass: String,
    val taxStatus: String,
    val total: BigDecimal,
    val totalTax: BigDecimal,
    val taxes: List<TaxDto>,
    val metaData: List<MetaDataDto>
)