package com.mwojnar.studentcalendar.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mwojnar.studentcalendar.database.*
import com.mwojnar.studentcalendar.databinding.RecyclerViewCourseItemBinding
import com.mwojnar.studentcalendar.databinding.RecyclerViewPersonItemBinding

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

            var titleNameText = ""
            item.title?.let { titleNameText += "$it " }
            item.firstName?.let { titleNameText += "$it " }
            titleNameText += item.lastName

            binding.titleNameText.text = titleNameText

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

class ScheduleAdapter(private val clickListener: OnItemClickListener<Schedule>) :
    ListAdapter<Schedule, ScheduleAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewCourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<Schedule>, item: Schedule) {
            binding.clickListener = clickListener
            //binding.model = item

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

class TestAdapter(private val clickListener: OnItemClickListener<Test>) :
    ListAdapter<Test, TestAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewCourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<Test>, item: Test) {
            binding.clickListener = clickListener
            //binding.model = item

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

class TaskAdapter(private val clickListener: OnItemClickListener<Task>) :
    ListAdapter<Task, TaskAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent)

    class ViewHolder private constructor(val binding: RecyclerViewCourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnItemClickListener<Task>, item: Task) {
            binding.clickListener = clickListener
            //binding.model = item

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
