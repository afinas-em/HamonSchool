package com.test.hamonschool.studentList

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.hamonschool.R
import com.test.hamonschool.databinding.StudentAlertLayoutBinding
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.student.StudentEntity
import com.test.hamonschool.room.student.StudentWithClass

class StudentListViewModel : ViewModel() {


    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var repo: StudentsRepository
    val studentListAdapter: MutableLiveData<StudentListAdapter>
    var context: Context? = null
    val classrooms: ArrayList<ClassroomEntity> = ArrayList()
    val students : ArrayList<StudentEntity>

    init {
        showProgress.value = true
        students = ArrayList()
        studentListAdapter = MutableLiveData(StudentListAdapter(students,this))
    }

    fun updateRepository(repository: StudentsRepository) {
        repo = repository
        repo.studentsList.observeForever {
            showProgress.value = false
            students.clear()
            students.addAll(it)
            studentListAdapter.value!!.notifyDataSetChanged()
        }

        repo.studentWithClass.observeForever {
            if (it == null)
                return@observeForever

            showStudentDetails(it)
        }

        repo.classroomList.observeForever {
            classrooms.clear()
            classrooms.addAll(it)
        }
    }

    private fun showStudentDetails(studentWithClass: StudentWithClass) {

        if (context == null)
            return

        val builder = AlertDialog.Builder(context!!)
        val binding = DataBindingUtil.inflate<StudentAlertLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.student_alert_layout,
            null,
            false
        )
        binding.student = studentWithClass
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        binding.btnAssign.setOnClickListener {

            val assignBuilder = AlertDialog.Builder(context!!)
            val view = LayoutInflater.from(context).inflate(
                R.layout.select_class_dialog,
                null,
                false
            ) as ListView
            assignBuilder.setView(view)

            view.adapter = ArrayAdapter<ClassroomEntity>(
                context!!,
                android.R.layout.simple_dropdown_item_1line,
                classrooms
            )

            val assignDialog = assignBuilder.create()
            assignDialog.show()

            view.setOnItemClickListener { _, _, i, _ ->

                val classId = classrooms[i].id
                if(studentWithClass.classroom_id == classId) {
                    Toast.makeText(context,"Already assigned", Toast.LENGTH_SHORT).show()
                    return@setOnItemClickListener
                }
                assignDialog.dismiss()
                repo.assignClassToStudent(studentWithClass.student_id, classrooms[i].id)
            }


            dialog.dismiss()
        }

        binding.btnRemove.setOnClickListener {
            dialog.dismiss()
            repo.removeClassFrom(studentWithClass.student_id)
        }

        binding.btnUpdate.setOnClickListener {
            binding.btnAssign.performClick()
        }

    }

    fun getStudentWithClass(student: StudentEntity, context: Context?) {
        this.context = context
        repo.getClassById(student)
    }

}