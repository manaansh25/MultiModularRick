package com.example.multimodularrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.multimodularrick.ui.theme.MultiModularRickTheme
import com.example.network.KtorClient
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var character by remember { //pura package ke sath likhna padega warna kotlin isko default Character class samajh lega
                mutableStateOf<com.example.network.Character?>(null)
            }


            LaunchedEffect(Unit) {
                delay(3000)
                println("Fetching character...")
                try {
                    val result = ktorClient.getCharacter(1)
                    println("Got character: ${result.name}")
                    character = result
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    e.printStackTrace()
                }
            }

            MultiModularRickTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Android")
                        Text(text = character?.name ?: "loading...")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiModularRickTheme {
        Greeting("Android")
    }
}