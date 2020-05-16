package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class CatalogVisibility {

    @JsonProperty("visible")
    VISIBLE,

    @JsonProperty("catalog")
    CATALOG,

    @JsonProperty("search")
    SEARCH,

    @JsonProperty("hidden")
    HIDDEN
}