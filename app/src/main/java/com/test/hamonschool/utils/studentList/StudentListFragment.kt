package com.test.hamonschool.utils.studentList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentStudentListBinding

/**
 * A simple [Fragment] subclass.
 */
class StudentListFragment : Fragment() {

    lateinit var binding : FragmentStudentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_list,container,false)

        return binding.root
    }

}
