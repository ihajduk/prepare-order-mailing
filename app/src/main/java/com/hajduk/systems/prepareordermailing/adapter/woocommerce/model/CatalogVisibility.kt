package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.google.gson.annotations.SerializedName

enum class CatalogVisibility {

    @SerializedName("visible")
    VISIBLE,

    @SerializedName("catalog")
    CATALOG,

    @SerializedName("search")
    SEARCH,

    @SerializedName("hidden")
    HIDDEN
}