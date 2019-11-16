package com.test.hamonschool.studentList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.hamonschool.models.StudentModel

class StudentListViewModel : ViewModel() {

    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val repo = StudentsRepository()
    val studentListAdapter: MutableLiveData<StudentListAdapter?> = MutableLiveData(null)

    init {
        showProgress.value = true
        repo.getStudents()

        repo.studentsList.observeForever {
            it?.let {
                showProgress.value = false
                if (it.isNotEmpty()) {
                    studentListAdapter.value = StudentListAdapter(it as ArrayList<StudentModel>)
                }
            }
        }

        repo.apiError.observeForever {
            it?.let {
                showProgress.value = false

            }
        }
    }

}