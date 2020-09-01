package com.broprojects.studentcalendar.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.database.CalendarDatabase
import com.broprojects.studentcalendar.databinding.FragmentTestBinding
import com.broprojects.studentcalendar.helpers.dateTimePickerDialog
import com.broprojects.studentcalendar.helpers.toDateTimeString
import java.util.*

class TestFragment : Fragment() {
    private var selectedWhenDateTime: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test, container, false
        )

        val dao = CalendarDatabase.getInstance(requireContext()).testsTableDao
        val viewModelFactory = TestViewModelFactory(requireActivity(), dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TestViewModel::class.java]
        binding.viewModel = viewModel

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            binding.courseTextLayout.setBoxStrokeColorStateList(it)
            binding.typeTextLayout.setBoxStrokeColorStateList(it)
            binding.subjectTextLayout.setBoxStrokeColorStateList(it)
            binding.locationTextLayout.setBoxStrokeColorStateList(it)
            binding.whenTextLayout.setBoxStrokeColorStateList(it)
            binding.infoTextLayout.setBoxStrokeColorStateList(it)
        })

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(TestFragmentDirections.actionTestFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        binding.typeText.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.test_array)
        ))

        binding.whenText.setOnClickListener {
            activity?.dateTimePickerDialog {
                selectedWhenDateTime = it
                binding.whenText.setText(it.toDateTimeString(requireContext()))
            }
        }

        return binding.root
    }
}