package com.mwojnar.studentcalendar.person

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
import com.mwojnar.studentcalendar.databinding.FragmentPersonBinding
import com.mwojnar.studentcalendar.helpers.validateEmpty

class PersonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPersonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_person, container, false
        )

        val args = PersonFragmentArgs.fromBundle(requireArguments())
        val peopleDao = CalendarDatabase.getInstance(requireContext()).peopleTableDao

        val viewModelFactory = PersonViewModelFactory(requireActivity(), peopleDao, args.personId?.toLong())
        val viewModel = ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // If user is updating data, change action bar title and show deleteButton
        if (args.personId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_person)
            binding.deleteButton.visibility = View.VISIBLE
        }

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            binding.firstNameTextLayout.setBoxStrokeColorStateList(it)
            binding.lastNameTextLayout.setBoxStrokeColorStateList(it)
            binding.titleTextLayout.setBoxStrokeColorStateList(it)
            binding.emailTextLayout.setBoxStrokeColorStateList(it)
            binding.phoneTextLayout.setBoxStrokeColorStateList(it)
            binding.officeTextLayout.setBoxStrokeColorStateList(it)
            binding.infoTextLayout.setBoxStrokeColorStateList(it)
        })

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(PersonFragmentDirections.actionPersonFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        binding.saveButton.setOnClickListener {
            // Validate input fields
            if (validateEmpty(this, binding.lastNameTextLayout, binding.lastNameText)) {
                viewModel.saveData()
            }
        }

        return binding.root
    }
}