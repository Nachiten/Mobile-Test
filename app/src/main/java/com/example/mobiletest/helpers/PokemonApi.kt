package com.example.mobiletest.helpers


import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
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

    suspend fun getPokemonListFromUrl(url: String): PokemonResponse {
        return client.get(url).body()
    }

    suspend fun getPokemonDetail(url: String): PokemonDetail {
        return client.get(url).body()
    }
}

@Serializable
data class PokemonResponse(
    val results: List<Pokemon>,
    var next: String? = null
)

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

@Serializable
data class PokemonDetail(
    val name: String,
    val sprites: Sprites
)

@Serializable
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String
)