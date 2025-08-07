package com.example.cirrusmobileapp.presentation.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations(val route: String) {

    @Serializable
    data object SyncScreen : Destinations("sync")

    @Serializable
    data object LoginScreen : Destinations("login")

    @Serializable
    data object HomeScreen : Destinations("home")

    @Serializable
    data object Customers : Destinations("customers")

    @Serializable
    data object Catalog : Destinations("catalog")

    @Serializable
    data object Calendar : Destinations("calendar")

    @Serializable
    data object More : Destinations("more")

}
