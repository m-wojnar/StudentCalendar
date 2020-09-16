package com.mwojnar.studentcalendar.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.ToolbarActivity
import com.mwojnar.studentcalendar.database.ItemType
import com.mwojnar.studentcalendar.database.YourDayItem
import com.mwojnar.studentcalendar.databinding.FragmentMainBinding

class MainFragment : Fragment(), TabLayout.OnTabSelectedListener, View.OnTouchListener {
    private val animationDuration = 300L
    private val translationXValue = -60f

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private var preferences: SharedPreferences? = null
    private lateinit var gestureDetector: GestureDetectorCompat

    private val toolbarActivity: ToolbarActivity
        get() = activity as ToolbarActivity

    private val yourDayList = mutableListOf<YourDayItem>()
    private var schedulesLoaded = false
    private var tasksLoaded = false
    private var testsLoaded = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        preferences = activity?.getPreferences(Context.MODE_PRIVATE)

        // Gestures on recycler view
        gestureDetector = GestureDetectorCompat(
            requireContext(),
            GestureListener(requireContext(), binding, preferences)
        )
        binding.recyclerView.setOnTouchListener(this)

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

        // Set adapter for recycler view for you day items
        viewModel.yourDaySchedulesData.observe(viewLifecycleOwner, {
            schedulesLoaded = true
            setYourDataAdapter(it)
        })
        viewModel.yourDayTasksData.observe(viewLifecycleOwner, {
            tasksLoaded = true
            setYourDataAdapter(it)
        })
        viewModel.yourDayTestsData.observe(viewLifecycleOwner, {
            testsLoaded = true
            setYourDataAdapter(it)
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
        val recentTab = preferences?.getInt(getString(R.string.selected_tab), 0) ?: 0
        binding.tabLayout.getTabAt(1)?.select()
        binding.tabLayout.getTabAt(recentTab)?.select()
    }

    override fun onStop() {
        // Show action bar icon only on this fragment
        super.onStop()
        toolbarActivity.hideActionBarIcon()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        // Save selected tab
        preferences?.edit()
            ?.putInt(getString(R.string.selected_tab), binding.tabLayout.selectedTabPosition)
            ?.apply()

        // Reload recycler view with animation on tab change
        hideRecyclerView {
            yourDayList.clear()
            loadDataToRecyclerView(binding.tabLayout.selectedTabPosition)
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

    private fun loadDataToRecyclerView(selectedTab: Int) {
        viewModel.loadData(selectedTab)
    }

    private fun showRecyclerView() {
        binding.recyclerView.animate()
            .alpha(1.0f)
            .translationX(0f)
            .setDuration(animationDuration)
            .start()
    }

    private fun navigate(id: String? = null, tabPosition: Int = binding.tabLayout.selectedTabPosition) {
        when (tabPosition) {
            2 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestFragment(id))
            3 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToScheduleFragment(id))
            4 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToCourseFragment(id))
            5 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToPersonFragment(id))
            else -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTaskFragment(id))
        }
    }

    private fun setYourDataAdapter(list: List<YourDayItem>) {
        yourDayList.addAll(list)

        if (schedulesLoaded && testsLoaded && tasksLoaded) {
            val adapter = YourDayAdapter(OnYourDayItemClickListener { id, type ->
                when (type) {
                    ItemType.SCHEDULE -> navigate(id.toString(), 3)
                    ItemType.TEST -> navigate(id.toString(), 2)
                    ItemType.TASK -> navigate(id.toString(), 1)
                }
            })
            binding.recyclerView.adapter = adapter

            yourDayList.sortBy { it.whenDateTime?.time }
            adapter.submitList(yourDayList)

            schedulesLoaded = false
            testsLoaded = false
            tasksLoaded = false
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?) : Boolean {
        view?.performClick()
        return gestureDetector.onTouchEvent(event)
    }

    private class GestureListener(
        private val context: Context,
        private val binding: FragmentMainBinding,
        private val preferences: SharedPreferences?
    ) : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 != null && e2 != null) {
                val selectedTab = binding.tabLayout.selectedTabPosition
                val distanceX = e2.x - e1.x

                // Swipe left
                if (distanceX <= -200 && selectedTab < 5) {
                    preferences?.edit()
                        ?.putInt(context.getString(R.string.selected_tab), selectedTab + 1)
                        ?.apply()

                    binding.tabLayout.getTabAt(1)?.select()
                    binding.tabLayout.getTabAt(selectedTab + 1)?.select()
                }

                // Swipe right
                if (distanceX >= 200 && selectedTab > 0) {
                    preferences?.edit()
                        ?.putInt(context.getString(R.string.selected_tab), selectedTab - 1)
                        ?.apply()

                    binding.tabLayout.getTabAt(1)?.select()
                    binding.tabLayout.getTabAt(selectedTab - 1)?.select()
                }
            }

            return true
        }
    }
}