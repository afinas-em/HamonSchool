package com.test.hamonschool

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.hamonschool.utils.AppUtil
import com.test.hamonschool.utils.Constants

class MainViewModel : ViewModel() {

    private val appUtil = AppUtil()
    private var currFragment: Constants.HomeFragments? = null

    var currFragmentObject: Fragment? = null

    val pageTitle = MutableLiveData("Hamon School")

    fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun setCurrentFragment(frg: Constants.HomeFragments) {
        pageTitle.postValue(appUtil.getPageTitle(frg))
        currFragment = frg
    }

    fun getCurrentFragment(): Constants.HomeFragments? = currFragment

}