package com.acedillo.openfit.model

import com.google.gson.annotations.SerializedName

data class WorkoutImage (
   var medium: ImageUrl? = null
)

data class ImageUrl(
   var url: String? = null
)