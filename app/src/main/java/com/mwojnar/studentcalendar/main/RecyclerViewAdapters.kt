package com.mwojnar.studentcalendar.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
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

fun setCourseColorAndIcon(
    context: Context,
    layout: ConstraintLayout,
    colorName: String?,
    imageView: ImageView,
    iconName: String?
) {
    colorName?.let {
        val colorId = context.resources.getIdentifier(it, context.getString(R.string.type_color), context.packageName)
        layout.backgroundTintList = ContextCompat.getColorStateList(context, colorId)
    }

    iconName?.let {
        val iconId = context.resources.getIdentifier(it, context.getString(R.string.type_drawable), context.packageName)
        imageView.setImageResource(iconId)
    }
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

            val context = binding.courseItem.context
            setCourseColorAndIcon(context, binding.courseItem, item.colorName, binding.imageView, item.iconName)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewCourseItemBinding.inflate(layoutInflater, parent, false)

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

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewPersonItemBinding.inflate(layoutInflater, parent, false)

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
            setCourseColorAndIcon(
                context,
                binding.scheduleItem,
                item.course.colorName,
                binding.courseIcon,
                item.course.iconName
            )

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

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewScheduleItemBinding.inflate(layoutInflater, parent, false)

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
            setCourseColorAndIcon(
                context,
                binding.testItem,
                item.course.colorName,
                binding.courseIcon,
                item.course.iconName
            )

            binding.whenText.text = item.test.whenDateTime?.toDateTimeString(context)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewTestItemBinding.inflate(layoutInflater, parent, false)

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
            setCourseColorAndIcon(
                context,
                binding.taskItem,
                item.course?.colorName,
                binding.courseIcon,
                item.course?.iconName
            )

            binding.priorityIcon.setImageResource(
                when (item.task.priority) {
                    2 -> R.drawable.ic_baseline_error_outline_24
                    1 -> R.drawable.ic_baseline_low_priority_24
                    else -> android.R.color.transparent
                }
            )

            binding.whenText.text = item.task.whenDateTime?.toDateTimeString(context)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewTaskItemBinding.inflate(layoutInflater, parent, false)

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

class YourDayAdapter(private val clickListener: OnYourDayItemClickListener) :
    ListAdapter<YourDayItem, YourDayAdapter.ViewHolder>(YourDayDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewYourDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnYourDayItemClickListener, item: YourDayItem) {
            binding.clickListener = clickListener
            binding.model = item

            val context = binding.yourDayItem.context
            setCourseColorAndIcon(
                context,
                binding.yourDayItem,
                item.course?.colorName,
                binding.courseIcon,
                item.course?.iconName
            )

            binding.priorityIcon.setImageResource(
                when (item.priority) {
                    2 -> R.drawable.ic_baseline_error_outline_24
                    1 -> R.drawable.ic_baseline_low_priority_24
                    else -> android.R.color.transparent
                }
            )

            binding.whenText.text = item.whenDateTime?.toDateTimeString(context)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewYourDayItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}