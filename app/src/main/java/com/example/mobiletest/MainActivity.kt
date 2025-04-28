package com.example.mobiletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.mobiletest.screens.*
import com.example.mobiletest.ui.theme.MobileTestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTestTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("pokemons") { PokemonsScreen() }
                composable("profile") { ProfileScreen() }
                composable("about") { AboutScreen() }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") },
            label = { Text("Home") },
            alwaysShowLabel = true,
            icon = {}
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "pokemons",
            onClick = { navController.navigate("pokemons") },
            label = { Text("Pokemons") },
            alwaysShowLabel = true,
            icon = {}
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "profile",
            onClick = { navController.navigate("profile") },
            label = { Text("Profile") },
            alwaysShowLabel = true,
            icon = {}
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "about",
            onClick = { navController.navigate("about") },
            label = { Text("About") },
            alwaysShowLabel = true,
            icon = {}
        )
    }
}