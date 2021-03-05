package com.acedillo.openfit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acedillo.openfit.HomeViewModel
import com.acedillo.openfit.R
import com.acedillo.openfit.model.WorkoutDetail
import com.acedillo.openfit.repository.RetrofitRepository
import com.acedillo.openfit.util.ImageLoader

class WorkoutDetailFragment : Fragment() {

    private lateinit var thumbnail: ImageView
    private lateinit var name: TextView
    private lateinit var category: TextView
    private lateinit var equipment: TextView
    private lateinit var viewModel: HomeViewModel
    private lateinit var workoutDetail: WorkoutDetail

    companion object {
        private const val ARG_WORKOUT_DETAIL = "arg.workout.detail"

        fun newInstance(workoutDetail: WorkoutDetail): WorkoutDetailFragment {
            val fragment = WorkoutDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_WORKOUT_DETAIL, workoutDetail)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_workout_detail, container, false)
        name = view.findViewById(R.id.name)
        category = view.findViewById(R.id.category)
        equipment = view.findViewById(R.id.equipment)
        thumbnail = view.findViewById(R.id.thumbnail)

        workoutDetail = requireArguments()[ARG_WORKOUT_DETAIL] as WorkoutDetail

        name.text = workoutDetail.name
        category.text = workoutDetail.category?.name

        val equipmentList = workoutDetail.equipment
        var equipmentString = ""
        if (equipmentList!!.isNotEmpty()) {
            for (equipmentName in equipmentList) {
                equipmentString = equipmentString + equipmentName + "\n"
            }
            equipment.text = equipmentString
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            requireActivity(),
            HomeViewModel.getFactory(RetrofitRepository())
        ).get(HomeViewModel::class.java)

        viewModel.workoutImageURL.observe(requireActivity(), Observer {
            ImageLoader.loadImage(thumbnail, it)
        })

        viewModel.getWorkoutImage(workoutDetail.id)
        super.onViewCreated(view, savedInstanceState)
    }

}