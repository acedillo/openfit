package com.acedillo.openfit

import androidx.lifecycle.*
import com.acedillo.openfit.model.Main
import com.acedillo.openfit.model.Workout
import com.acedillo.openfit.model.WorkoutDetail
import com.acedillo.openfit.repository.Repository
import kotlinx.coroutines.*

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private var coroutineScope: CoroutineScope = viewModelScope

    constructor(repository: Repository, coroutineScope: CoroutineScope) : this(repository) {
        this.coroutineScope = coroutineScope
    }


    private val _loading = MutableLiveData<Boolean>()
    private val _loadingNext = MutableLiveData<Boolean>()
    private val _nextWorkouts = MutableLiveData<Main>()
    private val _workouts = MutableLiveData<Main>()
    private val _workout = MutableLiveData<Workout>()
    private val _workoutDetail = MutableLiveData<WorkoutDetail>()
    private val _workoutImageURL = MutableLiveData<String>()

    val loading: LiveData<Boolean>
        get() = _loading
    val loadingNext: LiveData<Boolean>
        get() = _loadingNext
    val workouts: LiveData<Main>
        get() = _workouts
    val nextWorkouts: LiveData<Main>
        get() = _nextWorkouts
    val workout: LiveData<Workout>
        get() = _workout
    val workoutDetail: LiveData<WorkoutDetail>
        get() = _workoutDetail
    val workoutImageURL: LiveData<String>
        get() = _workoutImageURL

    companion object {
        fun getFactory(repository: Repository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
        }
    }

    fun getMain(): Job {
        _loading.postValue(true)
        return coroutineScope.launch {
            loadMain()
        }
    }

    private suspend fun loadMain() {
        return withContext(Dispatchers.IO) {
            val mainWorkouts = repository.getWorkouts()
            _workouts.postValue(mainWorkouts)
            _loading.postValue(false)
        }
    }

    fun getNextWorkout(url: String): Job {
        _loading.postValue(true)
        return coroutineScope.launch {
            loadNextWorkouts(url)
        }
    }

    private suspend fun loadNextWorkouts(url: String) {
        return withContext(Dispatchers.IO) {
            val mainWorkouts = repository.getNextWorkout(url)
            _nextWorkouts.postValue(mainWorkouts)
            _loading.postValue(false)
        }
    }

    fun getWorkoutDetail(id: Int): Job {
        _loading.postValue(true)
        return coroutineScope.launch {
            loadWorkout(id)
        }
    }

    private suspend fun loadWorkout(id: Int) {
        return withContext(Dispatchers.IO) {
            val workoutDetail = repository.getWorkout(id)
            if (workoutDetail.equipment == null) {
                workoutDetail.equipment = ArrayList()
            }
            _workoutDetail.postValue(workoutDetail)
            _loading.postValue(false)
        }
    }

    fun getWorkoutImage(id: Int): Job {
        _loading.postValue(true)
        return coroutineScope.launch {
            loadWorkoutImage(id)
        }
    }

    private suspend fun loadWorkoutImage(id: Int) {
        return withContext(Dispatchers.IO) {
            val workoutImage = repository.getThumbnail(id)
            if (workoutImage != null) {
                _loading.postValue(false)


                if (workoutImage.medium?.url != null) {
                    _workoutImageURL.postValue(workoutImage.medium?.url!!)
                } else {
                    _workoutImageURL.postValue("")
                }
                _loading.postValue(false)
            } else {
                _workoutImageURL.postValue("")
            }
        }
    }

    fun onWorkoutSelected(workout: Workout) {
        getWorkoutDetail(workout.id)
    }


}