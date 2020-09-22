package com.mwojnar.studentcalendar.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.ToolbarActivity
import com.mwojnar.studentcalendar.database.CalendarDatabase
import com.mwojnar.studentcalendar.databinding.FragmentTaskBinding
import com.mwojnar.studentcalendar.helpers.*

class TaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        val args = TaskFragmentArgs.fromBundle(requireArguments())
        val database = CalendarDatabase.getInstance(requireContext())

        val dao = database.tasksTableDao
        val coursesDao = database.coursesTableDao

        val viewModelFactory = TaskViewModelFactory(requireActivity(), dao, coursesDao, args.taskId?.toLong())
        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            binding.courseTextLayout.setBoxStrokeColorStateList(it)
            binding.titleTextLayout.setBoxStrokeColorStateList(it)
            binding.whenTextLayout.setBoxStrokeColorStateList(it)
            binding.priorityTextLayout.setBoxStrokeColorStateList(it)
            binding.locationTextLayout.setBoxStrokeColorStateList(it)
            binding.whenTextLayout.setBoxStrokeColorStateList(it)
            binding.reminderTextLayout.setBoxStrokeColorStateList(it)
            binding.infoTextLayout.setBoxStrokeColorStateList(it)
        })

        // If user is updating data, change action bar title, fill text fields and show deleteButton
        if (args.taskId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_task)
            binding.deleteButton.visibility = View.VISIBLE
        }

        viewModel.model.observe(viewLifecycleOwner, {
            binding.priorityText.setText(viewModel.priorityTextMap[it.priority], false)
            binding.reminderText.setText(viewModel.remindersTextMap[it.reminder], false)
            binding.whenText.setText(it.whenDateTime?.toDateTimeString(requireContext()))
            viewModel.loadCourseName()
        })

        viewModel.selectedCourse.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.courseText.setText(it.name, false)
            }
        })

        binding.priorityText.setAdapter(ValueAdapter(requireContext(), viewModel.priorityArray))
        binding.priorityText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setPriority((adapterView.getItemAtPosition(position) as ValueDropdownItem).value.toInt())
        }

        binding.reminderText.setAdapter(ValueAdapter(requireContext(), viewModel.remindersArray))
        binding.reminderText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setReminder((adapterView.getItemAtPosition(position) as ValueDropdownItem).value)
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
            val titleEmpty = validateEmpty(this, binding.titleTextLayout, binding.titleText)
            var whenEmpty = true
            var reminderTimeInPast = true

            viewModel.model.value?.reminder?.let {
                if (it >= 0) {
                    whenEmpty = validateEmpty(this, binding.whenTextLayout, binding.whenText)
                    val reminderTime = viewModel.model.value?.whenDateTime?.time?.minus(it)
                    reminderTimeInPast = validateReminderInThePast(
                        this, binding.reminderTextLayout, reminderTime ?: 0
                    )
                }
            }

            if (titleEmpty && whenEmpty && reminderTimeInPast) {
                viewModel.saveData()
            }
        }

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}

