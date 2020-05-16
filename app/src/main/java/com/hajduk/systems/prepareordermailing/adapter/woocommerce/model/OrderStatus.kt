package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class OrderStatus {

    @JsonProperty("pending")
    PENDING,

    @JsonProperty("processing")
    PROCESSING,

    @JsonProperty("on-hold")
    ON_HOLD,

    @JsonProperty("completed")
    COMPLETED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("refunded")
    REFUNDED,

    @JsonProperty("failed")
    FAILED,

    @JsonProperty("trash")
    TRASH
}