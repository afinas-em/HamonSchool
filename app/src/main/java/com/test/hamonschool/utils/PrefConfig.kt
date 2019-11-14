package com.minideed.nativeandroidapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class PrefConfig(context: Context) {

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    private companion object {
        const val PREF_NAME: String = "HamonSchool"
    }


    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

//    var tutorialsShown: Boolean
//        get() = sharedPreferences.getBoolean(TUTORIAL_SHOWN, false)
//        set(value) {
//            editor.putBoolean(TUTORIAL_SHOWN, value)
//            editor.apply()
//        }


}
