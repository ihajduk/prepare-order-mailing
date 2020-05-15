package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import java.time.LocalDateTime

data class ImageDto(
    val id: Int,
    val dateCreated: LocalDateTime,
    val dateCreatedGmt: LocalDateTime,
    val dateModified: LocalDateTime,
    val dateModifiedGmt: LocalDateTime,
    val src: String,
    val name: String,
    val alt: String
)