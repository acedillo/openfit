package com.acedillo.openfit.model

data class WorkoutDetail (
    var name: String? = null,
    var description: String? = null,
    var category: Category? = null

)

data class Category(
    var name: String? = null
)