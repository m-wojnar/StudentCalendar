package com.mwojnar.studentcalendar.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.ToolbarActivity
import com.mwojnar.studentcalendar.database.CalendarDatabase
import com.mwojnar.studentcalendar.databinding.FragmentTestBinding
import com.mwojnar.studentcalendar.helpers.*

class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_test, container, false
        )

        val args = TestFragmentArgs.fromBundle(requireArguments())
        val database = CalendarDatabase.getInstance(requireContext())

        val dao = database.testsTableDao
        val coursesDao = database.coursesTableDao

        val viewModelFactory = TestViewModelFactory(requireActivity(), dao, coursesDao, args.testId?.toLong())
        val viewModel = ViewModelProvider(this, viewModelFactory)[TestViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // If user is updating data, change action bar title, fill text fields and show deleteButton
        if (args.testId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_test)
            binding.deleteButton.visibility = View.VISIBLE
        }

        viewModel.model.observe(viewLifecycleOwner, {
            binding.whenText.setText(it.whenDateTime?.toDateTimeString(requireContext()))
            binding.typeText.setText(it.type, false)
            viewModel.loadCourseName()
        })

        viewModel.selectedCourse.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.courseText.setText(it.name, false)
            }
        })

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

        binding.typeText.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.test_array)
        ))
        binding.typeText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setType(adapterView.getItemAtPosition(position).toString())
        }

        // Load courses dropdown items and set list in adapter
        viewModel.coursesList.observe(viewLifecycleOwner, { dropdownList ->
            binding.courseText.setAdapter(
                ValueAdapter(requireContext(), dropdownList.map { it.toValueDropdownItem() }.toTypedArray())
            )
            binding.courseText.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setCourse((adapterView.getItemAtPosition(position) as ValueDropdownItem).value)
            }
        })

        binding.whenText.setOnClickListener {
            activity?.dateTimePickerDialog {
                viewModel.setWhenDateTime(it)
                binding.whenText.setText(it.toDateTimeString(requireContext()))
            }
        }

        binding.saveButton.setOnClickListener {
            // Validate input fields
            val courseEmpty = validateEmpty(this, binding.courseTextLayout, binding.courseText)
            val subjectEmpty = validateEmpty(this, binding.subjectTextLayout, binding.subjectText)
            val whenEmpty = validateEmpty(this, binding.whenTextLayout, binding.whenText)

            if (courseEmpty && subjectEmpty && whenEmpty) {
                viewModel.saveData()
            }
        }

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(TestFragmentDirections.actionTestFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}