package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.repository.ProductosRepository
import com.localgo.artelabspa.model.Producto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val repository: ProductosRepository = ProductosRepository()
) : ViewModel() {

    // --- ESTADO INTERNO ---
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    // Guarda la lista COMPLETA de productos, sin filtrar.
    private val _allProducts = MutableStateFlow<List<Producto>>(emptyList())

    // --- NUEVO: ESTADO PARA LOS FILTROS ---
    private val _searchText = MutableStateFlow("")
    private val _maxPrice = MutableStateFlow(10000.0) // Precio máximo inicial alto

    // --- ESTADO PÚBLICO (LO QUE LA UI OBSERVA) ---
    val isLoading = _isLoading.asStateFlow()
    val errorMessage = _errorMessage.asStateFlow()
    val searchText = _searchText.asStateFlow()
    val maxPrice = _maxPrice.asStateFlow()

    // --- NUEVO: EL FLOW DE PRODUCTOS FILTRADOS ---
    // Este es el flow mágico que la UI observará. Combina la lista completa
    // con los filtros de búsqueda y precio, y se actualiza automáticamente.
    val productos: StateFlow<List<Producto>> = combine(
        _allProducts,
        _searchText,
        _maxPrice
    ) { products, text, price ->
        products.filter { product ->
            // Condición de filtro por nombre (ignora mayúsculas/minúsculas)
            val nameMatches = product.nombre?.contains(text, ignoreCase = true) ?: true
            // Condición de filtro por precio
            val priceMatches = (product.precio ?: 0.0) <= price
            nameMatches && priceMatches
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        cargarProductosDesdeApi()
    }

    // --- NUEVO: FUNCIONES PARA ACTUALIZAR LOS FILTROS DESDE LA UI ---
    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onMaxPriceChanged(price: Float) {
        _maxPrice.value = price.toDouble()
    }

    private fun cargarProductosDesdeApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Ahora cargamos la lista completa en nuestra variable interna.
                _allProducts.value = repository.getProductosFromApi()
                if (_allProducts.value.isEmpty()) {
                    _errorMessage.value = "No se encontraron productos."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar los productos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
