package com.mwojnar.studentcalendar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.*
import com.mwojnar.studentcalendar.databinding.*
import com.mwojnar.studentcalendar.helpers.toDateString
import com.mwojnar.studentcalendar.helpers.toDateTimeString
import com.mwojnar.studentcalendar.helpers.toTimeString

class ItemDiffCallback<T : EntityClass> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem.getId() == newItem.getId()

    // I use EntityClass interface only to data classes - they have "equals" method implemented by default
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}

class OnItemClickListener<T : EntityClass>(val func: (id: Long?) -> Unit) {
    fun onClick(model: T) = func(model.getId())
}

class CourseAdapter(private val clickListener: OnItemClickListener<Course>) :
    ListAdapter<Course, CourseAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewCourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<Course>, item: Course) {
            binding.clickListener = clickListener
            binding.model = item

            item.colorId?.let {
                val context = binding.courseItem.context
                binding.courseItem.backgroundTintList = ContextCompat.getColorStateList(context, it)
            }

            item.iconId?.let {
                binding.imageView.setImageResource(it)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewCourseItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class PersonAdapter(private val clickListener: OnItemClickListener<Person>) :
    ListAdapter<Person, PersonAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewPersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<Person>, item: Person) {
            binding.clickListener = clickListener
            binding.model = item

            item.phone?.let {
                binding.phoneText.text = it
                binding.phoneText.visibility = View.VISIBLE
            }

            item.email?.let {
                binding.emailText.text = it
                binding.emailText.visibility = View.VISIBLE
            }

            item.location?.let {
                binding.locationText.text = it
                binding.locationText.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewPersonItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ScheduleAdapter(private val clickListener: OnItemClickListener<ScheduleAndCourseAndPerson>) :
    ListAdapter<ScheduleAndCourseAndPerson, ScheduleAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<ScheduleAndCourseAndPerson>, item: ScheduleAndCourseAndPerson) {
            binding.clickListener = clickListener
            binding.model = item
            val context = binding.scheduleItem.context

            item.course.colorId?.let {
                binding.scheduleItem.backgroundTintList = ContextCompat.getColorStateList(context, it)
            }

            item.course.iconId?.let {
                binding.courseIcon.setImageResource(it)
            }

            binding.courseTypeText.text = if (item.schedule.type != null) {
                    "${item.course.name}: ${item.schedule.type}"
                } else {
                    item.course.name
                }

            item.person?.let {
                binding.personText.text = item.person.toString()
                binding.personText.visibility = View.VISIBLE
            }

            var whenText = "${item.schedule.whenTime?.toTimeString(context)}, "
            whenText += when (item.schedule.weekday) {
                2 -> context.getString(R.string.monday)
                3 -> context.getString(R.string.tuesday)
                4 -> context.getString(R.string.wednesday)
                5 -> context.getString(R.string.thursday)
                6 -> context.getString(R.string.friday)
                7 -> context.getString(R.string.saturday)
                else -> context.getString(R.string.sunday)
            }
            binding.whenText.text = whenText

            binding.startEndText.text = context.getString(
                R.string.start_end_text,
                item.schedule.startDate?.toDateString(context),
                item.schedule.endDate?.toDateString(context)
            )

            item.schedule.location?.let {
                binding.locationText.text = it
                binding.locationText.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewScheduleItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TestAdapter(private val clickListener: OnItemClickListener<TestAndCourse>) :
    ListAdapter<TestAndCourse, TestAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewTestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<TestAndCourse>, item: TestAndCourse) {
            binding.clickListener = clickListener
            binding.model = item
            val context = binding.testItem.context

            item.course.colorId?.let {
                binding.testItem.backgroundTintList = ContextCompat.getColorStateList(context, it)
            }

            item.course.iconId?.let {
                binding.courseIcon.setImageResource(it)
            }

            binding.titleText.text = if (item.test.type != null) {
                    "${item.course.name}: ${item.test.type}"
                } else {
                    item.course.name
                }

            binding.whenText.text = item.test.whenDateTime?.toDateTimeString(context)

            item.test.location?.let {
                binding.locationText.text = it
                binding.locationText.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewTestItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TaskAdapter(private val clickListener: OnItemClickListener<TaskAndCourse>) :
    ListAdapter<TaskAndCourse, TaskAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<TaskAndCourse>, item: TaskAndCourse) {
            binding.clickListener = clickListener
            binding.model = item
            val context = binding.taskItem.context

            item.course?.colorId?.let {
                binding.taskItem.backgroundTintList = ContextCompat.getColorStateList(context, it)
            }

            item.course?.iconId?.let {
                binding.courseIcon.setImageResource(it)
            }

            item.course?.name?.let {
                binding.courseText.text = it
                binding.courseText.visibility = View.VISIBLE
            }

            item.task.whenDateTime?.let {
                binding.whenText.text = it.toDateTimeString(context)
                binding.whenText.visibility = View.VISIBLE
            }

            item.task.location?.let {
                binding.locationText.text = it
                binding.locationText.visibility = View.VISIBLE
            }

            binding.priorityIcon.setImageResource(when (item.task.priority) {
                2 -> R.drawable.ic_baseline_error_outline_24
                1 -> R.drawable.ic_baseline_low_priority_24
                else -> android.R.color.transparent
            })

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewTaskItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class OnYourDayItemClickListener(val func: (id: Long?, type: ItemType) -> Unit) {
    fun onClick(model: YourDayItem) = func(model.itemId, model.itemType)
}

class YourDayDiffCallback : DiffUtil.ItemCallback<YourDayItem>() {
    override fun areItemsTheSame(oldItem: YourDayItem, newItem: YourDayItem) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: YourDayItem, newItem: YourDayItem) =
        oldItem == newItem
}

class YourDayAdapter(private val clickListener: OnYourDayItemClickListener)
    : ListAdapter<YourDayItem, YourDayAdapter.ViewHolder>(YourDayDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewYourDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnYourDayItemClickListener, item: YourDayItem) {
            binding.clickListener = clickListener
            binding.model = item
            val context = binding.taskItem.context

            item.course?.colorId?.let {
                binding.taskItem.backgroundTintList = ContextCompat.getColorStateList(context, it)
            }

            item.course?.iconId?.let {
                binding.courseIcon.setImageResource(it)
            }

            binding.priorityIcon.setImageResource(when (item.priority) {
                2 -> R.drawable.ic_baseline_error_outline_24
                1 -> R.drawable.ic_baseline_low_priority_24
                else -> android.R.color.transparent
            })

            item.moreInfo?.let {
                binding.moreText.text = it
                binding.moreText.visibility = View.VISIBLE
            }

            item.location?.let {
                binding.locationText.text = it
                binding.locationText.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerViewYourDayItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}