package com.acedillo.openfit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main (
    var count: Int = 0,
    var next: String? = null,
    var previous: String? = null,
    var results: List<Workout?>? = null
): Parcelable