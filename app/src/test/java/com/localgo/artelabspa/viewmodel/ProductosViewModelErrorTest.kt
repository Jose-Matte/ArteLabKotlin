package com.localgo.artelabspa.viewmodel

import com.localgo.artelabspa.data.repository.ProductosRepository
import com.localgo.artelabspa.model.Producto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductosViewModelErrorTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: ProductosRepository
    private lateinit var viewModel: ProductosViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cargarProductosError_muestraMensajeError() = runTest {

        // Simular error del backend
        coEvery { repository.getProductosFromApi() } throws RuntimeException("API caída")

        // Crear ViewModel (init llama cargarProductosDesdeApi automáticamente)
        viewModel = ProductosViewModel(repository)

        // Avanzar corrutinas internas
        dispatcher.scheduler.advanceUntilIdle()

        // Verificar mensaje de error
        assertEquals(
            "Error al cargar los productos: API caída",
            viewModel.errorMessage.value
        )

        // Debe quedar la lista vacía
        assertTrue(viewModel.productos.value.isEmpty())

        // isLoading debe ser false al terminar
        assertEquals(false, viewModel.isLoading.value)
    }
}
