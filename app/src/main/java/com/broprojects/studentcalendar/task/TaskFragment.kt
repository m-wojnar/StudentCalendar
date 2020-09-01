package com.broprojects.studentcalendar.task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.database.CalendarDatabase
import com.broprojects.studentcalendar.databinding.FragmentTaskBinding
import com.broprojects.studentcalendar.helpers.dateTimePickerDialog
import com.broprojects.studentcalendar.helpers.toDateTimeString

class TaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        val args = TaskFragmentArgs.fromBundle(requireArguments())
        val dao = CalendarDatabase.getInstance(requireContext()).tasksTableDao

        val viewModelFactory = TaskViewModelFactory(requireActivity(), dao, args.taskId?.toLong())
        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
        binding.viewModel = viewModel

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

        // If user is updating data, change action bar title and fill text fields
        if (args.taskId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_task)
        }

        viewModel.task.observe(viewLifecycleOwner, {
            binding.priorityText.setText(viewModel.priorityTextMap[it.priority])
            binding.reminderText.setText(viewModel.remindersTextMap[it.reminder])
            binding.whenText.setText(it.whenDateTime?.toDateTimeString(requireContext()))
        })

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        // Setup adapter for priority picker and save value in selectedPriority
        binding.priorityText.setAdapter(ValueAdapter(requireContext(), viewModel.priorityArray))
        binding.priorityText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setPriority((adapterView.getItemAtPosition(position) as ValueDropdownItem).value)
        }

        // Setup adapter for time picker and save time in selectedReminderTime
        binding.reminderText.setAdapter(ValueAdapter(requireContext(), viewModel.remindersArray))
        binding.reminderText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setReminder((adapterView.getItemAtPosition(position) as ValueDropdownItem).value)
        }

        binding.whenText.setOnClickListener {
            activity?.dateTimePickerDialog {
                viewModel.setWhenDateTime(it)
                binding.whenText.setText(it.toDateTimeString(requireContext()))
            }
        }

        return binding.root
    }
}

class ValueAdapter(context: Context, private val objects: Array<out ValueDropdownItem>) :
    ArrayAdapter<ValueDropdownItem>(context, R.layout.icon_dropdown_menu_item, objects) {

    override fun getCount() = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:
            LayoutInflater.from(context).inflate(R.layout.icon_dropdown_menu_item, parent, false)

        view.findViewById<TextView>(R.id.dropdown_item_text).text = objects[position].text
        return view
    }
}