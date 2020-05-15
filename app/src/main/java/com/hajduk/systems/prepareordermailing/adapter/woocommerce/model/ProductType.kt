package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class ProductType {

    @SerializedName("simple")
    SIMPLE,

    @SerializedName("grouped")
    GROUPED,

    @SerializedName("external")
    EXTERNAL,

    @SerializedName("variable")
    VARIABLE
}