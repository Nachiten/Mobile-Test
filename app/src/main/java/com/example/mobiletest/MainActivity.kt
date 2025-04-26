package com.example.mobiletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mobiletest.ui.theme.MobileTestTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current // This allows us to show Toast messages

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Ingresá un texto") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row{
                Button(onClick = {
                    // Trim the text and update the state
                    val trimmedText = text.trim()

                    // Update the state with the trimmed text
                    text = trimmedText

                    // Check if the trimmed text is not empty
                    if (trimmedText.isEmpty()) {
                        // Show Toast asking to enter something
                        Toast.makeText(context, "Por favor ingresá un texto", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Show Toast with the entered text
                    Toast.makeText(context, "Ingresaste: $trimmedText", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Mostrar resultado")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    // Update the state with the trimmed text
                    text = ""
                }) {
                    Text("Reiniciar")
                }
            }

        }
    }
}

