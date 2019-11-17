package com.test.hamonschool.room.student

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
class StudentEntity(

    @PrimaryKey
    @ColumnInfo(name ="student_id")
    var id: Int,

    @ColumnInfo(name ="student_name")
    var name: String,

    @ColumnInfo(name ="student_email")
    var email: String,

    @ColumnInfo(name ="student_age")
    var age: Int
){
    @ColumnInfo(name ="student_classroom_id")
    var classroom: Int? = null
}