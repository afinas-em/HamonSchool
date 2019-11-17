package com.test.hamonschool.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.hamonschool.room.classroom.ClassroomDao
import com.test.hamonschool.room.classroom.ClassroomEntity
import com.test.hamonschool.room.student.StudentDao
import com.test.hamonschool.room.student.StudentEntity
import com.test.hamonschool.room.subject.SubjectDao
import com.test.hamonschool.room.subject.SubjectEntity
import com.test.hamonschool.utils.AppUtil
import com.test.hamonschool.utils.Constants
import com.test.hamonschool.utils.Converters

@Database(
    entities = [ClassroomEntity::class, StudentEntity::class, SubjectEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun classroomDao(): ClassroomDao
    abstract fun subjectDao(): SubjectDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, Constants.DATABASE_NAME)
            .build()
    }
}