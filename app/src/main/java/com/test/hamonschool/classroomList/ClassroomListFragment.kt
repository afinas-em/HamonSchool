package com.test.hamonschool.classroomList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentClassroomListBinding

/**
 * A simple [Fragment] subclass.
 */
class ClassroomListFragment : Fragment() {

    lateinit var binding: FragmentClassroomListBinding
    lateinit var viewModel: ClassroomListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(ClassroomListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classroom_list, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.updateRepository(ClassroomRepository(context!!))

        return binding.root
    }

}
