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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.database.CalendarDatabase
import com.broprojects.studentcalendar.databinding.FragmentCourseBinding

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

        // If user is updating data, change action bar title and fill text fields
        if (args.courseId != null) {
            (activity as ToolbarActivity).setActionBarText(R.string.update_course)
        }

        viewModel.course.observe(viewLifecycleOwner, {
            binding.iconText.setText(viewModel.iconsTextMap[it.iconId])
            binding.colorText.setText(viewModel.colorsTextMap[it.colorId])
        })

        // Set app color theme on views
        viewModel.colorStateList.observe(viewLifecycleOwner, {
            binding.saveButton.backgroundTintList = it
            binding.nameTextLayout.setBoxStrokeColorStateList(it)
            binding.iconTextLayout.setBoxStrokeColorStateList(it)
            binding.colorTextLayout.setBoxStrokeColorStateList(it)
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

        viewModel.goToMainFragment.observe(viewLifecycleOwner, {
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