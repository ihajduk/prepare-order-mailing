package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: Int,
    val parent_id: Int,
    val orderKey: String,
    val createdVia: String,
    val version: String,
    val status: String, //enum
    val currency: String, //enum
    val dateCreated: LocalDateTime,
    val dateCreatedGmt: LocalDateTime,
    val dateModified: LocalDateTime,
    val datModifiedGmt: LocalDateTime,
    val discountTotal: BigDecimal,
    val discountTax: BigDecimal,
    val shippingTotal: BigDecimal,
    val shippingTax: BigDecimal,
    val cartTax: BigDecimal,
    val total: BigDecimal,
    val totalTax: BigDecimal,
    val pricesIncludeTax: Boolean,
    val customerId: Int,
    val customerIpAddress: String,
    val customerUserAgent: String,
    val customerNote: String,
    val billing: BillingDto,
    val shipping: ShippingDto,
    val paymentMethod: String, //enum
    val paymentMethodTitle: String,
    val transactionId: String,
    val datePaid: LocalDateTime,
    val datePaidGmt: LocalDateTime,
    val dateCompleted: LocalDateTime,
    val dateCompletedGmt: LocalDateTime,
    val cartHash: String,
    val metaData: List<MetaDataDto>,
    val lineItems: List<LineItemDto>,
    val taxLines: List<TaxLineDto>,
    val shippingLines: List<ShippingLineDto>,
    val feeLines: List<FeeLineDto>,
    val couponLines: List<Any>,
    val refunds: List<Any>
)