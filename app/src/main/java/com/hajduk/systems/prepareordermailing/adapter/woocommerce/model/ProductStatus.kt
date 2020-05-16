package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class ProductStatus {

    @JsonProperty("draft")
    DRAFT,

    @JsonProperty("pending")
    PENDING,

    @JsonProperty("private")
    PRIVATE,

    @JsonProperty("publish")
    PUBLISH
}