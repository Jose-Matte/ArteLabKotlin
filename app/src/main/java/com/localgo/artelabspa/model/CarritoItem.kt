package com.localgo.artelabspa.model

data class CarritoItem(
    val producto: Producto,
    val cantidad: Int = 1
) {
    val subtotal: Double
        get() = (producto.precio ?: 0.0) * cantidad
}
