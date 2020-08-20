package com.broprojects.studentcalendar.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentCourseBinding

class CourseFragment : Fragment() {
    private var selectedColorId: Int? = null
    private var selectedIconId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCourseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_course, container, false
        )

        val viewModelFactory = CourseViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[CourseViewModel::class.java]
        binding.viewModel = viewModel

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, Observer {
            binding.saveButton.backgroundTintList = it
            binding.nameTextLayout.setBoxStrokeColorStateList(it)
            binding.iconTextLayout.setBoxStrokeColorStateList(it)
            binding.colorTextLayout.setBoxStrokeColorStateList(it)
        })

        // Setup adapter for color picker and save colorId in selectedColorId
        val colorAdapter = IconAdapter(requireContext(), viewModel.colorsItemsArray)
        binding.colorText.setAdapter(colorAdapter)
        binding.colorText.setOnItemClickListener { adapterView, _, position, _ ->
            selectedColorId = (adapterView.getItemAtPosition(position) as IconDropdownItem).id
        }

        // Setup adapter for icon picker and save iconId in selectedIconId
        val iconAdapter = IconAdapter(requireContext(), viewModel.iconsItemsArray)
        binding.iconText.setAdapter(iconAdapter)
        binding.iconText.setOnItemClickListener { adapterView, _, position, _ ->
            selectedIconId = (adapterView.getItemAtPosition(position) as IconDropdownItem).id
        }

        viewModel.goToMainFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}

class IconAdapter(context: Context, private val objects: Array<out IconDropdownItem>) :
    ArrayAdapter<IconDropdownItem>(context, R.layout.icon_dropdown_menu_item, objects) {

    override fun getCount() = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:
            LayoutInflater.from(context).inflate(R.layout.icon_dropdown_menu_item, parent, false)

        view.findViewById<ImageView>(R.id.dropdown_item_color).setBackgroundResource(objects[position].id)
        view.findViewById<TextView>(R.id.dropdown_item_text).text = objects[position].name

        return view
    }
}