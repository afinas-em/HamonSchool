package com.test.hamonschool.subjectList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.test.hamonschool.R
import com.test.hamonschool.databinding.FragmentSubjectListBinding

/**
 * A simple [Fragment] subclass.
 */
class SubjectListFragment : Fragment() {

    lateinit var binding: FragmentSubjectListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subject_list, container, false)

        return binding.root
    }

}
