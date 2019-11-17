package com.test.hamonschool.subjectList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentSubjectListBinding

/**
 * A simple [Fragment] subclass.
 */
class SubjectListFragment : Fragment() {

    lateinit var binding: FragmentSubjectListBinding
    private lateinit var viewModel: SubjectListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject_list, container, false)

        viewModel = ViewModelProvider(this).get(SubjectListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.updateRepository(SubjectsRepository(context!!))

        return binding.root
    }

}
