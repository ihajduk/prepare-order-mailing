package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

data class AttributeDto(
    val id: String,
    val name: String,
    val position: Int,
    val visible: Boolean,
    val variation: Boolean,
    val options: List<String>
)