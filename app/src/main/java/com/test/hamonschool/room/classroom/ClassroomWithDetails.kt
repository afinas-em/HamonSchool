package com.test.hamonschool.room.classroom

class ClassroomWithDetails(

    var classroom_id: Int,
    var classroom_name: String,
    var classroom_layout: String,
    var classroom_size: Int,
    var teacher: String,
    var subject: String,
    var students: Int = 0

)