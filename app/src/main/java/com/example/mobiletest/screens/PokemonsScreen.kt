package com.example.mobiletest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobiletest.helpers.PokemonApi
import com.example.mobiletest.helpers.PokemonDetail
import kotlin.system.measureTimeMillis


@Composable
fun PokemonsScreen() {
    var pokemonsAmount = 10

    var pokemons by remember { mutableStateOf<List<PokemonDetail>>(emptyList()) }
    var nextUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    suspend fun loadPokemons(url: String) {
        println("[DEBUG] Started loading Pokémon list from $url")
        try {
            if (pokemons.isEmpty()) isLoading = true else isLoadingMore = true

            val elapsedMillis = measureTimeMillis {
                val response = PokemonApi.getPokemonListFromUrl(url)
                val details = response.results.map { pokemon ->
                    PokemonApi.getPokemonDetail(pokemon.url)
                }
                pokemons = pokemons + details
                nextUrl = response.next
            }

            println("[DEBUG] ✅ Loaded new Pokémon batch in ${elapsedMillis}ms")

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
            isLoadingMore = false
        }
    }

    LaunchedEffect(Unit) {
        loadPokemons("https://pokeapi.co/api/v2/pokemon?limit=$pokemonsAmount")
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                if (lastVisibleItem == pokemons.lastIndex && !isLoadingMore && nextUrl != null) {
                    loadPokemons(nextUrl!!)
                }
            }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pokemons) { pokemon ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = pokemon.sprites.frontDefault,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(96.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pokemon.name.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
