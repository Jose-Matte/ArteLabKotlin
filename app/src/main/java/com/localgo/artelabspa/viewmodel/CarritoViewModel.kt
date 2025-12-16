package com.localgo.artelabspa.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.repository.PedidoRepository
import com.localgo.artelabspa.model.CarritoItem
import com.localgo.artelabspa.model.CarritoUiState
import com.localgo.artelabspa.model.CreatePedidoDto
import com.localgo.artelabspa.model.PedidoItemDto
import com.localgo.artelabspa.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarritoViewModel(private val context: Context) : ViewModel() {

    private val pedidoRepository = PedidoRepository()
    private val sessionManager = SessionManager(context)

    private val _carritoItems = MutableStateFlow<List<CarritoItem>>(emptyList())
    val carritoItems: StateFlow<List<CarritoItem>> = _carritoItems.asStateFlow()

    private val _uiState = MutableStateFlow<CarritoUiState>(CarritoUiState.Empty)
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    private val _itemCount = MutableStateFlow(0)
    val itemCount: StateFlow<Int> = _itemCount.asStateFlow()

    private val _compraState = MutableStateFlow<CompraState>(CompraState.Idle)
    val compraState: StateFlow<CompraState> = _compraState.asStateFlow()

    init {
        updateUiState()
    }

    fun agregarProducto(producto: Producto) {
        _carritoItems.update { currentItems ->
            val existingItem = currentItems.find { it.producto.id == producto.id }

            if (existingItem != null) {
                // Si el producto ya existe, incrementar cantidad
                val stockDisponible = producto.stock ?: 0
                if (existingItem.cantidad < stockDisponible) {
                    currentItems.map { item ->
                        if (item.producto.id == producto.id) {
                            item.copy(cantidad = item.cantidad + 1)
                        } else {
                            item
                        }
                    }
                } else {
                    // No agregar más si ya llegamos al stock máximo
                    currentItems
                }
            } else {
                // Si es un producto nuevo, agregarlo con cantidad 1
                currentItems + CarritoItem(producto, 1)
            }
        }
        updateUiState()
    }

    fun eliminarProducto(productoId: String) {
        _carritoItems.update { currentItems ->
            currentItems.filter { it.producto.id != productoId }
        }
        updateUiState()
    }

    fun aumentarCantidad(productoId: String) {
        _carritoItems.update { currentItems ->
            currentItems.map { item ->
                if (item.producto.id == productoId) {
                    val stockDisponible = item.producto.stock ?: 0
                    if (item.cantidad < stockDisponible) {
                        item.copy(cantidad = item.cantidad + 1)
                    } else {
                        item
                    }
                } else {
                    item
                }
            }
        }
        updateUiState()
    }

    fun disminuirCantidad(productoId: String) {
        _carritoItems.update { currentItems ->
            currentItems.mapNotNull { item ->
                if (item.producto.id == productoId) {
                    if (item.cantidad > 1) {
                        item.copy(cantidad = item.cantidad - 1)
                    } else {
                        null // Eliminar el item si la cantidad llega a 0
                    }
                } else {
                    item
                }
            }
        }
        updateUiState()
    }

    fun vaciarCarrito() {
        _carritoItems.value = emptyList()
        updateUiState()
    }

    fun calcularTotal(): Double {
        return _carritoItems.value.sumOf { it.subtotal }
    }

    private fun updateUiState() {
        val items = _carritoItems.value
        _itemCount.value = items.sumOf { it.cantidad }

        _uiState.value = if (items.isEmpty()) {
            CarritoUiState.Empty
        } else {
            CarritoUiState.WithItems(
                items = items,
                total = calcularTotal()
            )
        }
    }

    fun procesarCompra() {
        viewModelScope.launch {
            _compraState.value = CompraState.Loading

            try {
                val token = sessionManager.getToken()
                if (token.isNullOrEmpty()) {
                    _compraState.value = CompraState.Error("No estás autenticado. Inicia sesión para realizar la compra.")
                    return@launch
                }

                // Convertir los items del carrito a PedidoItemDto
                val items = _carritoItems.value.mapNotNull { carritoItem ->
                    val productoId = carritoItem.producto.id
                    if (productoId != null) {
                        PedidoItemDto(
                            producto = productoId,
                            cantidad = carritoItem.cantidad
                        )
                    } else {
                        null
                    }
                }

                // Crear el DTO del pedido
                val createPedidoDto = CreatePedidoDto(
                    items = items,
                    direccionEntrega = null,
                    notasEntrega = null
                )

                // Enviar el pedido a la API
                val result = pedidoRepository.createPedido(token, createPedidoDto)

                result.fold(
                    onSuccess = { response ->
                        if (response.success) {
                            vaciarCarrito()
                            _compraState.value = CompraState.Success("Pedido creado exitosamente")
                        } else {
                            _compraState.value = CompraState.Error(response.message ?: "Error al crear el pedido")
                        }
                    },
                    onFailure = { exception ->
                        _compraState.value = CompraState.Error(exception.message ?: "Error de conexión")
                    }
                )
            } catch (e: Exception) {
                _compraState.value = CompraState.Error(e.message ?: "Error inesperado")
            }
        }
    }

    fun resetCompraState() {
        _compraState.value = CompraState.Idle
    }
}

sealed class CompraState {
    data object Idle : CompraState()
    data object Loading : CompraState()
    data class Success(val message: String) : CompraState()
    data class Error(val message: String) : CompraState()
}
