package com.test.hamonschool.studentList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentStudentListBinding

/**
 * A simple [Fragment] subclass.
 */
class StudentListFragment : Fragment() {

    private lateinit var binding: FragmentStudentListBinding
    private lateinit var viewModel: StudentListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_list, container, false)
        viewModel = ViewModelProvider(this).get(StudentListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        return binding.root
    }

}
