package com.fcadev.rickandmortyapp.model.location

data class Result(
    val created: String?,
    val dimension: String?,
    val id: Int?,
    val name: String?,
    val residents: List<String?>?,
    val type: String?,
    val url: String?
)