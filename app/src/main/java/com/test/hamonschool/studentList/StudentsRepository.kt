package com.test.hamonschool.studentList

import androidx.lifecycle.MutableLiveData
import com.test.hamonschool.models.StudentModel
import com.test.hamonschool.models.StudentsResult
import com.test.hamonschool.utils.NetworkTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsRepository {

    private val networkTask = NetworkTask()
    val studentsList = MutableLiveData<List<StudentModel>>(null)
    val apiError = MutableLiveData<String>(null)

    fun getStudents() {

        val studentsListCall = networkTask.apiCalls.getStudentsList()

        studentsListCall.clone().enqueue(object : Callback<StudentsResult> {
            override fun onFailure(call: Call<StudentsResult>, t: Throwable) {
                apiError.value = "can't connect to server"
                apiError.value = null
            }

            override fun onResponse(
                call: Call<StudentsResult>, response: Response<StudentsResult>
            ) {

                if (response.isSuccessful)
                    studentsList.value = response.body()?.students
                else {
                    apiError.value = "can't connect to server"
                    apiError.value = null
                }
            }

        })


    }

}