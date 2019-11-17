package com.test.hamonschool.studentList

import android.content.Context
import android.os.AsyncTask
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.hamonschool.room.AppDatabase
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.student.StudentEntity
import com.test.hamonschool.room.student.StudentWithClass
import com.test.hamonschool.utils.AppUtil

class StudentsRepository(context: Context) {

    private val db: AppDatabase = AppUtil.getDatabase(context)
    val studentsList: LiveData<List<StudentEntity>>
    val classroomList: LiveData<List<ClassroomEntity>>
    var studentWithClass: MutableLiveData<StudentWithClass> = MutableLiveData()
    var toast = MutableLiveData("")


    init {
        studentsList = db.studentDao().getAllStudents()
        classroomList = db.classroomDao().getAllClassrooms()
        toast.observeForever {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

    fun getClassById(student: StudentEntity) {

        if (student.classroom != null) {

            db.classroomDao().getClassById(student.classroom!!).observeForever {
                studentWithClass.value = StudentWithClass(
                    student.id,
                    student.name,
                    student.email,
                    student.age,
                    student.classroom!!,
                    it.id,
                    it.name,
                    it.layout,
                    it.size
                )
                studentWithClass.value = null
            }
        } else {
            studentWithClass.value = StudentWithClass(
                student.id,
                student.name,
                student.email,
                student.age,
                null,
                null,
                " - ",
                null,
                null
            )
            studentWithClass.value = null
        }

    }

    fun removeClassFrom(studentId: Int) {
        RemoveClassTask(db, toast).execute(studentId)
    }

    fun assignClassToStudent(id: Int, classId: Int) {

        var isCalled = false
        db.studentDao().getAssignedStudentsFromClass(classId).observeForever { list ->

            if (isCalled)
                return@observeForever

            var isCalled1 = false
            db.subjectDao().getAssignedSubjectFromClass(classId).observeForever {
                if (isCalled1)
                    return@observeForever

                isCalled1 = true
                if (it.isEmpty()) {
                    toast.value = "Subject not assigned"
                    toast.value = ""
                } else
                    UpdateClassTask(db, toast, list.size).execute(id, classId)
            }


            isCalled = true
        }

    }

    class RemoveClassTask(
        val db: AppDatabase,
        val toast: MutableLiveData<String>
    ) : AsyncTask<Int, Void, String>() {
        override fun doInBackground(vararg id: Int?): String {
            db.studentDao().removeClassFrom(id[0]!!)
            return "Data updated"
        }

        override fun onPostExecute(result: String?) {
            toast.value = result
            toast.value = ""
        }
    }

    class UpdateClassTask(
        val db: AppDatabase,
        val toast: MutableLiveData<String>,
        val size: Int
    ) : AsyncTask<Int, Void, String>() {

        override fun doInBackground(vararg id: Int?): String {
            val classroomEntity = db.classroomDao().getClass(id[1]!!)
            if (size < classroomEntity.size) {
                db.studentDao().updateClass(id[0]!!, id[1]!!)
                return "Data updated"
            } else
                return "No seats available in the classroom"
        }

        override fun onPostExecute(result: String?) {
            toast.value = result
            toast.value = ""
        }
    }


}