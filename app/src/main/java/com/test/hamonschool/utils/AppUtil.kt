package com.test.hamonschool.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.test.hamonschool.classroomList.ClassroomListFragment
import com.test.hamonschool.studentList.StudentListFragment
import com.test.hamonschool.subjectList.SubjectListFragment
import com.test.hamonschool.room.AppDatabase
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AppUtil {

    companion object{

        fun gotoFragment(manager: FragmentManager, frg: Constants.HomeFragments): Fragment {

            val fragment: Fragment = when (frg) {
                Constants.HomeFragments.StudentsList -> StudentListFragment()
                Constants.HomeFragments.ClassRoomList -> ClassroomListFragment()
                Constants.HomeFragments.SubjectList -> SubjectListFragment()
            }

            val transaction = manager.beginTransaction()
            transaction.replace(com.test.hamonschool.R.id.lytContainer, fragment)
            transaction.commitAllowingStateLoss()

            return fragment
        }

        fun getPageTitle(frg: Constants.HomeFragments): String {

            return when (frg) {
                Constants.HomeFragments.StudentsList -> "Students List"
                Constants.HomeFragments.ClassRoomList -> "Classroom List"
                Constants.HomeFragments.SubjectList -> "Subject List"
            }
        }

        fun getDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()

        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val n = cm.activeNetwork
                if (n != null) {
                    val nc = cm.getNetworkCapabilities(n)
                    return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                }
            } else {
                val ni = cm.activeNetworkInfo
                if (ni != null)
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)

            }

            return false
        }

    }

}

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): ArrayList<Int> {

        if(TextUtils.isEmpty(value))
            return ArrayList()

        val listType = object : TypeToken<ArrayList<Int>>() {

        }.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: ArrayList<Int>?): String {

        if(list==null || list.isEmpty())
            return ""

        return Gson().toJson(list)
    }
}
