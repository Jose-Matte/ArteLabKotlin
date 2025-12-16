package com.localgo.artelabspa.data.repository

import com.localgo.artelabspa.data.remote.RetrofitClient
import com.localgo.artelabspa.model.CreatePedidoDto
import com.localgo.artelabspa.model.PedidoResponse

class PedidoRepository {
    private val pedidoApiService = RetrofitClient.createPedidoApiService()

    suspend fun createPedido(token: String, createPedidoDto: CreatePedidoDto): Result<PedidoResponse> {
        return try {
            val response = pedidoApiService.createPedido("Bearer $token", createPedidoDto)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMisPedidos(token: String): Result<PedidoResponse> {
        return try {
            val response = pedidoApiService.getMisPedidos("Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
