package com.example.francoapp.ui.pantallas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.francoapp.R
import com.example.francoapp.dominio.SesionManager
import com.example.francoapp.ui.viewmodel.LoginViewModel


@Composable
fun LoginDesign(viewModel: LoginViewModel, navController:NavController, sesionManager: SesionManager) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var token by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color=Color(android.graphics.Color.parseColor("#f8eeec")))
    ) {
        Image(
            painter = painterResource(id=R.drawable.top_background1),
            contentDescription = null
        )
        Text(text="Bienvenido\nBack", color = Color(android.graphics.Color.parseColor("#Ea5d35")),
        modifier=Modifier.padding(top = 16.dp, start = 24.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )
       // var text by rememberSaveable { mutableStateOf("") }
        TextField(value = username, onValueChange = {username=it},
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.name), contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(start = 6.dp)
                        .padding(3.dp)
                )
            },
            label = {Text(text="Usuario")},
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .background(Color.White, CircleShape)

        )

        //var text2 by rememberSaveable { mutableStateOf("") }

        TextField(value = password, onValueChange = {password=it},
            leadingIcon = {
                Image(
                    painter = painterResource(id=R.drawable.password), contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(start = 6.dp)
                        .padding(6.dp)
                )
            },
            label = {Text(text="Contraseña")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.view_open else R.drawable.view_close),

                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .background(Color.White, CircleShape)

        )

        Image(painter = painterResource(id=R.drawable.btn_arraw1), contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .padding(top = 24.dp)
                .align(Alignment.End)
                .clickable {  // Evento de autenticación en el clic de la imagen
                    viewModel.autenticar(username, password) { resultToken, errorMessage ->
                        token = resultToken
                        mensajeError = errorMessage

                        // Navegar al dashboard si el token es válido
                        if (resultToken != null) {
                            sesionManager.saveToken(resultToken)
                            navController.navigate("dashboard")
                        }
                    } }
                .padding(end = 24.dp)
        )
        mensajeError?.let {
            Text(
                text = "Error: $it",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }










    /*Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo claro para una apariencia moderna
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center), // Centrado
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Espacio para el logo en la parte superior
            Box(
                modifier = Modifier
                    .size(200.dp)
                     // Fondo y esquinas redondeadas opcionales
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logouno),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Bienvenido",
                fontSize = 24.sp,
                color = Color(0xFF1A73E8), // Color azul destacado
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Inicia sesión para continuar",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de usuario y contraseña
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // Botón de inicio de sesión
            Button(
                onClick = {
                    viewModel.autenticar(username, password) { resultToken, errorMessage ->
                        token = resultToken
                        mensajeError = errorMessage

                        if (resultToken != null) {
                            navController.navigate("dashboard")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A73E8),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Iniciar sesión")
            }

            // Mensaje de token de autenticación o de error

            mensajeError?.let {
                Text(
                    text = "Error: $it",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }*/
}

