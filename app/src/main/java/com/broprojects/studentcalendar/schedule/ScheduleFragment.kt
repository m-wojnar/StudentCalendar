package com.broprojects.studentcalendar.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.*
import com.broprojects.studentcalendar.databinding.FragmentScheduleBinding
import java.util.*

class ScheduleFragment : Fragment() {
    private var startDate: Date? = null
    private var endDate: Date? = null
    private var whenDateTime: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentScheduleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_schedule, container, false
        )

        val viewModelFactory = ScheduleViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[ScheduleViewModel::class.java]
        binding.viewModel = viewModel

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            binding.typeTextLayout.setBoxStrokeColorStateList(it)
            binding.whenTextLayout.setBoxStrokeColorStateList(it)
            binding.startTextLayout.setBoxStrokeColorStateList(it)
            binding.endTextLayout.setBoxStrokeColorStateList(it)
            binding.personTextLayout.setBoxStrokeColorStateList(it)
            binding.locationTextLayout.setBoxStrokeColorStateList(it)
            binding.infoTextLayout.setBoxStrokeColorStateList(it)
        })

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        binding.typeText.setAdapter(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.schedule_array)
        ))

        binding.startText.setOnClickListener {
            activity?.datePickerDialog {
                startDate = it
                binding.startText.setText(it.toDateString(requireContext()))
            }
        }

        binding.endText.setOnClickListener {
            activity?.datePickerDialog {
                endDate = it
                binding.endText.setText(it.toDateString(requireContext()))
            }
        }

        binding.whenText.setOnClickListener {
            activity?.timePickerDialog {
                whenDateTime = it
                binding.whenText.setText(it.toTimeString(requireContext()))
            }
        }

        return binding.root
    }
}