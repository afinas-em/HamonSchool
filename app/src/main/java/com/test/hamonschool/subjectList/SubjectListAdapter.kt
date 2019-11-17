package com.test.hamonschool.subjectList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.hamonschool.R
import com.test.hamonschool.databinding.StudentSingleItemViewBinding
import com.test.hamonschool.databinding.SubjectSingleItemViewBinding
import com.test.hamonschool.room.student.StudentEntity
import com.test.hamonschool.room.subject.SubjectEntity

class SubjectListAdapter(var list: ArrayList<SubjectEntity>, var viewModel: SubjectListViewModel) :
    RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = DataBindingUtil.inflate<SubjectSingleItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subject_single_item_view,
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

    class ViewHolder(val binding: SubjectSingleItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subjectEntity: SubjectEntity) {
            binding.subjectModel = subjectEntity
        }

        fun showAlert(subject: SubjectEntity, viewModel: SubjectListViewModel) {
            val context = itemView.context
            viewModel.getSubjectWithClass(subject,context)
        }
    }

}
