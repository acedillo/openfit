package com.acedillo.openfit.repository

import com.acedillo.openfit.model.Main
import com.acedillo.openfit.model.WorkoutDetail
import com.acedillo.openfit.model.WorkoutImage
import com.acedillo.openfit.rest.ApiClient
import com.acedillo.openfit.rest.ApiInterface
import com.google.gson.JsonSyntaxException

class RetrofitRepository : Repository {

    override fun getWorkouts(): Main {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getWorkouts()
        val response = call.execute()
        return response.body()!!
    }

    override fun getNextWorkout(url: String): Main {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getNextWorkouts(url)
        val response = call.execute()
        return response.body()!!
    }

    override fun getWorkout(id: Int): WorkoutDetail {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getWorkout(id)
        val response = call.execute()
        return response.body()!!
    }

    override fun getThumbnail(id: Int): WorkoutImage? {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getThumbnail(id)
        return try {
            val response = call.execute()
            response.body()!!
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}