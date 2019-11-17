package com.test.hamonschool.room.classroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.hamonschool.room.subject.SubjectEntity

@Dao
interface ClassroomDao {

    @Query("SELECT * FROM classrooms")
    fun getAllClassrooms(): LiveData<List<ClassroomEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllClassrooms(classrooms :List<ClassroomEntity>)

    @Query("SELECT * FROM classrooms WHERE classroom_id = :id")
    fun getClassById(id: Int): LiveData<ClassroomEntity>

    @Query("SELECT * FROM classrooms WHERE classroom_id = :id")
    fun getClass(id: Int): ClassroomEntity

}