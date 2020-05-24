package com.hajduk.systems.prepareordermailing.holder

import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import java.io.File

object DomainDataHolder {
    private var orderId: Int? = 0
    private var photoFileAbsolutePath: String? = null
    private var customer: CustomerDto? = null
    private var order: OrderDto? = null

    fun clearData() {
        if (photoFileAbsolutePath != null) {
            File(demandPhotoFileAbsolutePath()).apply {
                if (exists()) {
                    delete()
                }
            }
        }
        orderId = null
        photoFileAbsolutePath = null
        customer = null
        order = null
    }

    fun setOrderId(orderId: Int) {
        this.orderId = orderId
    }

    fun demandOrderId(): Int {
        return orderId ?: throw RequiredDataMissingException("orderId")
    }

    fun setPhotoFileAbsolutePath(photoFileAbsolutePath: String) {
        this.photoFileAbsolutePath = photoFileAbsolutePath
    }

    fun demandPhotoFileAbsolutePath(): String {
        return photoFileAbsolutePath ?: throw RequiredDataMissingException("photoFileAbsolutePath")
    }

    fun demandPhotoFile(): File {
        return File(demandPhotoFileAbsolutePath()).also {
            if (!it.exists()) {
                throw IllegalStateException("Photo file doesn't exist")
            }
        }
    }

    fun setCustomer(customer: CustomerDto) {
        this.customer = customer
    }

    fun demandCustomer(): CustomerDto {
        return customer ?: throw RequiredDataMissingException("customer")
    }

    fun setOrder(order: OrderDto) {
        this.order = order
    }

    fun demandOrder(): OrderDto {
        return order ?: throw RequiredDataMissingException("order")
    }
}

class RequiredDataMissingException(dataFieldName: String) : RuntimeException("Missing required data: $dataFieldName")