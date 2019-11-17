package com.test.hamonschool.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.hamonschool.classroomList.ClassroomListAdapter
import com.test.hamonschool.studentList.StudentListAdapter
import com.test.hamonschool.subjectList.SubjectListAdapter

@BindingAdapter("app:student_adapter")
fun RecyclerView.setStudentsAdapter(adapter: StudentListAdapter?) {
    if(adapter!=null) {
        this.adapter = adapter
        layoutManager = LinearLayoutManager(context)
    }
}

@BindingAdapter("app:subject_adapter")
fun RecyclerView.setSubjectsAdapter(adapter: SubjectListAdapter?) {
    if(adapter!=null) {
        this.adapter = adapter
        layoutManager = LinearLayoutManager(context)
    }
}

@BindingAdapter("app:classroom_adapter")
fun RecyclerView.setClassroomAdapter(adapter: ClassroomListAdapter?) {
    if(adapter!=null) {
        this.adapter = adapter
        layoutManager = LinearLayoutManager(context)
    }
}