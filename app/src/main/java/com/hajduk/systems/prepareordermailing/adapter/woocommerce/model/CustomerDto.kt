package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CustomerDto(
    val id: Int,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreated: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreatedGmt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateModified: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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