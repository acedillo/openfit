package com.acedillo.openfit.repository

import com.acedillo.openfit.model.Main
import com.acedillo.openfit.model.WorkoutDetail
import com.acedillo.openfit.model.WorkoutImage

interface Repository {
    fun getWorkouts(): Main

    fun getNextWorkout(url : String) : Main

    fun getWorkout(id: Int): WorkoutDetail

    fun getThumbnail(id: Int) : WorkoutImage?

}