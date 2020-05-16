package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class StockStatus {

    @JsonProperty("instock")
    IN_STOCK,

    @JsonProperty("outofstock")
    OUT_OF_STOCK,

    @JsonProperty("onbackorder")
    ON_BACKORDER
}