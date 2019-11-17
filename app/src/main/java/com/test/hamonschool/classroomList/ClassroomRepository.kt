package com.test.hamonschool.classroomList

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.hamonschool.room.AppDatabase
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.classroom.ClassroomWithDetails
import com.test.hamonschool.room.subject.SubjectEntity
import com.test.hamonschool.utils.AppUtil

class ClassroomRepository(context: Context) {

    var db: AppDatabase = AppUtil.getDatabase(context)
    var classroomList: LiveData<List<ClassroomEntity>>
    var classroomWithDetails: MutableLiveData<ClassroomWithDetails> = MutableLiveData()

    init {
        classroomList = db.classroomDao().getAllClassrooms()
    }

    fun getClassDetails(classroom: ClassroomEntity) {
        ClassroomTask(db, classroom, classroomWithDetails).execute()
    }

    class ClassroomTask(
        val db: AppDatabase,
        val classroom: ClassroomEntity,
        val classroomWithDetails: MutableLiveData<ClassroomWithDetails>
    ) :
        AsyncTask<Void, Void, ClassroomWithDetails>() {

        override fun doInBackground(vararg p0: Void?): ClassroomWithDetails {

            val subject: SubjectEntity? = db.subjectDao().getSubjectAssignedToClass(classroom.id)
            val count: Int? = db.studentDao().getClassroomStudentCount(classroom.id)

            val teacher = subject?.teacher ?: " - "
            val subjectName = subject?.name ?: " - "
            val students = count ?: 0

            return ClassroomWithDetails(
                classroom.id,
                classroom.name,
                classroom.layout,
                classroom.size,
                teacher,
                subjectName,
                students

            )
        }

        override fun onPostExecute(result: ClassroomWithDetails?) {
            classroomWithDetails.value = result
        }

    }
}