package com.test.hamonschool.room.classroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classrooms")
class ClassroomEntity(

    @PrimaryKey
    @ColumnInfo(name = "classroom_id")
    var id: Int,

    @ColumnInfo(name = "classroom_name")
    var name: String,

    @ColumnInfo(name = "classroom_layout")
    var layout: String,

    @ColumnInfo(name = "classroom_size")
    var size: Int
){

    override fun toString(): String {
        return name
    }
}