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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private var selectedReminderTime: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        val viewModelFactory = TaskViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
        binding.viewModel = viewModel

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, Observer {
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

        viewModel.goToMainFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        val priorities = arrayOf(
            getString(R.string.high),
            getString(R.string.normal),
            getString(R.string.low)
        )
        val priorityAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, priorities)
        binding.priorityText.setAdapter(priorityAdapter)

        // Setup adapter for time picker and save time in selectedReminderTime
        val timeAdapter = TimeAdapter(requireContext(), viewModel.remindersArray)
        binding.reminderText.setAdapter(timeAdapter)
        binding.reminderText.setOnItemClickListener { adapterView, _, position, _ ->
            selectedReminderTime = (adapterView.getItemAtPosition(position) as TimeDropdownItem).time
        }

        return binding.root
    }
}

class TimeAdapter(context: Context, private val objects: Array<out TimeDropdownItem>) :
    ArrayAdapter<TimeDropdownItem>(context, R.layout.icon_dropdown_menu_item, objects) {

    override fun getCount() = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:
        LayoutInflater.from(context).inflate(R.layout.icon_dropdown_menu_item, parent, false)
        view.findViewById<TextView>(R.id.dropdown_item_text).text = objects[position].text
        return view
    }
}