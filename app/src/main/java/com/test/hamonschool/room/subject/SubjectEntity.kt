package com.test.hamonschool.room.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
class SubjectEntity(

    @PrimaryKey
    @ColumnInfo(name ="subject_id")
    var id: Int,

    @ColumnInfo(name ="subject_name")
    var name: String,

    @ColumnInfo(name ="subject_teacher")
    var teacher: String,

    @ColumnInfo(name ="subject_credits")
    var credits: Int
){
    @ColumnInfo(name ="subject_classroom")
    var classroom: Int? = null
}