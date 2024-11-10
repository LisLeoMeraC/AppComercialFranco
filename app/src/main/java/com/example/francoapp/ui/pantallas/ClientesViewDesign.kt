package com.example.francoapp.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.francoapp.datos.repositorio.ClienteRepositoryImpl
import com.example.francoapp.datos.servicio.Helper
import com.example.francoapp.datos.servicio.ServiciosAPI
import com.example.francoapp.dominio.ClienteUseCase
import com.example.francoapp.entidades.Cliente
import com.example.francoapp.ui.viewmodel.ClienteViewModel
import com.example.francoapp.ui.viewmodel.TestViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@Composable
fun ClientesViewDesign(clienteViewModel: ClienteViewModel) {
    val clientes by clienteViewModel.clientes.collectAsState()
    val errorMessage by clienteViewModel.errorMessage.collectAsState()
    val listState = rememberLazyListState()
    val searchQuery = remember { mutableStateOf("") } // Estado para la búsqueda

    // Lanza la carga inicial de clientes solo una vez cuando la vista se inicializa
    LaunchedEffect(Unit) {
        clienteViewModel.cargarClientes()
    }

    // Detecta si se llega al final de la lista para cargar más clientes
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                lastVisibleItemIndex >= (clientes.size - 1)  // Detecta si está en el último ítem visible
            }
            .distinctUntilChanged()
            .collect { isAtEnd ->
                if (isAtEnd && !clienteViewModel.lastPageReached) {
                    clienteViewModel.cargarClientes()  // Carga más clientes si se alcanza el final
                }
            }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Header mejorado
        item {
            ClientesHeader() // Llamada al nuevo encabezado estilizado
        }

        // Cuadro de búsqueda debajo del encabezado
        item {
            SearchBar(searchQuery.value) { query -> searchQuery.value = query }
        }

        // Muestra la lista de clientes
        val filteredClientes = if (searchQuery.value.isEmpty()) {
            clientes
        } else {
            clientes.filter { it.id.toString().contains(searchQuery.value, ignoreCase = true) }
        }

        items(filteredClientes) { cliente ->
            ClienteItem(cliente)
        }

        // Muestra un indicador de carga al final si hay más clientes por cargar
        if (errorMessage == null && !clienteViewModel.lastPageReached) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        } else if (errorMessage != null) { // Muestra un mensaje de error si ocurre un error
            item {
                Text(
                    text = errorMessage ?: "",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun ClientesHeader() {
    // Contenedor del encabezado con un fondo y un diseño atractivo
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFF4C6184), Color(0xFFF9C177))))
            .padding(vertical = 20.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
    ) {
        Text(
            text = "Clientes",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Subtítulo o descripción del encabezado
        Text(
            text = "Explora la lista de clientes y sus detalles.",
            style = MaterialTheme.typography.body1.copy(color = Color.White.copy(alpha = 0.8f)),
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    // Diseño moderno de la barra de búsqueda
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar por código de cliente...") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.White),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar cliente",
                tint = Color.Gray
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF4C6184), // Color del borde cuando está enfocado
            unfocusedBorderColor = Color.Gray,
            textColor = Color.Black
        )
    )
}

@Composable
fun ClienteItem(cliente: Cliente) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* Agrega la acción deseada */ },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Línea naranja con gradiente más ancha
            Divider(
                color = Color.Transparent,  // Color transparente para usar el gradiente
                thickness = 8.dp,  // Hacer la línea más ancha
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.parseColor("#4c6184")), // Primer color del gradiente
                                Color(android.graphics.Color.parseColor("#f9c177"))  // Segundo color del gradiente
                            )
                        )
                    )
            )

            // Contenido del card (detalles del cliente)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono o imagen de cliente
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(android.graphics.Color.parseColor("#4c6184")), // Primer color del gradiente
                                    Color(android.graphics.Color.parseColor("#f9c177"))  // Segundo color del gradiente
                                )
                            ),
                            shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cliente.nombres.take(1).uppercase(),  // Inicial del cliente
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Detalles del cliente
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${cliente.nombres} ${cliente.apellidos}",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ID: ${cliente.id}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                    Text(
                        text = "Cédula: ${cliente.cedula}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                    Text(
                        text = "Teléfono: ${cliente.telefono}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                    Text(
                        text = "Recinto: ${cliente.recinto.nombreRecinto}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}