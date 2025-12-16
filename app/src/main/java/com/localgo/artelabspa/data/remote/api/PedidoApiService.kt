package com.localgo.artelabspa.data.remote.api

import com.localgo.artelabspa.model.CreatePedidoDto
import com.localgo.artelabspa.model.PedidoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PedidoApiService {

    @POST("pedido")
    suspend fun createPedido(
        @Header("Authorization") token: String,
        @Body createPedidoDto: CreatePedidoDto
    ): PedidoResponse

    @GET("pedido/mis-pedidos")
    suspend fun getMisPedidos(
        @Header("Authorization") token: String
    ): PedidoResponse
}
