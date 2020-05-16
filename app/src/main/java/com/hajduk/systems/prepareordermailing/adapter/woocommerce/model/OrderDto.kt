package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: Int,
    val parent_id: Int,
    val orderKey: String,
    val createdVia: String,
    val version: String,
    val status: OrderStatus,
    val currency: Currency,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreated: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreatedGmt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateModified: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
    val paymentMethod: String,
    val paymentMethodTitle: String,
    val transactionId: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val datePaid: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val datePaidGmt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCompleted: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCompletedGmt: LocalDateTime,
    val cartHash: String,
    val metaData: List<MetaDataDto>,
    val lineItems: List<LineItemDto>,
    val taxLines: List<TaxLineDto>,
    val shippingLines: List<ShippingLineDto>,
    val feeLines: List<FeeLineDto>,
    val couponLines: List<CouponLineDto>,
    val refunds: List<RefundDto>
)