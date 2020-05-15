package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class TaxStatus {

    @SerializedName("taxable")
    TAXABLE,

    @SerializedName("shipping")
    SHIPPING,

    @SerializedName("none")
    NONE
}