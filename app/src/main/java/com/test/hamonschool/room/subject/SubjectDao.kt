package com.test.hamonschool.room.subject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subjects")
    fun getAllSubjects(): LiveData<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllSubjects(subjects :List<SubjectEntity>)


    @Query("UPDATE subjects SET subject_classroom = null WHERE subject_id = :id")
    fun removeClassFrom(id: Int)

    @Query("UPDATE subjects SET subject_classroom = :classId WHERE subject_id = :id")
    fun updateClass(id: Int, classId: Int)

    @Query("SELECT * FROM subjects WHERE subject_classroom = :id")
    fun getAssignedSubjectFromClass(id: Int): LiveData<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE subject_classroom = :classId")
    fun getSubjectAssignedToClass(classId: Int): SubjectEntity


}
