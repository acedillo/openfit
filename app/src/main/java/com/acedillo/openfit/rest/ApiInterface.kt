package com.acedillo.openfit.rest

import com.acedillo.openfit.model.Main
import com.acedillo.openfit.model.WorkoutDetail
import com.acedillo.openfit.model.WorkoutImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface ApiInterface {

    @GET("/api/v2/exercise/?language=2&format=json")
    fun getWorkouts(): Call<Main>

    @GET
    fun getNextWorkouts(@Url url: String): Call<Main>

    @GET("api/v2/exerciseinfo/{id}/?language=2&format=json")
    fun getWorkout(@Path("id") id: Int): Call<WorkoutDetail>

    @GET("https://wger.de/api/v2/exerciseimage/{id}/thumbnails/?is_main=True&language=2&format=json")
    fun getThumbnail(@Path("id") id: Int): Call<WorkoutImage>
}