package com.acedillo.openfit.model

data class Main (
    var count: Int = 0,
    var next: String? = null,
    var previous: String? = null,
    var results: List<Workout>? = null
)