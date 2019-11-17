package com.test.hamonschool.classroomList

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.hamonschool.R
import com.test.hamonschool.databinding.ClassroomAlertLayoutBinding
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.classroom.ClassroomWithDetails

class ClassroomListViewModel : ViewModel() {


    val showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    lateinit var repo: ClassroomRepository
    val classroomListAdapter: MutableLiveData<ClassroomListAdapter?> = MutableLiveData(null)
    var context: Context? = null

    init {
        showProgress.value = true

    }

    fun updateRepository(repository: ClassroomRepository) {
        repo = repository
        repo.classroomList.observeForever {
            showProgress.value = false
            classroomListAdapter.value = ClassroomListAdapter(it as ArrayList<ClassroomEntity>, this)
        }
        repo.classroomWithDetails.observeForever {
            if (it == null)
                return@observeForever

            showClassDetails(it)
        }
    }

    private fun showClassDetails(classroom: ClassroomWithDetails) {
        if (context == null)
            return

        val builder = AlertDialog.Builder(context!!)
        val binding = DataBindingUtil.inflate<ClassroomAlertLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.classroom_alert_layout,
            null,
            false
        )
        binding.classroom = classroom
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    fun getClassroomDetails(classroom: ClassroomEntity, context: Context) {
        this.context = context
        repo.getClassDetails(classroom)
    }

}