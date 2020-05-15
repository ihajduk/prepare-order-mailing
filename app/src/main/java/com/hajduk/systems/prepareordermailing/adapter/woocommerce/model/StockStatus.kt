package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class StockStatus {

    @SerializedName("instock")
    IN_STOCK,

    @SerializedName("outofstock")
    OUT_OF_STOCK,

    @SerializedName("onbackorder")
    ON_BACKORDER
}