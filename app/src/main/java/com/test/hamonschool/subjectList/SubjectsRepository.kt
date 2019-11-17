package com.test.hamonschool.subjectList

import android.content.Context
import android.os.AsyncTask
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.hamonschool.room.AppDatabase
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.subject.SubjectEntity
import com.test.hamonschool.room.subject.SubjectWithClass
import com.test.hamonschool.utils.AppUtil

class SubjectsRepository(context: Context) {

    var db: AppDatabase = AppUtil.getDatabase(context)
    var subjectsList: LiveData<List<SubjectEntity>>
    val classroomList: LiveData<List<ClassroomEntity>>
    var subjectWithClass: MutableLiveData<SubjectWithClass> = MutableLiveData()
    var toast = MutableLiveData("")

    init {
        subjectsList = db.subjectDao().getAllSubjects()
        classroomList = db.classroomDao().getAllClassrooms()
        toast.observeForever {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun getClassById(subject: SubjectEntity) {

        if (subject.classroom != null) {

            db.classroomDao().getClassById(subject.classroom!!).observeForever {
                subjectWithClass.value = SubjectWithClass(
                    subject.id,
                    subject.name,
                    subject.teacher,
                    subject.credits,
                    subject.classroom!!,
                    it.id,
                    it.name,
                    it.layout,
                    it.size
                )
                subjectWithClass.value = null
            }
        } else {
            subjectWithClass.value = SubjectWithClass(
                subject.id,
                subject.name,
                subject.teacher,
                subject.credits,
                null,
                null,
                " - ",
                null,
                null
            )
            subjectWithClass.value = null
        }

    }

    fun removeClassFrom(subjectId: Int) {
        RemoveClassTask(db, toast).execute(subjectId)
    }

    fun assignClassToSubject(id: Int, classId: Int) {

        var isCalled = false
        db.subjectDao().getAssignedSubjectFromClass(classId).observeForever {

            if (isCalled)
                return@observeForever

            isCalled = true

            if (it.isEmpty())
                UpdateClassTask(db, toast).execute(id, classId)
            else
                toast.value = "Already assigned by other subject"

            toast.value = ""
        }

    }

    class RemoveClassTask(
        private val db: AppDatabase,
        val toast: MutableLiveData<String>
    ) : AsyncTask<Int, Void, String>() {
        override fun doInBackground(vararg id: Int?): String {
            db.subjectDao().removeClassFrom(id[0]!!)
            return "Data updated"
        }

        override fun onPostExecute(result: String?) {
            toast.value = result
            toast.value = ""
        }
    }

    class UpdateClassTask(
        private val db: AppDatabase,
        val toast: MutableLiveData<String>
    ) : AsyncTask<Int, Void, String>() {
        override fun doInBackground(vararg id: Int?): String {
            db.subjectDao().updateClass(id[0]!!, id[1]!!)
            return "Data updated"
        }

        override fun onPostExecute(result: String?) {
            toast.value = result
            toast.value = ""
        }
    }


}