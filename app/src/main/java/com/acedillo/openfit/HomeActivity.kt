package com.acedillo.openfit

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acedillo.openfit.fragment.WorkoutDetailFragment
import com.acedillo.openfit.fragment.WorkoutFragment
import com.acedillo.openfit.repository.RetrofitRepository

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var loading: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()

        viewModel = ViewModelProvider(this, HomeViewModel.getFactory(RetrofitRepository())).get(
            HomeViewModel::class.java
        )

        viewModel.loading.observe(this, Observer {
            loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.workouts.observe(this, Observer {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, WorkoutFragment.newInstance(it))
                .addToBackStack(WorkoutFragment::class.java.name)
                .commit()
        })

        viewModel.workoutDetail.observe(this, Observer {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, WorkoutDetailFragment.newInstance(it))
                .addToBackStack(WorkoutFragment::class.java.name)
                .commit()
        })

    }

    private fun initViews() {
        loading = findViewById(R.id.loading)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMain()
    }
}