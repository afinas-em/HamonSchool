package com.test.hamonschool.room.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    fun getAllStudents(): LiveData<List<StudentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllStudents(students: List<StudentEntity>)

    @Query("UPDATE students SET student_classroom_id = null WHERE student_id = :id")
    fun removeClassFrom(id: Int)

    @Query("UPDATE students SET student_classroom_id = :classId WHERE student_id = :id")
    fun updateClass(id: Int, classId: Int)

    @Query("SELECT * FROM students WHERE student_classroom_id = :classId")
    fun getAssignedStudentsFromClass(classId: Int): LiveData<List<StudentEntity>>

    @Query("SELECT COUNT(*) FROM students WHERE student_classroom_id = :id")
    fun getClassroomStudentCount(id: Int): Int?

}