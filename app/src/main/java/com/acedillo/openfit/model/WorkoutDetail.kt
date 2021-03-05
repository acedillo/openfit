package com.acedillo.openfit.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkoutDetail (
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var category: Category? = null,
    var equipment: List<Equipment>? = null

): Parcelable


@Parcelize
data class Category(
    var name: String? = null
): Parcelable

@Parcelize
data class Equipment(
    var name: String? = null
): Parcelable