package com.localgo.artelabspa.model
import com.google.gson.annotations.SerializedName

data class Producto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nombre")
    val nombre: String?,
    @SerializedName("description")
    val descripcion: String?,
    @SerializedName("precio")
    val precio: Double?,
    @SerializedName("stock")
    val stock: Int?,
    @SerializedName("imagen")
    val imagen: String?
)
