package com.localgo.artelabspa.model

import com.google.gson.annotations.SerializedName

data class PedidoItemDto(
    @SerializedName("producto")
    val producto: String,  // ID del producto

    @SerializedName("cantidad")
    val cantidad: Int
)

data class CreatePedidoDto(
    @SerializedName("items")
    val items: List<PedidoItemDto>,

    @SerializedName("direccionEntrega")
    val direccionEntrega: String? = null,

    @SerializedName("notasEntrega")
    val notasEntrega: String? = null
)

data class PedidoResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: Pedido?
)

data class Pedido(
    @SerializedName("_id")
    val id: String,

    @SerializedName("cliente")
    val cliente: String,

    @SerializedName("items")
    val items: List<PedidoItem>,

    @SerializedName("total")
    val total: Double,

    @SerializedName("estado")
    val estado: String = "pendiente",

    @SerializedName("imagen")
    val imagen: String? = null,

    @SerializedName("imagenThumbnail")
    val imagenThumbnail: String? = null,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String
)

data class PedidoItem(
    @SerializedName("producto")
    val producto: ProductoRef,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("precio")
    val precio: Double
)

data class ProductoRef(
    @SerializedName("_id")
    val id: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("imagen")
    val imagen: String? = null
)
