package com.broprojects.studentcalendar.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPersonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_person, container, false
        )

        val viewModelFactory = PersonViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]
        binding.viewModel = viewModel

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

        return binding.root
    }
}