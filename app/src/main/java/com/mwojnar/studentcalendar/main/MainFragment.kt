package com.mwojnar.studentcalendar.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.ToolbarActivity
import com.mwojnar.studentcalendar.databinding.FragmentMainBinding

class MainFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private val animationDuration = 300L
    private val translationXValue = -60f

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var preferences: SharedPreferences

    private val toolbarActivity: ToolbarActivity
        get() = activity as ToolbarActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)

        val viewModelFactory = MainViewModelFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Get header of navigation drawer
        val navigationView = activity?.findViewById<NavigationView>(R.id.navigation_view)
        val headerView = navigationView?.getHeaderView(0)

        // Set chosen color to the action bar and navigation drawer
        viewModel.color.observe(viewLifecycleOwner, {
            binding.floatingActionButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), it)
            headerView?.setBackgroundResource(it)
        })

        // Set chosen text to the action bar
        viewModel.text.observe(viewLifecycleOwner, {
            toolbarActivity.setActionBarText(it)
        })

        // Load data to recycler view when ready depending on selected tab
        viewModel.coursesData.observe(viewLifecycleOwner, {
            if (it != null) {
                // Set adapter for recycler view for courses items
                val adapter = CourseAdapter(OnItemClickListener { id ->
                    navigate(id.toString())
                })
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })

        viewModel.peopleData.observe(viewLifecycleOwner, {
            if (it != null) {
                // Set adapter for recycler view for people items
                val adapter = PersonAdapter(OnItemClickListener { id ->
                    navigate(id.toString())
                })
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })

        viewModel.schedulesData.observe(viewLifecycleOwner, {
            if (it != null) {
                // Set adapter for recycler view for schedules items
                val adapter = ScheduleAdapter(OnItemClickListener { id ->
                    navigate(id.toString())
                })
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })

        viewModel.testsData.observe(viewLifecycleOwner, {
            if (it != null) {
                // Set adapter for recycler view for tests items
                val adapter = TestAdapter(OnItemClickListener { id ->
                    navigate(id.toString())
                })
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })

        viewModel.tasksData.observe(viewLifecycleOwner, {
            if (it != null) {
                // Set adapter for recycler view for tasks items
                val adapter = TaskAdapter(OnItemClickListener { id ->
                    navigate(id.toString())
                })
                binding.recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })

        binding.tabLayout.addOnTabSelectedListener(this)
        binding.floatingActionButton.setOnClickListener {
            navigate()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Show action bar icon only on this fragment
        toolbarActivity.showActionBarIcon()

        // Select recently selected "Your day" tab on startup
        val recentTab = preferences.getInt(getString(R.string.selected_tab), 0)
        binding.tabLayout.getTabAt(recentTab)?.select()
        binding.tabLayout.getTabAt(recentTab)?.select()
    }

    override fun onStop() {
        // Show action bar icon only on this fragment
        super.onStop()
        toolbarActivity.hideActionBarIcon()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        // Save selected tab
        preferences.edit()
            .putInt(getString(R.string.selected_tab), binding.tabLayout.selectedTabPosition)
            .apply()

        // Reload recycler view with animation on tab change
        hideRecyclerView {
            loadDataToRecyclerView()
            showRecyclerView()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    private fun hideRecyclerView(func: () -> Unit) {
        binding.recyclerView.animate()
            .alpha(0.0f)
            .translationX(translationXValue)
            .setDuration(animationDuration)
            .withEndAction(func)
            .start()
    }

    private fun loadDataToRecyclerView() {
        viewModel.loadData(binding.tabLayout.selectedTabPosition)
    }

    private fun showRecyclerView() {
        binding.recyclerView.animate()
            .alpha(1.0f)
            .translationX(0f)
            .setDuration(animationDuration)
            .start()
    }

    private fun navigate(id: String? = null) {
        when (binding.tabLayout.selectedTabPosition) {
            1 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTaskFragment(id))
            2 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestFragment(id))
            3 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToScheduleFragment(id))
            4 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToCourseFragment(id))
            5 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToPersonFragment(id))
        }
    }
}