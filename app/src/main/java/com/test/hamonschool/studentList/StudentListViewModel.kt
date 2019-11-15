package com.test.hamonschool.studentList

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.hamonschool.models.StudentModel

class StudentListViewModel : ViewModel() {

    val showProgress = MutableLiveData(View.GONE)
    val repo = StudentsRepository()
    val studentListAdapter : MutableLiveData<StudentListAdapter?> = MutableLiveData(null)

    init {
        showProgress.value = View.VISIBLE
        repo.getStudents()

        repo.studentsList.observeForever {
            showProgress.value = View.GONE
            it?.let {
                if(it.isNotEmpty()){
                    studentListAdapter.value = StudentListAdapter(it as ArrayList<StudentModel>)
                }
            }
        }

        repo.apiError.observeForever {
            it?.let {

            }
        }
    }

}