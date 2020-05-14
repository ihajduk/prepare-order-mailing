package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.time.LocalDateTime

data class CustomerDto(
    val id: Int,
    val dateCreated: LocalDateTime,
    val dateCreatedGmt: LocalDateTime,
    val dateModified: LocalDateTime,
    val dateModifiedGmt: LocalDateTime,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val username: String,
    val billing: BillingDto,
    val shipping: ShippingDto,
    val isPayingCustomer: Boolean,
    val avatarUrl: String,
    val metaData: List<MetaDataDto>
)