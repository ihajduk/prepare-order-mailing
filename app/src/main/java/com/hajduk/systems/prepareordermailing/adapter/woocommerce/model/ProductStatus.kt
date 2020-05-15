package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class ProductStatus {

    @SerializedName("draft")
    DRAFT,

    @SerializedName("pending")
    PENDING,

    @SerializedName("private")
    PRIVATE,

    @SerializedName("publish")
    PUBLISH
}