package com.example.mobiletest.helpers


import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object PokemonApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getPokemonList(limit: Int = 20): PokemonResponse {
        return client.get("https://pokeapi.co/api/v2/pokemon?limit=$limit").body()
    }
}

@Serializable
data class PokemonResponse(
    val results: List<Pokemon>
)

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)