package com.hajduk.systems.prepareordermailing.adapter.woocommerce.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class TaxStatus {

    @JsonProperty("taxable")
    TAXABLE,

    @JsonProperty("shipping")
    SHIPPING,

    @JsonProperty("none")
    NONE
}