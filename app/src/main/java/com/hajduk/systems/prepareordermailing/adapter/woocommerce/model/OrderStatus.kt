package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class OrderStatus {

    @SerializedName("pending")
    PENDING,

    @SerializedName("processing")
    PROCESSING,

    @SerializedName("on-hold")
    ON_HOLD,

    @SerializedName("completed")
    COMPLETED,

    @SerializedName("cancelled")
    CANCELLED,

    @SerializedName("refunded")
    REFUNDED,

    @SerializedName("failed")
    FAILED,

    @SerializedName("trash")
    TRASH
}