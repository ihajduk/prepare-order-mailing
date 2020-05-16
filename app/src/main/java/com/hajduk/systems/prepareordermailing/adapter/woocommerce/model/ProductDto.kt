package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductDto(
    val id: Int,
    val name: String,
    val slug: String,
    val permalink: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreated: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreatedGmt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateModified: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateModifiedGmt: LocalDateTime,
    val type: ProductType,
    val status: ProductStatus,
    val featured: Boolean,
    val catalogVisibility: CatalogVisibility,
    val description: String,
    val shortDescription: String,
    val sku: String,
    val price: BigDecimal,
    val regularPrice: String,
    val salePrice: BigDecimal,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateOnSaleFrom: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateOnSaleFromGmt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateOnSaleTo: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateOnSaleToGmt: LocalDateTime,
    val priceHtml: String,
    val onSale: Boolean,
    val purchasable: Boolean,
    val totalSales: Int,
    val virtual: Boolean,
    val downloadable: Boolean,
    val downloads: List<DownloadDto>,
    val downloadLimit: Int,
    val downloadExpiry: Int,
    val externalUrl: String,
    val buttonText: String,
    val taxStatus: TaxStatus,
    val taxClass: String,
    val manageStock: Boolean,
    val stockQuantity: Int,
    val stockStatus: StockStatus,
    val backorders: String,//yes, no - from boolean?
    val backordersAllowed: Boolean,
    val backordered: Boolean,
    val soldIndividually: Boolean,
    val weight: BigDecimal,
    val dimensions: DimensionsDto,
    val shippingRequired: Boolean,
    val shippingTaxable: Boolean,
    val shippingClass: String,
    val shippingClassId: Int,
    val reviewsAllowed: Boolean,
    val averageRating: BigDecimal,
    val ratingCount: Int,
    val relatedIds: List<Int>,
    val upsellIds: List<Int>,
    val crossSellIds: List<Int>,
    val parentId: Int,
    val purchaseNote: String,
    val categories: List<CategoryDto>,
    val tags: List<TagDto>,
    val images: List<ImageDto>,
    val attributes: List<AttributeDto>,
    val defaultAttributes: List<DefaultAttributeDto>,
    val variations: List<Int>,
    val groupedProducts: List<Int>,
    val menuOrder: Int,
    val metaData: List<MetaDataDto>
)