package com.localgo.artelabspa.model

sealed class CarritoUiState {
    data object Empty : CarritoUiState()
    data class WithItems(
        val items: List<CarritoItem>,
        val total: Double
    ) : CarritoUiState()
}
