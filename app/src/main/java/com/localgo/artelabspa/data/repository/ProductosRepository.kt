package com.localgo.artelabspa.data.repository

import com.localgo.artelabspa.data.remote.RetrofitClient
import com.localgo.artelabspa.model.Producto

class ProductosRepository {
    /**
     * Obtiene la lista de productos desde nuestra API de MockAPI.
     * Es una función suspendida porque realiza una operación de red.
     */
    suspend fun getProductosFromApi(): List<Producto> {
        return try {
            // Llamamos a la instancia principal de Retrofit y al método que obtiene los productos.
            RetrofitClient.instance.getProductos()
        } catch (e: Exception) {
            // En caso de un error de red (ej. sin conexión), imprimimos el error para depuración
            // y devolvemos una lista vacía para que la app no se bloquee.
            e.printStackTrace()
            emptyList()
        }
    }
}
