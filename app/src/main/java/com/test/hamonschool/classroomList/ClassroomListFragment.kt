package com.test.hamonschool.classroomList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentClassroomListBinding

/**
 * A simple [Fragment] subclass.
 */
class ClassroomListFragment : Fragment() {

    lateinit var binding: FragmentClassroomListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_classroom_list, container, false)

        return binding.root
    }

}
