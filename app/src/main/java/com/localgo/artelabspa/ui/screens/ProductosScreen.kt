package com.localgo.artelabspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.localgo.artelabspa.model.Producto
import com.localgo.artelabspa.viewmodel.ProductosViewModel
import com.localgo.artelabspa.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductosViewModel = viewModel(),
    carritoViewModel: CarritoViewModel = viewModel()
) {
    // Recogemos todos los estados del ViewModel
    val products by viewModel.productos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val maxPrice by viewModel.maxPrice.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo de Arte") }) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            // UI PARA LOS FILTROS ---
            FilterControls(
                searchText = searchText,
                onSearchTextChanged = { viewModel.onSearchTextChanged(it) },
                maxPrice = maxPrice.toFloat(),
                onMaxPriceChanged = { viewModel.onMaxPriceChanged(it) }
            )

            // --- UI PARA MOSTRAR LOS PRODUCTOS O ESTADOS ---
            Box(modifier = Modifier.weight(1f)) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                    products.isNotEmpty() -> {
                        // LA LLAMADA A ProductList AHORA ES VÁLIDA
                        ProductList(
                            products = products,
                            onAddToCart = { producto ->
                                carritoViewModel.agregarProducto(producto)
                            }
                        )
                    }
                    // Mensaje para cuando los filtros no devuelven resultados
                    else -> {
                        Text(
                            text = "No se encontraron productos que coincidan con los filtros.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterControls(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    maxPrice: Float,
    onMaxPriceChanged: (Float) -> Unit
) {
    // Usamos una Card para darle un fondo y elevación a los controles
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.large // Bordes más redondeados
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // --- Campo de búsqueda mejorado con iconos ---
            OutlinedTextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                label = { Text("Buscar por nombre...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // Icono de lupa al inicio
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icono de búsqueda"
                    )
                },
                // Icono para limpiar el texto (aparece solo si hay texto)
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { onSearchTextChanged("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                }
            )

            // --- Slider de precio con título y valor más claros ---
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Precio máximo",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    // Formatea el precio para que se vea como dinero (ej: $5.500)
                    Text(
                        text = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                            .format(maxPrice),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Slider(
                    value = maxPrice,
                    onValueChange = onMaxPriceChanged,
                    valueRange = 0f..10000f, // Ajusta el rango máximo según tus productos
                    steps = 99
                )
            }
        }
    }
}

//  FUNCIÓN AÑADIDA QUE FALTABA
@Composable
fun ProductList(products: List<Producto>, onAddToCart: (Producto) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, onAddToCart = onAddToCart)
        }
    }
}

@Composable
fun ProductCard(product: Producto, onAddToCart: (Producto) -> Unit) {
    var showAddedMessage by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(model = product.imagen,
                contentDescription = "Imagen de ${product.nombre ?: "Producto"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.nombre ?: "Nombre no disponible",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.descripcion ?: "Sin descripción.",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(12.dp))

                // --- Fila para Precio y Stock ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Muestra el PRECIO
                    Text(
                        text = "$${product.precio?.toString() ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    // Muestra el STOCK disponible
                    Text(
                        text = "Stock: ${product.stock?.toString() ?: "0"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if ((product.stock ?: 0) > 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón para añadir al carrito
                Button(
                    onClick = {
                        onAddToCart(product)
                        showAddedMessage = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = (product.stock ?: 0) > 0
                ) {
                    Text(if ((product.stock ?: 0) > 0) "Agregar al Carrito" else "Agotado")
                }

                // Mensaje de confirmación
                if (showAddedMessage) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        showAddedMessage = false
                    }
                    Text(
                        text = "✓ Agregado al carrito",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
