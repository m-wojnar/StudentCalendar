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
import com.broprojects.studentcalendar.databinding.FragmentTaskBinding
import com.broprojects.studentcalendar.dateTimePickerDialog
import com.broprojects.studentcalendar.toDateTimeString
import java.util.*

class TaskFragment : Fragment() {
    private var selectedReminderTime: Long? = null
    private var selectedWhenDateTime: Date? = null
    private var selectedPriority: Long? = null

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

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        // Setup adapter for priority picker and save value in selectedPriority
        binding.priorityText.setAdapter(ValueAdapter(requireContext(), viewModel.priorityArray))
        binding.priorityText.setOnItemClickListener { adapterView, _, position, _ ->
            selectedPriority = (adapterView.getItemAtPosition(position) as ValueDropdownItem).value
        }

        // Setup adapter for time picker and save time in selectedReminderTime
        binding.reminderText.setAdapter(ValueAdapter(requireContext(), viewModel.remindersArray))
        binding.reminderText.setOnItemClickListener { adapterView, _, position, _ ->
            selectedReminderTime = (adapterView.getItemAtPosition(position) as ValueDropdownItem).value
        }

        binding.whenText.setOnClickListener {
            activity?.dateTimePickerDialog {
                selectedWhenDateTime = it
                binding.whenText.setText(it.toDateTimeString(requireContext()))
            }
        }

        return binding.root
    }
}

data class ValueDropdownItem(val text: String, val value: Long) {
    override fun toString() = text
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