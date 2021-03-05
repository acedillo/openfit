package com.acedillo.openfit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Workout(
    var id: Int,
    var description: String? = null,
    var name: String? = null

) : Parcelable