package com.test.hamonschool.subjectList

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
import com.test.hamonschool.databinding.SubjectAlertLayoutBinding
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.subject.SubjectEntity
import com.test.hamonschool.room.subject.SubjectWithClass

class SubjectListViewModel : ViewModel() {

    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private lateinit var repo: SubjectsRepository
    val subjectListAdapter: MutableLiveData<SubjectListAdapter>
    var context: Context? = null
    val classrooms: ArrayList<ClassroomEntity> = ArrayList()
    val subjects: ArrayList<SubjectEntity>

    init {
        showProgress.value = true
        subjects = ArrayList()
        subjectListAdapter = MutableLiveData(SubjectListAdapter(subjects, this))
    }

    fun updateRepository(repository: SubjectsRepository) {
        repo = repository
        repo.subjectsList.observeForever {
            showProgress.value = false
            subjects.clear()
            subjects.addAll(it)
            subjectListAdapter.value!!.notifyDataSetChanged()
        }
        repo.subjectWithClass.observeForever {
            if (it == null)
                return@observeForever

            showSubjectDetails(it)
        }

        repo.classroomList.observeForever {
            classrooms.clear()
            classrooms.addAll(it)
        }
    }

    fun getSubjectWithClass(subject: SubjectEntity, context: Context?) {
        this.context = context
        repo.getClassById(subject)
    }

    private fun showSubjectDetails(subjectWithClass: SubjectWithClass) {

        if (context == null)
            return

        val builder = AlertDialog.Builder(context!!)
        val binding = DataBindingUtil.inflate<SubjectAlertLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.subject_alert_layout,
            null,
            false
        )
        binding.subject = subjectWithClass
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
                if(subjectWithClass.classroom_id == classId) {
                    Toast.makeText(context,"Already assigned",Toast.LENGTH_SHORT).show()
                    return@setOnItemClickListener
                }
                repo.assignClassToSubject(subjectWithClass.subject_id, classId)
                assignDialog.dismiss()
            }


            dialog.dismiss()
        }

        binding.btnRemove.setOnClickListener {
            dialog.dismiss()
            repo.removeClassFrom(subjectWithClass.subject_id)
        }

        binding.btnUpdate.setOnClickListener {
            binding.btnAssign.performClick()
        }

    }

}