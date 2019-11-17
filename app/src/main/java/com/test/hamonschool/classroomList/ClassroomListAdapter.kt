package com.test.hamonschool.classroomList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.hamonschool.R
import com.test.hamonschool.databinding.ClassroomSingleItemViewBinding
import com.test.hamonschool.room.classroom.ClassroomEntity

class ClassroomListAdapter(var list: ArrayList<ClassroomEntity>, val viewModel: ClassroomListViewModel) :
    RecyclerView.Adapter<ClassroomListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = DataBindingUtil.inflate<ClassroomSingleItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.classroom_single_item_view,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            holder.showAlert(list[position], viewModel)
        }
    }

    class ViewHolder(val binding: ClassroomSingleItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(classroom: ClassroomEntity) {
            binding.classroomModel = classroom
        }

        fun showAlert(classroom: ClassroomEntity,viewModel: ClassroomListViewModel) {
            val context = itemView.context
            viewModel.getClassroomDetails(classroom,context)
        }
    }

}
