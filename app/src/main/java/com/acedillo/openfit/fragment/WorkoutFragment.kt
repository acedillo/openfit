package com.acedillo.openfit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acedillo.openfit.HomeViewModel
import com.acedillo.openfit.R
import com.acedillo.openfit.RecyclerViewLoadMoreScrollListener
import com.acedillo.openfit.WorkoutListAdapter
import com.acedillo.openfit.model.Main
import com.acedillo.openfit.model.Workout
import com.acedillo.openfit.repository.RetrofitRepository


class WorkoutFragment : Fragment() {

    private var viewModel: HomeViewModel? = null
    private var list: RecyclerView? = null
    private lateinit var adapter: WorkoutListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var scrollListener: RecyclerViewLoadMoreScrollListener

    companion object {

        private const val ARGS_MAIN = "args.main"

        fun newInstance(data: Main): WorkoutFragment {
            val workoutFragment = WorkoutFragment()
            val args = Bundle()

            args.putParcelable(ARGS_MAIN, data)
            workoutFragment.arguments = args
            return workoutFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProvider(requireActivity(), HomeViewModel.getFactory(RetrofitRepository()))
                .get(HomeViewModel::class.java)

        viewModel?.nextWorkouts?.observe(this, Observer {
            updateList(it)
        })

        viewModel?.loadingNext?.observe(this, Observer {
            if (it) {
                adapter.addLoadingView()
            } else {
                adapter.removeLoadingView()
                scrollListener.setLoaded()
            }
        })

        super.onCreate(savedInstanceState)
    }

    private fun updateList(main: Main?) {
        (list?.adapter as WorkoutListAdapter).addData(main?.results!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        val data = requireArguments()[ARGS_MAIN] as Main
        adapter = WorkoutListAdapter(ArrayList(data.results),
            object : WorkoutListAdapter.Listener {
                override fun onItemSelected(workout: Workout) {
                    viewModel?.onWorkoutSelected(workout)
                }
            })


        initRecyclerView(view, data)

        return view
    }

    private fun initRecyclerView(view: View, data: Main) {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list = view.findViewById(R.id.workout_list)
        list?.layoutManager = layoutManager
        list?.adapter = adapter
        list?.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            RecyclerView.VERTICAL
        )
        list?.addItemDecoration(dividerItemDecoration)

        scrollListener = RecyclerViewLoadMoreScrollListener(layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel?.getNextWorkout(data.next!!)
            }
        })

        list?.addOnScrollListener(scrollListener)

    }


}