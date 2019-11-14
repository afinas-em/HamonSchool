package com.test.hamonschool.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.test.hamonschool.R
import com.test.hamonschool.utils.classroomList.ClassroomListFragment
import com.test.hamonschool.utils.studentList.StudentListFragment
import com.test.hamonschool.utils.subjectList.SubjectListFragment

class AppUtil {

    fun gotoFragment(manager: FragmentManager, frg: Constants.HomeFragments): Fragment {

        val fragment: Fragment = when (frg) {
            Constants.HomeFragments.StudentsList -> StudentListFragment()
            Constants.HomeFragments.ClassRoomList -> ClassroomListFragment()
            Constants.HomeFragments.SubjectList -> SubjectListFragment()
        }

        val transaction = manager.beginTransaction()
        transaction.replace(R.id.lytContainer, fragment)
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

}
