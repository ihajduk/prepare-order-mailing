package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

data class CouponLineDto(
    val id: String,
    val code: String,
    val discount: String,
    val discountTax: String,
    val metadata: MetaDataDto
)