package com.mwojnar.studentcalendar.course

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
import com.mwojnar.studentcalendar.databinding.FragmentCourseBinding
import com.mwojnar.studentcalendar.helpers.IconAdapter
import com.mwojnar.studentcalendar.helpers.IconDropdownItem
import com.mwojnar.studentcalendar.helpers.validateEmpty

class CourseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCourseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_course, container, false
        )

        val args = CourseFragmentArgs.fromBundle(requireArguments())
        val dao = CalendarDatabase.getInstance(requireContext()).coursesTableDao

        val viewModelFactory = CourseViewModelFactory(requireActivity(), dao, args.courseId?.toLong())
        val viewModel = ViewModelProvider(this, viewModelFactory)[CourseViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // If user is updating data, change action bar title, fill text fields and show deleteButton
        if (args.courseId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_course)
            binding.deleteButton.visibility = View.VISIBLE
        }

        viewModel.model.observe(viewLifecycleOwner, {
            binding.iconText.setText(viewModel.iconsTextMap[it.iconId], false)
            binding.colorText.setText(viewModel.colorsTextMap[it.colorId], false)
        })

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            viewModel.setBoxStrokeColorForChildren(binding.mainLayout, it)
        })

        // Setup adapter for color picker and save colorId in selectedColorId
        binding.colorText.setAdapter(IconAdapter(requireContext(), viewModel.colorsItemsArray))
        binding.colorText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setColor((adapterView.getItemAtPosition(position) as IconDropdownItem).id)
        }

        // Setup adapter for icon picker and save iconId in selectedIconId
        binding.iconText.setAdapter(IconAdapter(requireContext(), viewModel.iconsItemsArray))
        binding.iconText.setOnItemClickListener { adapterView, _, position, _ ->
            viewModel.setIcon((adapterView.getItemAtPosition(position) as IconDropdownItem).id)
        }

        binding.saveButton.setOnClickListener {
            // Validate input fields
            if (validateEmpty(this, binding.nameTextLayout, binding.nameText)) {
                viewModel.saveData()
            }
        }

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}