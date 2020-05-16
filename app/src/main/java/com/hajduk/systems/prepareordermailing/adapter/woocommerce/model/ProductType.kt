package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class ProductType {

    @JsonProperty("simple")
    SIMPLE,

    @JsonProperty("grouped")
    GROUPED,

    @JsonProperty("external")
    EXTERNAL,

    @JsonProperty("variable")
    VARIABLE
}