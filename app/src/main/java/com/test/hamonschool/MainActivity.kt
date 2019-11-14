package com.test.hamonschool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.test.hamonschool.databinding.ActivityMainBinding
import com.test.hamonschool.utils.AppUtil
import com.test.hamonschool.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this

        navHome.setNavigationItemSelectedListener {

            val frg: Constants.HomeFragments = when (it.itemId) {

                R.id.menu_students -> Constants.HomeFragments.StudentsList
                R.id.menu_classrooms -> Constants.HomeFragments.ClassRoomList
                R.id.menu_subjects -> Constants.HomeFragments.SubjectList
                else -> Constants.HomeFragments.StudentsList

            }

            //close navigation drawer
            lytDrawer.closeDrawer(GravityCompat.START)
            gotoFragment(frg)

            return@setNavigationItemSelectedListener true
        }

        gotoFragment(Constants.HomeFragments.StudentsList)

    }

    private fun gotoFragment(frg: Constants.HomeFragments) {

        if (frg == viewmodel.getCurrentFragment())
            return

        viewmodel.setCurrentFragment(frg)
        val fragment = AppUtil().gotoFragment(supportFragmentManager, frg)
        viewmodel.currFragmentObject = fragment
    }
}
