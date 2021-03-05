package com.acedillo.openfit

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.acedillo.openfit.WorkoutListAdapter.Constant.VIEW_TYPE_ITEM
import com.acedillo.openfit.model.Workout

class WorkoutListAdapter (private val list: ArrayList<Workout?>,
                          private val listener: Listener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM){
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_workout_item, parent, false)
         WorkoutViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        if(getItemViewType(position) == VIEW_TYPE_ITEM) {
            holder as WorkoutViewHolder
            holder.name.text = data?.name
            holder.description.text =
                HtmlCompat.fromHtml(data?.description!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.itemView.setOnClickListener { listener?.onItemSelected(data) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun addLoadingView() {
        //Add loading item
        Handler().post {
            list.add(null)
            notifyItemInserted(list.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (list.size != 0) {
            list.removeAt(list.size - 1)
            notifyItemRemoved(list.size)
        }
    }

    fun addData(dataViews: List<Workout?>) {
        this.list.addAll(dataViews)
        notifyDataSetChanged()
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.workout_name)
        val description : TextView = itemView.findViewById(R.id.workout_description)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onItemSelected(workout: Workout)
    }
}