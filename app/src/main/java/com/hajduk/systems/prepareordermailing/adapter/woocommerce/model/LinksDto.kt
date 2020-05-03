package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

data class LinksDto(
    val collection: List<CollectionDto>,
    val self: List<SelfDto>
)