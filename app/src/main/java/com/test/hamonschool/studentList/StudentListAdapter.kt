package com.test.hamonschool.studentList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.hamonschool.R
import com.test.hamonschool.databinding.StudentSingleItemViewBinding
import com.test.hamonschool.models.StudentModel

class StudentListAdapter(var list: ArrayList<StudentModel>) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = DataBindingUtil.inflate<StudentSingleItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.student_single_item_view,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(val binding: StudentSingleItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(studentModel: StudentModel) {
            binding.studentModel = studentModel
        }
    }

}
