package com.test.hamonschool

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.hamonschool.databinding.ActivitySplashBinding
import com.test.hamonschool.models.ClassroomResult
import com.test.hamonschool.models.StudentsResult
import com.test.hamonschool.models.SubjectsResult
import com.test.hamonschool.room.AppDatabase
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.student.StudentEntity
import com.test.hamonschool.room.subject.SubjectEntity
import com.test.hamonschool.utils.AppUtil
import com.test.hamonschool.utils.NetworkTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashActivity : AppCompatActivity() {

    private val tag = "SplashActivity"

    lateinit var context: SplashActivity
    private lateinit var database: AppDatabase
    private val networkTask = NetworkTask()
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        binding.title = "Loading contents"
        binding.lifecycleOwner = this


        context = this
        database = AppUtil.getDatabase(this)


        if (AppUtil.isNetworkAvailable(this))
            checkStudents()
        else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show()
            Handler().postDelayed({
                finishAffinity()
            }, 500)
        }

    }

    private fun checkStudents() {

        Log.e(tag, "Checking students")
        binding.title = "Checking students"

        val allStudents = database.studentDao().getAllStudents()
        var already = false
        allStudents.observe(this, Observer {

            if(already)
                return@Observer
            already = true

            if (it.isNotEmpty())
                checkSubjects()
            else
                getStudents()
        })

    }

    private fun checkSubjects() {

        Log.e(tag, "Checking subjects")
        binding.title = "Checking subjects"

        var already = false

        val allSubjects = database.subjectDao().getAllSubjects()
        allSubjects.observe(this, Observer {

             if(already)
                return@Observer
            already = true

            if (it.isNotEmpty())
                checkClassroom()
            else
                getSubjects()
        })

    }

    private fun getSubjects() {

        Log.e(tag, "getting subjects")
        binding.title = "getting subjects"

        val subjectsListCall = networkTask.apiCalls.getSubjectsList()

        subjectsListCall.clone().enqueue(object : Callback<SubjectsResult> {
            override fun onFailure(call: Call<SubjectsResult>, t: Throwable) {
                Toast.makeText(context, "can't connect to server", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SubjectsResult>, response: Response<SubjectsResult>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()
                    InsertSubjects(database).execute(body?.subjects)

                    checkClassroom()
                } else
                    Toast.makeText(context, "Data parsing error", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun checkClassroom() {
        Log.e(tag, "Checking classrooms")
        binding.title = "Checking classrooms"

        var already = false
        val allClassroom = database.classroomDao().getAllClassrooms()
        allClassroom.observe(this, Observer {
             if(already)
                return@Observer

            already = true
            if (it.isNotEmpty())
                moveToMainActivity()
            else
                getClassrooms()
        })
    }

    private fun getClassrooms() {
        Log.e(tag, "Getting classrooms")
        binding.title = "Getting classrooms"
        val classroomListCall = networkTask.apiCalls.getClassrooms()

        classroomListCall.clone().enqueue(object : Callback<ClassroomResult> {
            override fun onFailure(call: Call<ClassroomResult>, t: Throwable) {
                Toast.makeText(context, "can't connect to server", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ClassroomResult>, response: Response<ClassroomResult>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()
                    InsertClassrooms(database).execute(body?.classrooms)
                    moveToMainActivity()
                } else
                    Toast.makeText(context, "Data parsing error", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        this.finish()
    }

    private fun getStudents() {
        Log.e(tag, "gettings students")
        binding.title = "gettings students"
        val studentsListCall = networkTask.apiCalls.getStudentsList()

        studentsListCall.clone().enqueue(object : Callback<StudentsResult> {
            override fun onFailure(call: Call<StudentsResult>, t: Throwable) {
                Toast.makeText(context, "can't connect to server", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<StudentsResult>, response: Response<StudentsResult>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()
                    InsertStudents(database).execute(body?.students)
                    checkSubjects()

                } else
                    Toast.makeText(context, "Data parsing error", Toast.LENGTH_LONG).show()
            }

        })
    }

    class InsertStudents(val database: AppDatabase) :
        AsyncTask<List<StudentEntity>, Void, String>() {
        override fun doInBackground(vararg students: List<StudentEntity>): String {
            database.studentDao().insertAllStudents(students[0])
            return ""
        }
    }

    class InsertSubjects(val database: AppDatabase) :
        AsyncTask<List<SubjectEntity>, Void, String>() {
        override fun doInBackground(vararg subjects: List<SubjectEntity>): String {
            database.subjectDao().insertAllSubjects(subjects[0])
            return ""
        }
    }

    class InsertClassrooms(val database: AppDatabase) :
        AsyncTask<List<ClassroomEntity>, Void, String>() {
        override fun doInBackground(vararg classrooms: List<ClassroomEntity>): String {
            database.classroomDao().insertAllClassrooms(classrooms[0])
            return ""
        }
    }
}
